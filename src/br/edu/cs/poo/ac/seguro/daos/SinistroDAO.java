package br.edu.cs.poo.ac.seguro.daos;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.edu.cs.poo.ac.seguro.entidades.Sinistro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SinistroDAO extends DAOGenerico<Sinistro> {

    @Override
    protected Class<Sinistro> getClasseEntidade() {
        return Sinistro.class;
    }
}