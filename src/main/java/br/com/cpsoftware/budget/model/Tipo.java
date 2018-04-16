package br.com.cpsoftware.budget.model;

public abstract class Tipo {
	private String nome;
	private float valor_total;
	private float valor_parcial;
	
	public Tipo(String nome, float valor_total) {
		this.nome = nome;
		this.valor_total = valor_total;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public float getValor_total() {
		return valor_total;
	}

	public void setValor_total(float valor_total) {
		this.valor_total = valor_total;
	}

	public float getValor_parcial() {
		return valor_parcial;
	}

	public void setValor_parcial(float valor_parcial) {
		this.valor_parcial = valor_parcial;
	}
}
