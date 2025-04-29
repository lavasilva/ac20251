package br.edu.cs.poo.ac.seguro.mediators;

import br.edu.cs.poo.ac.seguro.daos.SeguradoPessoaDAO;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;

public class SeguradoPessoaMediator {
    private static SeguradoPessoaMediator instancia = new SeguradoPessoaMediator(); // instância única
    private SeguradoMediator seguradoMediator = SeguradoMediator.obterInstancia();
    private SeguradoPessoaDAO seguradoPessoaDAO = new SeguradoPessoaDAO();

    private SeguradoPessoaMediator() {
    }

    public static SeguradoPessoaMediator obterInstancia() {
        return instancia;
    }

    public String validarCpf(String cpf) {
        if (StringUtils.ehNuloOuBranco(cpf)) {
            return "CPF não pode ser vazio.";
        }
        if (!ValidadorCpfCnpj.ehCpfValido(cpf)) {
            return "CPF inválido.";
        }
        return null;
    }

    public String validarRenda(double renda) {
        return renda <= 0 ? "Renda deve ser maior que zero." : null;
    }

    public String incluirSeguradoPessoa(SeguradoPessoa seg) {
        String msg = validarSeguradoPessoa(seg);
        if (msg != null) return msg;

        return seguradoPessoaDAO.incluir(seg);
    }

    public String alterarSeguradoPessoa(SeguradoPessoa seg) {
        String msg = validarSeguradoPessoa(seg);
        if (msg != null) return msg;

        return seguradoPessoaDAO.alterar(seg);
    }

    public String excluirSeguradoPessoa(String cpf) {
        return seguradoPessoaDAO.excluir(cpf);
    }

    public SeguradoPessoa buscarSeguradoPessoa(String cpf) {
        return seguradoPessoaDAO.buscar(cpf);
    }

    public String validarSeguradoPessoa(SeguradoPessoa seg) {
        String msg;

        msg = seguradoMediator.validarNome(seg.getNome());
        if (msg != null) return msg;

        msg = seguradoMediator.validarTelefone(seg.getTelefone());
        if (msg != null) return msg;

        msg = validarCpf(seg.getCpf());
        if (msg != null) return msg;

        msg = validarRenda(seg.getRenda());
        if (msg != null) return msg;

        return null;
    }
}
