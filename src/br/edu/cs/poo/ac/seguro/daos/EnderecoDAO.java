package br.edu.cs.poo.ac.seguro.daos;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.edu.cs.poo.ac.seguro.entidades.Endereco;

public class EnderecoDAO extends DAOGenerico {
    public EnderecoDAO() {
        cadastro = new CadastroObjetos(Endereco.class);
    }

    public Endereco buscar(String cep) {
        return (Endereco) cadastro.buscar(cep);
    }

    public boolean incluir(Endereco endereco) {
        if (buscar(endereco.getCep()) != null) {
            return false;
        } else {
            cadastro.incluir(endereco, endereco.getCep());
            return true;
        }
    }

    public boolean alterar(Endereco endereco) {
        if (buscar(endereco.getCep()) == null) {
            return false;
        } else {
            cadastro.alterar(endereco, endereco.getCep());
            return true;
        }
    }

    public boolean excluir(String cep) {
        if (buscar(cep) == null) {
            return false;
        } else {
            cadastro.excluir(cep);
            return true;
        }
    }
}
