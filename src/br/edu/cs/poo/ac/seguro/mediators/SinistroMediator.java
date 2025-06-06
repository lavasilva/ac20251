package br.edu.cs.poo.ac.seguro.mediators;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.edu.cs.poo.ac.seguro.daos.ApoliceDAO;
import br.edu.cs.poo.ac.seguro.daos.SinistroDAO;
import br.edu.cs.poo.ac.seguro.daos.VeiculoDAO;
import br.edu.cs.poo.ac.seguro.entidades.Apolice;
import br.edu.cs.poo.ac.seguro.entidades.Sinistro;
import br.edu.cs.poo.ac.seguro.entidades.TipoSinistro;
import br.edu.cs.poo.ac.seguro.entidades.Veiculo;
import br.edu.cs.poo.ac.seguro.excecoes.ExcecaoValidacaoDados;

public class SinistroMediator {

	private VeiculoDAO daoVeiculo = new VeiculoDAO();
	private ApoliceDAO daoApolice = new ApoliceDAO();
	private SinistroDAO daoSinistro = new SinistroDAO();
	private static SinistroMediator instancia;

	public static SinistroMediator getInstancia() {
		if (instancia == null)
			instancia = new SinistroMediator();
		return instancia;
	}

	private SinistroMediator() {}

	public String incluirSinistro(DadosSinistro dados, LocalDateTime dataHoraAtual) throws ExcecaoValidacaoDados {
		ExcecaoValidacaoDados excecao = new ExcecaoValidacaoDados();
		List<String> mensagensErro = excecao.getMensagens();

		if (dados == null) {
			mensagensErro.add("Dados do sinistro devem ser informados");
			throw excecao;
		}

		if (dados.getDataHoraSinistro() == null) {
			mensagensErro.add("Data/hora do sinistro deve ser informada");
		}

		if (dados.getDataHoraSinistro() != null &&
				(dados.getDataHoraSinistro().isAfter(dataHoraAtual) || dados.getDataHoraSinistro().isEqual(dataHoraAtual))) {
			mensagensErro.add("Data/hora do sinistro deve ser menor que a data/hora atual");
		}

		if (StringUtils.ehNuloOuBranco(dados.getPlaca())) {
			mensagensErro.add("Placa do Veiculo deve ser informada");
		}

		Veiculo veiculo = null;
		if (!StringUtils.ehNuloOuBranco(dados.getPlaca())) {
			veiculo = daoVeiculo.buscar(dados.getPlaca());
			if (veiculo == null) {
				mensagensErro.add("Veiculo não cadastrado");
			}
		}

		if (StringUtils.ehNuloOuBranco(dados.getUsuarioRegistro())) {
			mensagensErro.add("Usuario do registro de sinistro deve ser informado");
		}

		if (dados.getValorSinistro() <= 0) {
			mensagensErro.add("Valor do sinistro deve ser maior que zero");
		}

		TipoSinistro tipoSinistro = TipoSinistro.getTipoSinistro(dados.getCodigoTipoSinistro());
		if (tipoSinistro == null) {
			mensagensErro.add("Codigo do tipo de sinistro invalido");
		}

		if (!mensagensErro.isEmpty()) {
			throw excecao;
		}

		Apolice apoliceVigente = buscarApoliceVigente(veiculo, dados.getDataHoraSinistro());

		if (apoliceVigente == null) {
			mensagensErro.add("Nao existe apolice vigente para o veiculo");
			throw excecao;
		}


		BigDecimal valorSinistro = new BigDecimal(dados.getValorSinistro());
		if (valorSinistro.compareTo(apoliceVigente.getValorMaximoSegurado()) > 0) {
			mensagensErro.add("Valor do sinistro nao pode ultrapassar o valor maximo segurado constante na apolice");
			throw excecao;
		}

		String numeroApolice = apoliceVigente.getNumero();
		int sequencial = calcularProximoSequencial(numeroApolice);

		String sequencialFormatado = String.format("%03d", sequencial);
		String numeroSinistro = "S" + numeroApolice + sequencialFormatado;

		Sinistro sinistro = new Sinistro(numeroSinistro, veiculo, dados.getDataHoraSinistro(),
				dataHoraAtual, dados.getUsuarioRegistro(), valorSinistro, tipoSinistro, sequencial, numeroSinistro);

		sinistro.setNumeroApolice(numeroApolice);
		sinistro.setSequencial(sequencial);

		boolean incluido = daoSinistro.incluir(sinistro);
		if (!incluido) {
			mensagensErro.add("Erro ao incluir sinistro no banco de dados");
			throw excecao;
		}

		return numeroSinistro;
	}

	private Apolice buscarApoliceVigente(Veiculo veiculo, LocalDateTime dataHoraSinistro) {
		if (veiculo == null || dataHoraSinistro == null) {
			return null;
		}

		CadastroObjetos cadastroApolices = new CadastroObjetos(Apolice.class);
		Serializable[] todasApolices = cadastroApolices.buscarTodos();

		if (todasApolices == null) {
			return null;
		}

		for (Serializable obj : todasApolices) {
			if (obj instanceof Apolice) {
				Apolice ap = (Apolice) obj;

				if (ap.getVeiculo() != null && veiculosIguais(ap.getVeiculo(), veiculo)) {
					LocalDateTime inicioVigencia = ap.getDataInicioVigencia().atStartOfDay();
					LocalDateTime fimVigencia = inicioVigencia.plusYears(1);

					if (!dataHoraSinistro.isBefore(inicioVigencia) && dataHoraSinistro.isBefore(fimVigencia)) {
						return ap;
					}
				}
			}
		}

		return null;
	}

	private boolean veiculosIguais(Veiculo v1, Veiculo v2) {
		if (v1 == null || v2 == null) {
			return false;
		}
		return v1.getPlaca() != null && v1.getPlaca().equals(v2.getPlaca());
	}

	private int calcularProximoSequencial(String numeroApolice) {
		Sinistro[] todosSinistros = daoSinistro.buscarTodos();
		List<Sinistro> sinistrosApolice = new ArrayList<>();

		if (todosSinistros != null) {
			for (Sinistro s : todosSinistros) {
				if (s != null && s.getNumero() != null &&
						s.getNumero().startsWith("S" + numeroApolice)) {
					sinistrosApolice.add(s);
				}
			}
		}

		if (sinistrosApolice.isEmpty()) {
			return 1;
		}

		Collections.sort(sinistrosApolice, new ComparadorSinistroSequencial());

		Sinistro ultimoSinistro = sinistrosApolice.get(sinistrosApolice.size() - 1);
		String numeroUltimo = ultimoSinistro.getNumero();
		String sequencialStr = numeroUltimo.substring(numeroUltimo.length() - 3);

		try {
			return Integer.parseInt(sequencialStr) + 1;
		} catch (NumberFormatException e) {
			return 1;
		}
	}
}
