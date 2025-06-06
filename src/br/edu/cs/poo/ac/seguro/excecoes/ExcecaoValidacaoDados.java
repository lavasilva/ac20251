package br.edu.cs.poo.ac.seguro.excecoes;

import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ExcecaoValidacaoDados extends Exception {

    private static final long serialVersionUID = 1L;

    private final List<String> mensagens;

    public ExcecaoValidacaoDados() {
        this.mensagens = new ArrayList<>();
    }

    public void adicionarMensagem(String mensagem) {
        mensagens.add(mensagem);
    }

    public boolean possuiErros() {
        return !mensagens.isEmpty();
    }

    @Override
    public String getMessage() {
        return String.join("\n", mensagens);
    }
}
