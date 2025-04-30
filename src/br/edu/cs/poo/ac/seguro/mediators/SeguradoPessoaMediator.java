package br.edu.cs.poo.ac.seguro.mediators;

import br.edu.cs.poo.ac.seguro.daos.SeguradoPessoaDAO;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;

public class SeguradoPessoaMediator {
    private SeguradoMediator seguradoMediator = SeguradoMediator.obterInstancia();
    private SeguradoPessoaDAO dao = new SeguradoPessoaDAO();

    private static SeguradoPessoaMediator instancia = new SeguradoPessoaMediator();
    public static SeguradoPessoaMediator obterInstancia() {
        return instancia;
    }

    public String validarCpf(String cpf) {
        if (StringUtils.ehNuloOuBranco(cpf)) {
            return "CPF nulo ou em branco";
        }
        if (!ValidadorCpfCnpj.ehCpfValido(cpf)) {
            return "CPF inválido";
        }
        return null;
    }

    public String validarRenda(double renda) {
        if (renda < 0) {
            return "Renda não pode ser negativa";
        }
        return null;
    }

    public String validarSeguradoPessoa(SeguradoPessoa seg) {
        String msg = validarCpf(seg.getCpf());
        if (msg != null) return msg;

        msg = validarRenda(seg.getRenda());
        if (msg != null) return msg;

        msg = seguradoMediator.validarNome(seg.getNome());
        if (msg != null) return msg;

        msg = seguradoMediator.validarEndereco(seg.getEndereco());
        if (msg != null) return msg;

        msg = seguradoMediator.validarDataCriacao(seg.getDataCriacao());
        if (msg != null) return msg;

        return null;
    }

    public String incluirSeguradoPessoa(SeguradoPessoa seg) {
        String msg = validarSeguradoPessoa(seg);
        if (msg != null) return msg;

        boolean sucesso = dao.incluir(seg);
        return sucesso ? null : "Já existe um segurado com este CPF";
    }

    public String alterarSeguradoPessoa(SeguradoPessoa seg) {
        String msg = validarSeguradoPessoa(seg);
        if (msg != null) return msg;

        boolean sucesso = dao.alterar(seg);
        return sucesso ? null : "Segurado com este CPF não encontrado";
    }

    public String excluirSeguradoPessoa(String cpf) {
        boolean sucesso = dao.excluir(cpf);
        return sucesso ? null : "Segurado com este CPF não encontrado";
    }

    public SeguradoPessoa buscarSeguradoPessoa(String cpf) {
        return dao.buscar(cpf);
    }
}
