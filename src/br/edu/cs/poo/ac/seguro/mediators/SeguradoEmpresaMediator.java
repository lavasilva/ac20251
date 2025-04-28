package br.edu.cs.poo.ac.seguro.br.edu.cs.poo.ac.seguro.mediators;

import br.edu.cs.poo.ac.seguro.daos.SeguradoEmpresaDAO;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoEmpresa;

public class SeguradoEmpresaMediator {
    private SeguradoMediator seguradoMediator = SeguradoMediator.instancia();
    private SeguradoEmpresaDAO seguradoEmpresaDAO = new SeguradoEmpresaDAO();

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

    public String incluirSeguradoEmpresa(SeguradoEmpresa seg) {
        String msg = validarSeguradoEmpresa(seg);
        if (msg != null) return msg;

        return seguradoEmpresaDAO.incluir(seg);
    }

    public String alterarSeguradoEmpresa(SeguradoEmpresa seg) {
        String msg = validarSeguradoEmpresa(seg);
        if (msg != null) return msg;

        return seguradoEmpresaDAO.alterar(seg);
    }

}
