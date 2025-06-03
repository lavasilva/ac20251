package br.edu.cs.poo.ac.seguro.daos;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;

public class SeguradoPessoaDAO extends DAOGenerico<SeguradoPessoa> {

    @Override
    protected Class<SeguradoPessoa> getClasseEntidade() {
        return SeguradoPessoa.class;
    }
}
