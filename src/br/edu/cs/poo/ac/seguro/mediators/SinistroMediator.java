package br.edu.cs.poo.ac.seguro.mediators;

import java.time.LocalDateTime;
import java.util.List;

import br.edu.cs.poo.ac.seguro.daos.ApoliceDAO;
import br.edu.cs.poo.ac.seguro.daos.SinistroDAO;
import br.edu.cs.poo.ac.seguro.daos.VeiculoDAO;
import br.edu.cs.poo.ac.seguro.entidades.Apolice;
import br.edu.cs.poo.ac.seguro.entidades.Sinistro;
import br.edu.cs.poo.ac.seguro.entidades.TipoSinistro;
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
    if (dados == null) {
        throw new NullPointerException("Dados do sinistro são nulos.");
    }

    ExcecaoValidacaoDados excecao = new ExcecaoValidacaoDados();

    if (dados.getDataHoraSinistro() == null) {
        excecao.getMensagens().add("Data e hora do sinistro não informada.");
    } else if (dados.getDataHoraSinistro().isAfter(dataHoraAtual)) {
        excecao.getMensagens().add("Data e hora do sinistro inválida (no futuro).");
    }

    if (dados.getPlaca() == null || dados.getPlaca().trim().isEmpty()) {
        excecao.getMensagens().add("Placa do veículo não informada.");
    } else if (!daoVeiculo.existePlaca(dados.getPlaca())) {
        excecao.getMensagens().add("Veículo não cadastrado.");
    }

    if (dados.getUsuarioRegistro() == null || dados.getUsuarioRegistro().trim().isEmpty()) {
        excecao.getMensagens().add("Usuário que registrou o sinistro não informado.");
    }

    if (dados.getValorSinistro() <= 0) {
        excecao.getMensagens().add("Valor do sinistro deve ser maior que zero.");
    }

    TipoSinistro tipoSinistro = TipoSinistro.getTipoPorCodigo(dados.getCodigoTipoSinistro());
    if (tipoSinistro == null) {
        excecao.getMensagens().add("Código do tipo de sinistro inválido.");
    }

    if (!excecao.getMensagens().isEmpty()) {
        throw excecao;
    }

    Apolice apoliceVigente = null;
    for (Apolice apolice : daoApolice.listarTodos()) {
        if (apolice.getPlaca().equals(dados.getPlaca())) {
            LocalDateTime inicio = apolice.getDataInicioVigencia();
            LocalDateTime fim = inicio.plusYears(1);
            if (!dados.getDataHoraSinistro().isBefore(inicio) && dados.getDataHoraSinistro().isBefore(fim)) {
                apoliceVigente = apolice;
                break;
            }
        }
    }

    if (apoliceVigente == null) {
        excecao.getMensagens().add("Nenhuma apólice vigente encontrada para o veículo na data do sinistro.");
        throw excecao;
    }

    if (dados.getValorSinistro() > apoliceVigente.getValorMaximoCobertura()) {
        excecao.getMensagens().add("Valor do sinistro excede o valor máximo de cobertura da apólice.");
        throw excecao;
    }

    List<Sinistro> sinistrosApolice = daoSinistro.listarPorNumeroApolice(apoliceVigente.getNumero());

    int sequencial = 1;
    if (!sinistrosApolice.isEmpty()) {
        sinistrosApolice.sort(new ComparadorSinistroSequencial());

        Sinistro ultimo = sinistrosApolice.get(sinistrosApolice.size() - 1);
        sequencial = ultimo.getSequencial() + 1;
    }

    String numeroSinistro = "S" + apoliceVigente.getNumero() + String.format("%03d", sequencial);

    Sinistro sinistro = new Sinistro();
    sinistro.setNumero(numeroSinistro);
    sinistro.setDataHoraSinistro(dados.getDataHoraSinistro());
    sinistro.setPlaca(dados.getPlaca());
    sinistro.setUsuarioRegistro(dados.getUsuarioRegistro());
    sinistro.setValorSinistro(dados.getValorSinistro());
    sinistro.setTipoSinistro(tipoSinistro);
    sinistro.setNumeroApolice(apoliceVigente.getNumero());
    sinistro.setSequencial(sequencial);

    daoSinistro.incluir(sinistro);

    return numeroSinistro;
}
}