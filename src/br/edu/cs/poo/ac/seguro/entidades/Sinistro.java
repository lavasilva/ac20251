package br.edu.cs.poo.ac.seguro.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor

public class Sinistro implements Serializable, Registro {
    private String numero;
    private Veiculo veiculo;
    private LocalDateTime dataHoraSinistro;
    private LocalDateTime dataHoraRegistro;
    private String usuarioRegistro;
    private BigDecimal valorSinistro;
    private TipoSinistro tipo;

    private int sequencial;
    private String numeroApolice;

    @Override
    public String getIdUnico() {
        return numero;
    }
}