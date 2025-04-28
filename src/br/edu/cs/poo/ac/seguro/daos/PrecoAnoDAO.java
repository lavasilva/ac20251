package br.edu.cs.poo.ac.seguro.daos;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.edu.cs.poo.ac.seguro.entidades.PrecoAno;

public class PrecoAnoDAO extends DAOGenerico {
    public PrecoAnoDAO() {
        cadastro = new CadastroObjetos(PrecoAno.class);
    }

    public PrecoAno buscar(int ano) {
        return (PrecoAno) cadastro.buscar(String.valueOf(ano));
    }

    public boolean incluir(PrecoAno precoAno) {
        if (buscar(precoAno.getAno()) != null) {
            return false;
        } else {
            cadastro.incluir(precoAno, String.valueOf(precoAno.getAno()));
            return true;
        }
    }

    public boolean alterar(PrecoAno precoAno) {
        if (buscar(precoAno.getAno()) == null) {
            return false;
        } else {
            cadastro.alterar(precoAno, String.valueOf(precoAno.getAno()));
            return true;
        }
    }

    public boolean excluir(int ano) {
        if (buscar(ano) == null) {
            return false;
        } else {
            cadastro.excluir(String.valueOf(ano));
            return true;
        }
    }
}
