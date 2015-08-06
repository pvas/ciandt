package br.com.ciandt.model;

public class Rota {

	private String id;
	private String origem;
	private String destino;
	private Integer distancia;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrigem() {
		return origem;
	}
	public void setOrigem(String origem) {
		this.origem = origem;
	}
	public String getDestino() {
		return destino;
	}
	public void setDestino(String destino) {
		this.destino = destino;
	}
	public Integer getDistancia() {
		return distancia;
	}
	public void setDistancia(Integer distancia) {
		this.distancia = distancia;
	}
	
	@Override
	public String toString() {
		return getOrigem() + " " + getDestino() + " " + getDistancia();
	}
}