package br.edu.cs.poo.ac.seguro.daos;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.edu.cs.poo.ac.seguro.entidades.Apolice;

public class ApoliceDAO extends DAOGenerico<Apolice> {

    @Override
    protected Class<Apolice> getClasseEntidade() {
        return Apolice.class;
    }
}