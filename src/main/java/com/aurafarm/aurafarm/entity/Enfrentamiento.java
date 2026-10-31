package com.aurafarm.aurafarm.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
@Entity
public class Enfrentamiento extends DomainEntity {
	@NotBlank
	private TipoEstado estado;

	public Enfrentamiento(@NotBlank TipoEstado estado) {
		this.estado = estado;
	}

	public TipoEstado getEstado() {
		return estado;
	}

	public void setEstado(TipoEstado estado) {
		this.estado = estado;
	}
	
}
