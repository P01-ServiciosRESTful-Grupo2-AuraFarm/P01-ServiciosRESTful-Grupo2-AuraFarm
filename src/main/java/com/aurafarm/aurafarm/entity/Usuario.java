package com.aurafarm.aurafarm.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Usuario extends Actor {
	private int victorias;

	public Usuario() {
		super();
	}

	public Usuario(@NotBlank String nombreUsuario, String foto, @NotBlank @Email String email,
			@NotBlank String contrasena) {
		super(nombreUsuario, foto, email, contrasena);
	}

	public int getVictorias() {
		return victorias;
	}

	public void setVictorias(int victorias) {
		this.victorias = victorias;
	}
}
