package com.aurafarm.aurafarm.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
public abstract class Actor extends DomainEntity {
	@NotBlank
	private String nombreUsuario;

	@URL
	private String foto;

	@NotBlank
	@Email
	private String email;

	@NotBlank
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String contrasena;

	public Actor() {
		super();
	}

	public Actor(@NotBlank String nombreUsuario, String foto, @NotBlank @Email String email,
			@NotBlank String contrasena) {
		super();
		this.nombreUsuario = nombreUsuario;
		this.foto = foto;
		this.email = email;
		this.contrasena = contrasena;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
}
