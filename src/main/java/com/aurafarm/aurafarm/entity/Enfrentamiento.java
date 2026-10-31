package com.aurafarm.aurafarm.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

@Entity
public class Enfrentamiento extends DomainEntity {
	@NotNull
	private TipoEstado estado;

	
	public Enfrentamiento() {
		super();
	}

	public Enfrentamiento(@NotNull TipoEstado estado) {
		this.estado = estado;
	}

	public TipoEstado getEstado() {
		return estado;
	}

	public void setEstado(TipoEstado estado) {
		this.estado = estado;
	}
	
}
