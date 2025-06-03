package br.edu.cs.poo.ac.seguro.daos;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;

public class SeguradoPessoaDAO extends DAOGenerico<SeguradoPessoa> {

    @Override
    protected Class<SeguradoPessoa> getClasseEntidade() {
        return SeguradoPessoa.class;
    }

    public SeguradoPessoa buscar(String cpf) {
        return (SeguradoPessoa) super.buscar(cpf);
    }

    public boolean incluir(SeguradoPessoa pessoa) {
        return super.incluir(pessoa);
    }

    public boolean alterar(SeguradoPessoa pessoa) {
        return super.alterar(pessoa);
    }

    public boolean excluir(String cpf) {
        return super.excluir(cpf);
    }

}
