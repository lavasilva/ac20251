package br.edu.cs.poo.ac.seguro.daos;

import br.edu.cs.poo.ac.seguro.entidades.Segurado;

public class SeguradoDAO extends DAOGenerico<Segurado {
    @Override
    public abstract Class<? extends Segurado> getClasseEntidade();
}
