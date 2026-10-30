package com.aurafarm.aurafarm.entity;

import java.time.LocalDate;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotBlank;

public class Noticias extends DomainEntity{
	@NotBlank
	private String titulo;
	@NotBlank
	private String contenido;
	@NotBlank
	private LocalDate fechaPublicacion;
	@URL
	private String imagen;
	
	
	
	public Noticias(@NotBlank String titulo, @NotBlank String contenido, @NotBlank LocalDate fechaPublicacion,
			@URL String imagen) {
		super();
		this.titulo = titulo;
		this.contenido = contenido;
		this.fechaPublicacion = fechaPublicacion;
		this.imagen = imagen;
	}
	
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getContenido() {
		return contenido;
	}
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	public LocalDate getFechaPublicacion() {
		return fechaPublicacion;
	}
	public void setFechaPublicacion(LocalDate fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
	
}
