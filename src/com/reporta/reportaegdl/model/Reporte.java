package com.reporta.reportaegdl.model;

import java.text.NumberFormat;

public class Reporte {
	private int id;
	private String titulo;
	private String mensaje;
	private double coordenadas;
	private String tipo;
	private String image;
	
	public Reporte() {
	}

	public Reporte(String titulo) {
		this.titulo = titulo;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return titulo;
	}
	
	
	public void setTitle(String titulo) {
		this.titulo = titulo;
	}
	public String getDescription() {
		return mensaje;
	}
	public void setDescription(String mensaje) {
		this.mensaje = mensaje;
	}
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
	public double getCoordenadas() {
		return coordenadas;
	}
	public void setCoordenadas(double coordenadas) {
		this.coordenadas = coordenadas;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Override
	public String toString() {
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		return titulo + "\n(" + nf.format(coordenadas) + ")";
	}
}
