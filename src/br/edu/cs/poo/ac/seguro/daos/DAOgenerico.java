package br.edu.cs.poo.ac.seguro.daos;

import br.edu.cs.poo.ac.seguro.entidades.Registro;
import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public abstract class DAOGenerico<T extends Registro> {

    private CadastroObjetos cadastro;

    public DAOGenerico() {
        this.cadastro = new CadastroObjetos(getClasseEntidade());
    }

    protected abstract Class<T> getClasseEntidade();

    public T buscar(String id) {
        return (T) cadastro.buscar(id);
    }

    public boolean incluir(T entidade) {
        if (buscar(entidade.getIdUnico()) != null) {
            return false;
        }
        cadastro.incluir(entidade, entidade.getIdUnico());
        return true;
    }

    public boolean alterar(T entidade) {
        if (buscar(entidade.getIdUnico()) == null) {
            return false;
        }
        cadastro.alterar(entidade, entidade.getIdUnico());
        return true;
    }

    public boolean excluir(String id) {
        if (buscar(id) == null) {
            return false;
        }
        cadastro.excluir(id);
        return true;
    }

    @SuppressWarnings("unchecked")
    public T[] buscarTodos() {
        Serializable[] objetos = cadastro.buscarTodos();
        List<T> lista = new ArrayList<>();

        for (Serializable obj : objetos) {
            lista.add((T) obj); // cast seguro item a item
        }

        // Cria um array do tipo T do tamanho da lista
        T[] array = (T[]) Array.newInstance(getClasseEntidade(), lista.size());
        return lista.toArray(array);
    }
}
