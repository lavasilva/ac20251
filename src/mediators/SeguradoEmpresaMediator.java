package br.edu.cs.poo.ac.seguro.mediators;

import br.edu.cs.poo.ac.seguro.daos.SeguradoEmpresaDAO;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoEmpresa;

public class SeguradoEmpresaMediator {
    private SeguradoMediator seguradoMediator = new SeguradoMediator();

    public String validarCnpj(String cnpj) {
        if (StringUtils.ehNuloOuBranco(cnpj)) {
            return "CNPJ não pode ser vazio.";
        }
        if (!ValidadorCpfCnpj.ehCnpjValido(cnpj)) {
            return "CNPJ inválido.";
        }
        return null;
    }

    public String validarFaturamento(double faturamento) {
        return faturamento <= 0 ? "Faturamento deve ser maior que zero." : null;
    }

    public String incluirSeguradoEmpresa(SeguradoEmpresa seg) {
        String msg = validarSeguradoEmpresa(seg);
        if (msg != null) return msg;

        return new SeguradoEmpresaDAO().incluir(seg);
    }

    public String alterarSeguradoEmpresa(SeguradoEmpresa seg) {
        String msg = validarSeguradoEmpresa(seg);
        if (msg != null) return msg;

        return new SeguradoEmpresaDAO().alterar(seg);
    }

    public String excluirSeguradoEmpresa(String cnpj) {
        return new SeguradoEmpresaDAO().excluir(cnpj);
    }

    public SeguradoEmpresa buscarSeguradoEmpresa(String cnpj) {
        return new SeguradoEmpresaDAO().buscar(cnpj);
    }

    public String validarSeguradoEmpresa(SeguradoEmpresa seg) {
        String msg;

        msg = seguradoMediator.validarNome(seg.getNome());
        if (msg != null) return msg;

        msg = seguradoMediator.validarTelefone(seg.getTelefone());
        if (msg != null) return msg;

        msg = validarCnpj(seg.getCnpj());
        if (msg != null) return msg;

        msg = validarFaturamento(seg.getFaturamento());
        if (msg != null) return msg;

        return null;
    }
}
