package com.aurafarm.aurafarm.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aurafarm.aurafarm.entity.Actor;
import com.aurafarm.aurafarm.repository.ActorRepository;

@Service
public class ActorService {
	@Autowired
	private ActorRepository actorRepository;

	public Optional<Actor> findById(int id) {
		return actorRepository.findById(id);
	}

	public int comprobarDatosUnicos(String email, String nombreUsuario, int idPropio) {
		int res = 0;
		Optional<Actor> porEmail = actorRepository.findByEmail(email);
		Optional<Actor> porNombre = actorRepository.findByNombreUsuario(nombreUsuario);
		if (porEmail.isPresent() && porEmail.get().getId() != idPropio) {
			res = 1;
		} else if (porNombre.isPresent() && porNombre.get().getId() != idPropio) {
			res = 2;
		}
		return res;
	}

	public Actor login(String email, String contrasena) {
		Optional<Actor> actorOpt = actorRepository.findByEmail(email);
		Actor res = null;
		if (actorOpt.isPresent() && actorOpt.get().getContrasena().equals(contrasena)) {
			res = actorOpt.get();
		}
		return res;
	}
}