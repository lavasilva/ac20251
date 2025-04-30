package br.edu.cs.poo.ac.seguro.entidades;

import java.util.Objects;

public class Veiculo {
    private String placa;
    private String marcaModelo;
    private int anoFabricacao;
    private CategoriaVeiculo categoriaVeiculo;
    private SeguradoPessoa seguradoPessoa;
    private SeguradoEmpresa seguradoEmpresa;

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarcaModelo() {
        return marcaModelo;
    }

    public void setMarcaModelo(String marcaModelo) {
        this.marcaModelo = marcaModelo;
    }

    public int getAnoFabricacao() {
        return anoFabricacao;
    }

    public void setAnoFabricacao(int anoFabricacao) {
        this.anoFabricacao = anoFabricacao;
    }

    public CategoriaVeiculo getCategoriaVeiculo() {
        return categoriaVeiculo;
    }

    public void setCategoriaVeiculo(CategoriaVeiculo categoriaVeiculo) {
        this.categoriaVeiculo = categoriaVeiculo;
    }

    public SeguradoPessoa getSeguradoPessoa() {
        return seguradoPessoa;
    }

    public void setSeguradoPessoa(SeguradoPessoa seguradoPessoa) {
        this.seguradoPessoa = seguradoPessoa;
    }

    public SeguradoEmpresa getSeguradoEmpresa() {
        return seguradoEmpresa;
    }

    public void setSeguradoEmpresa(SeguradoEmpresa seguradoEmpresa) {
        this.seguradoEmpresa = seguradoEmpresa;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Veiculo veiculo = (Veiculo) obj;
        return Objects.equals(placa, veiculo.placa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(placa);
    }
}
