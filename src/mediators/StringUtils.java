package br.edu.cs.poo.ac.seguro.mediators;

public class StringUtils {

    private StringUtils() {}

    // Retorna true se a string for nula ou vazia após remover espaços
    public static boolean ehNuloOuBranco(String str) {
        return str == null || str.trim().isEmpty();
    }

    // Retorna true se a string contiver apenas números
    public static boolean temSomenteNumeros(String input) {
        return input != null && input.matches("\\d+");
    }
}
