package com.aurafarm.aurafarm.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Usuario extends Actor {
	private int victorias;

	@ManyToMany
	private List<Jornada> jornadas;

	@ManyToMany
	private List<Enfrentamiento> enfrentamientos;

	@OneToMany
	private List<Enfrentamiento> enfrentamientosGanados;

	public Usuario(@NotBlank String nombreUsuario, String foto, @NotBlank @Email String email,
			@NotBlank String contrasena, int victorias, List<Jornada> jornadas,
			List<Enfrentamiento> enfrentamientos, List<Enfrentamiento> enfrentamientosGanados) {
		super(nombreUsuario, foto, email, contrasena);
		this.victorias = victorias;
		this.jornadas = jornadas;
		this.enfrentamientos = enfrentamientos;
		this.enfrentamientosGanados = enfrentamientosGanados;
	}

	public Usuario(@NotBlank String nombreUsuario, String foto, @NotBlank @Email String email,
			@NotBlank String contrasena) {
		super(nombreUsuario, foto, email, contrasena);
		this.jornadas = new ArrayList<>();
		this.enfrentamientos = new ArrayList<>();
		this.enfrentamientosGanados = new ArrayList<>();
	}

	public Usuario() {
		super();
	}

	public int getVictorias() {
		return victorias;
	}

	public void setVictorias(int victorias) {
		this.victorias = victorias;
	}

	public List<Jornada> getJornadas() {
		return jornadas;
	}

	public void setJornadas(List<Jornada> jornadas) {
		this.jornadas = jornadas;
	}

	public List<Enfrentamiento> getEnfrentamientos() {
		return enfrentamientos;
	}

	public void setEnfrentamientos(List<Enfrentamiento> enfrentamientos) {
		this.enfrentamientos = enfrentamientos;
	}

	public List<Enfrentamiento> getEnfrentamientosGanados() {
		return enfrentamientosGanados;
	}

	public void setEnfrentamientosGanados(List<Enfrentamiento> enfrentamientosGanados) {
		this.enfrentamientosGanados = enfrentamientosGanados;
	}
}