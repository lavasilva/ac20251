package br.edu.cs.poo.ac.seguro.br.edu.cs.poo.ac.seguro.mediators;

import java.math.BigDecimal;
import java.time.LocalDate;
import br.edu.cs.poo.ac.seguro.entidades.Endereco;

public class SeguradoMediator {
	private static SeguradoMediator instancia = new SeguradoMediator(); // instância única

	private SeguradoMediator() {
	}

	public static SeguradoMediator obterInstancia() {
		return instancia;
	}

	public String validarNome(String nome) {
		return null;
	}

	public String validarEndereco(Endereco endereco) {
		return null;
	}

	public String validarDataCriacao(LocalDate dataCriacao) {
		return null;
	}

	public BigDecimal ajustarDebitoBonus(BigDecimal bonus, BigDecimal valorDebito) {
		return null;
	}
}
