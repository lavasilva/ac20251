package br.edu.cs.poo.ac.seguro.entidades;


public enum TipoSinistro {	
	COLISAO(1,"Colisão"),
	INCENDIO(2,"Incêndio"),
	FURTO(3, "Furto"),
	ENCHENTE(4, "Enchente"),
	DEPREDACAO(5, "Depredação");
	
	private int codigo;
	private String nome;
	
	private TipoSinistro(int codigo, String nome) {
		this.codigo = codigo;
		this.nome = nome;
	}
	
	public int getCodigo() {
		return codigo;
	}
	
	public String getNome() {
		return nome;
	}
	
	public static TipoSinistro getTipoSinistro(int codigo) {
		TipoSinistro[] tipos = TipoSinistro.values();
		for (int i=0; i<tipos.length; i++) {
	        if (tipos[i].getCodigo() == codigo) {
	        	return tipos[i];
	        }
		}
		return null;
	}
			

}