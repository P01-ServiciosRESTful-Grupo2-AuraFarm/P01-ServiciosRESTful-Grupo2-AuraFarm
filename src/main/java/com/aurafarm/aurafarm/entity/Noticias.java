package com.aurafarm.aurafarm.entity;

import java.time.LocalDate;

import org.hibernate.validator.constraints.URL;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
@Entity
public class Noticias extends DomainEntity{
	@NotBlank
	@Size(max = 255)
	private String titulo;
	@NotBlank
	@Size(max = 2000)
	@Column(length = 2000)
	private String contenido;
	@NotNull
	private LocalDate fechaPublicacion;
	@URL
	@Size(max = 1000)
	@Column(length = 1000)
	private String imagen;
	
	
	public Noticias(@NotBlank String titulo, @NotBlank String contenido, @NotNull LocalDate fechaPublicacion,
			@URL String imagen) {
		super();
		this.titulo = titulo;
		this.contenido = contenido;
		this.fechaPublicacion = fechaPublicacion;
		this.imagen = imagen;
	}
	
	
	public Noticias() {
		super();
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
