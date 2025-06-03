package br.edu.cs.poo.ac.seguro.daos;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.edu.cs.poo.ac.seguro.entidades.Registro;

public abstract class DAOGenerico<T extends Registro> {

    protected CadastroObjetos cadastro;

    public DAOGenerico() {
        cadastro = new CadastroObjetos(getClasseEntidade());
    }

    public abstract Class<T> getClasseEntidade();

    public T buscar(String idUnico) {
        return (T) cadastro.buscar(idUnico);
    }

    public List<T> buscarTodos() {
        Serializable[] objs = cadastro.buscarTodos();
        List<T> lista = new ArrayList<>();
        for (Serializable obj : objs) {
            lista.add((T) obj);
        }
        return lista;
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

    public boolean excluir(String idUnico) {
        if (buscar(idUnico) == null) {
            return false;
        }
        cadastro.excluir(idUnico);
        return true;
    }
}