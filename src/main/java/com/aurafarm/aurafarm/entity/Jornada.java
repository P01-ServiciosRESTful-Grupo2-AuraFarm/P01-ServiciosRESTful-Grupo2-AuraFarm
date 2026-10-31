package com.aurafarm.aurafarm.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Jornada extends DomainEntity{
	@NotBlank
	private String nombre;
	@NotBlank
	private String descripcion;
	@NotNull
	private LocalDate fecha;
	@NotNull
	@JsonFormat(pattern = "HH:mm:ss")
	private LocalTime horaInicio;
	@NotNull
	@JsonFormat(pattern = "HH:mm:ss")
	private LocalTime horaFin;
	@NotNull
	@Min(2)
	private int maxInscripciones;
	@NotNull
	private TipoPlazo plazo;
	
	@OneToMany
	private List<Enfrentamiento> enfrentamientos;

	public Jornada(@NotBlank String nombre, @NotBlank String descripcion, @NotNull LocalDate fecha,
			@NotNull LocalTime horaInicio, @NotNull LocalTime horaFin, @NotNull @Min(2) int maxInscripciones,
			@NotNull TipoPlazo plazo, List<Enfrentamiento> enfrentamientos) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.maxInscripciones = maxInscripciones;
		this.plazo = plazo;
		this.enfrentamientos = enfrentamientos;
	}
	
	

	public Jornada() {
		super();
	}




	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public LocalTime getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(LocalTime horaInicio) {
		this.horaInicio = horaInicio;
	}

	public LocalTime getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(LocalTime horaFin) {
		this.horaFin = horaFin;
	}

	public int getMaxInscripciones() {
		return maxInscripciones;
	}

	public void setMaxInscripciones(int maxInscripciones) {
		this.maxInscripciones = maxInscripciones;
	}

	public TipoPlazo getPlazo() {
		return plazo;
	}

	public void setPlazo(TipoPlazo plazo) {
		this.plazo = plazo;
	}

	public List<Enfrentamiento> getEnfrentamientos() {
		return enfrentamientos;
	}

	public void setEnfrentamientos(List<Enfrentamiento> enfrentamientos) {
		this.enfrentamientos = enfrentamientos;
	}
	
	
	
	
}
