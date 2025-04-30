package br.edu.cs.poo.ac.seguro.mediators;

public class ValidadorCpfCnpj {

    public static boolean ehCpfValido(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}")) return false;

        // Elimina CPFs com todos os dígitos iguais
        if (cpf.matches("(\\d)\\1{10}")) return false;

        int soma = 0, peso = 10;
        for (int i = 0; i < 9; i++) {
            soma += (cpf.charAt(i) - '0') * peso--;
        }
        int dig1 = 11 - (soma % 11);
        dig1 = dig1 > 9 ? 0 : dig1;

        soma = 0;
        peso = 11;
        for (int i = 0; i < 10; i++) {
            soma += (cpf.charAt(i) - '0') * peso--;
        }
        int dig2 = 11 - (soma % 11);
        dig2 = dig2 > 9 ? 0 : dig2;

        return cpf.charAt(9) - '0' == dig1 && cpf.charAt(10) - '0' == dig2;
    }

    public static boolean ehCnpjValido(String cnpj) {
        if (cnpj == null || !cnpj.matches("\\d{14}")) return false;

        // Elimina CNPJs com todos os dígitos iguais
        if (cnpj.matches("(\\d)\\1{13}")) return false;

        int[] peso1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] peso2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        int soma = 0;
        for (int i = 0; i < 12; i++) {
            soma += (cnpj.charAt(i) - '0') * peso1[i];
        }
        int dig1 = soma % 11;
        dig1 = dig1 < 2 ? 0 : 11 - dig1;

        soma = 0;
        for (int i = 0; i < 13; i++) {
            soma += (cnpj.charAt(i) - '0') * peso2[i];
        }
        int dig2 = soma % 11;
        dig2 = dig2 < 2 ? 0 : 11 - dig2;

        return cnpj.charAt(12) - '0' == dig1 && cnpj.charAt(13) - '0' == dig2;
    }
}
