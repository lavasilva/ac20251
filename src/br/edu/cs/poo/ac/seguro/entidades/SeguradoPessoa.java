package br.edu.cs.poo.ac.seguro.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class SeguradoPessoa extends Segurado implements Serializable, Registro {

    private String cpf;
    private double renda;

    public SeguradoPessoa(String nome, Endereco endereco, LocalDate dataNascimento,
                          BigDecimal bonus, String cpf, double renda)
    {
        super(Objects.requireNonNull(nome),
                Objects.requireNonNull(endereco),
                Objects.requireNonNull(dataNascimento),
                Objects.requireNonNull(bonus));
        this.cpf = Objects.requireNonNull(cpf);
        this.renda = renda;
    }

    public double getRenda() {
        return renda;
    }

    public void setRenda(double renda) {
        this.renda = renda;
    }

    public LocalDate getDataNascimento() {
        return super.getDataCriacao();
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        super.setDataCriacao(dataNascimento);
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public boolean isEmpresa() {
        return false;
    }

    @Override
    public String getIdUnico() {
        return cpf;
    }

}
