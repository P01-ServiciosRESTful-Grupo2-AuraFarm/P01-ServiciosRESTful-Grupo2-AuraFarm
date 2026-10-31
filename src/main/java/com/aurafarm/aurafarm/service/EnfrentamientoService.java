package com.aurafarm.aurafarm.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aurafarm.aurafarm.entity.Enfrentamiento;
import com.aurafarm.aurafarm.entity.Jornada;
import com.aurafarm.aurafarm.entity.TipoEstado;
import com.aurafarm.aurafarm.entity.Usuario;
import com.aurafarm.aurafarm.repository.EnfrentamientoRepository;

import jakarta.transaction.Transactional;

@Service
public class EnfrentamientoService {
	@Autowired
	private EnfrentamientoRepository enfrentamientoRepository;

	@Autowired
	private UsuarioService usuarioService;
	
	@Transactional
	public List<Enfrentamiento> generarCruces(Jornada jornada) {
		if (!enfrentamientoRepository.encontrarPorJornada(jornada.getId()).isEmpty()) {
			return null;
		}

		List<Usuario> inscritos = new ArrayList<>(usuarioService.getInscritosByJornada(jornada.getId()));
		Collections.shuffle(inscritos);

		if (jornada.getEnfrentamientos() == null) {
			jornada.setEnfrentamientos(new ArrayList<>());
		}

		List<Enfrentamiento> cruces = new ArrayList<>();
		for (int i = 0; i < inscritos.size(); i += 2) {
			Usuario u1 = inscritos.get(i);
			Usuario u2 = (i + 1 < inscritos.size()) ? inscritos.get(i + 1) : null;

			Enfrentamiento e = new Enfrentamiento(u2 == null ? TipoEstado.NO_FINALIZADA : TipoEstado.PENDIENTE);
			e = enfrentamientoRepository.save(e);

			anadirEnfrentamiento(u1, e);
			if (u2 != null) {
				anadirEnfrentamiento(u2, e);
			}
			jornada.getEnfrentamientos().add(e);
			cruces.add(e);
		}
		return cruces;
	}

	@Transactional
	public boolean eliminarCruces(Jornada jornada) {
		List<Enfrentamiento> cruces = enfrentamientoRepository.encontrarPorJornada(jornada.getId());
		for (Enfrentamiento e : cruces) {
			if (e.getEstado() == TipoEstado.FINALIZADA) {
				return false;
			}
		}
		for (Enfrentamiento e : cruces) {
			for (Usuario u : usuarioService.getParticipantesByEnfrentamiento(e.getId())) {
				u.getEnfrentamientos().removeIf(x -> x.getId() == e.getId());
				usuarioService.save(u);
			}
			jornada.getEnfrentamientos().removeIf(x -> x.getId() == e.getId());
			enfrentamientoRepository.delete(e);
		}
		return true;
	}
	
	private void anadirEnfrentamiento(Usuario u, Enfrentamiento e) {
		if (u.getEnfrentamientos() == null) {
			u.setEnfrentamientos(new ArrayList<>());
		}
		u.getEnfrentamientos().add(e);
		usuarioService.save(u);
	}
}