package br.edu.cs.poo.ac.seguro.mediators;

import java.math.BigDecimal;
import java.time.LocalDate;
import br.edu.cs.poo.ac.seguro.entidades.Endereco;

public class SeguradoMediator {

    private static SeguradoMediator instancia = new SeguradoMediator();

    private SeguradoMediator() {}

    public static SeguradoMediator obterInstancia() {
        return instancia;
    }

    public String validarNome(String nome) {
        if (StringUtils.ehNuloOuBranco(nome)) {
            return "Nome não informado";
        }
        return null;
    }

    public String validarEndereco(Endereco endereco) {
        if (endereco == null) {
            return "Endereço não informado";
        }
        if (StringUtils.ehNuloOuBranco(endereco.getLogradouro()) ||
                StringUtils.ehNuloOuBranco(endereco.getNumero()) ||
                StringUtils.ehNuloOuBranco(endereco.getCidade()) ||
                StringUtils.ehNuloOuBranco(endereco.getCep())) {
            return "Endereço incompleto";
        }
        return null;
    }

    public String validarDataCriacao(LocalDate dataCriacao) {
        if (dataCriacao == null) {
            return "Data de criação não informada";
        }
        if (dataCriacao.isAfter(LocalDate.now())) {
            return "Data de criação no futuro";
        }
        return null;
    }

    public BigDecimal ajustarDebitoBonus(BigDecimal bonus, BigDecimal valorDebito) {
        if (bonus == null || valorDebito == null) {
            return BigDecimal.ZERO;
        }
        if (valorDebito.compareTo(bonus) > 0) {
            return BigDecimal.ZERO;
        }
        return bonus.subtract(valorDebito);
    }
}
