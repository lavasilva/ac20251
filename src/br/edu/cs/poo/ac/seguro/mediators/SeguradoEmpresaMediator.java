package br.edu.cs.poo.ac.seguro.mediators;

import br.edu.cs.poo.ac.seguro.daos.SeguradoEmpresaDAO;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoEmpresa;

public class SeguradoEmpresaMediator {
    private SeguradoMediator seguradoMediator = SeguradoMediator.obterInstancia();
    private SeguradoEmpresaDAO dao = new SeguradoEmpresaDAO();

    private static SeguradoEmpresaMediator instancia = new SeguradoEmpresaMediator();
    public static SeguradoEmpresaMediator obterInstancia() {
        return instancia;
    }

    public String validarCnpj(String cnpj) {
        if (StringUtils.ehNuloOuBranco(cnpj)) {
            return "CNPJ nulo ou em branco";
        }
        if (!ValidadorCpfCnpj.ehCnpjValido(cnpj)) {
            return "CNPJ inválido";
        }
        return null;
    }

    public String validarFaturamento(double faturamento) {
        if (faturamento < 0) {
            return "Faturamento não pode ser negativo";
        }
        return null;
    }

    public String validarSeguradoEmpresa(SeguradoEmpresa seg) {
        String msg = validarCnpj(seg.getCnpj());
        if (msg != null) return msg;

        msg = validarFaturamento(seg.getFaturamento());
        if (msg != null) return msg;

        msg = seguradoMediator.validarNome(seg.getNome());
        if (msg != null) return msg;

        msg = seguradoMediator.validarEndereco(seg.getEndereco());
        if (msg != null) return msg;

        msg = seguradoMediator.validarDataCriacao(seg.getDataCriacao());
        if (msg != null) return msg;

        return null;
    }

    public String incluirSeguradoEmpresa(SeguradoEmpresa seg) {
        String msg = validarSeguradoEmpresa(seg);
        if (msg != null) return msg;

        boolean sucesso = dao.incluir(seg);
        return sucesso ? null : "Já existe um segurado com este CNPJ";
    }

    public String alterarSeguradoEmpresa(SeguradoEmpresa seg) {
        String msg = validarSeguradoEmpresa(seg);
        if (msg != null) return msg;

        boolean sucesso = dao.alterar(seg);
        return sucesso ? null : "Segurado com este CNPJ não encontrado";
    }

    public String excluirSeguradoEmpresa(String cnpj) {
        boolean sucesso = dao.excluir(cnpj);
        return sucesso ? null : "Segurado com este CNPJ não encontrado";
    }

    public SeguradoEmpresa buscarSeguradoEmpresa(String cnpj) {
        return dao.buscar(cnpj);
    }
}
