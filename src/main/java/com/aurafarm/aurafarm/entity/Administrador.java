package com.aurafarm.aurafarm.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Administrador extends Actor {

	public Administrador() {
		super();
	}

	public Administrador(@NotBlank String nombreUsuario, String foto, @NotBlank @Email String email,
			@NotBlank String contrasena) {
		super(nombreUsuario, foto, email, contrasena);
	}
}
