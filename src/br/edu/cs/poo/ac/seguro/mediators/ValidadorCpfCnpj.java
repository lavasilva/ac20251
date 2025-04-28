package br.edu.cs.poo.ac.seguro.br.edu.cs.poo.ac.seguro.mediators;

public class ValidadorCpfCnpj {

    public static boolean ehCnpjValido(String cnpj) {
        if (StringUtils.ehNuloOuBranco(cnpj) || !StringUtils.temSomenteNumeros(cnpj)) {
            return false;
        }

        if (cnpj.length() != 14) return false;

        if (cnpj.chars().distinct().count() == 1) return false;

        int[] pesos1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] pesos2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        int soma1 = 0, soma2 = 0;
        for (int i = 0; i < 12; i++) {
            int digito = Character.getNumericValue(cnpj.charAt(i));
            soma1 += digito * pesos1[i];
            soma2 += digito * pesos2[i];
        }

        int digito1 = soma1 % 11 < 2 ? 0 : 11 - (soma1 % 11);
        soma2 += digito1 * pesos2[12];
        int digito2 = soma2 % 11 < 2 ? 0 : 11 - (soma2 % 11);

        return cnpj.endsWith("" + digito1 + digito2);
    }

    public static boolean ehCpfValido(String cpf) {
        if (StringUtils.ehNuloOuBranco(cpf) || !StringUtils.temSomenteNumeros(cpf)) {
            return false;
        }

        if (cpf.length() != 11) return false;

        if (cpf.chars().distinct().count() == 1) return false;

        int soma1 = 0, soma2 = 0;
        for (int i = 0; i < 9; i++) {
            int digito = Character.getNumericValue(cpf.charAt(i));
            soma1 += digito * (10 - i);
            soma2 += digito * (11 - i);
        }

        int digito1 = soma1 % 11 < 2 ? 0 : 11 - (soma1 % 11);
        soma2 += digito1 * 2;
        int digito2 = soma2 % 11 < 2 ? 0 : 11 - (soma2 % 11);

        return cpf.endsWith("" + digito1 + digito2);
    }
}
