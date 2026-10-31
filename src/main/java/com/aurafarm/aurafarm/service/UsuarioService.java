package com.aurafarm.aurafarm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aurafarm.aurafarm.entity.Enfrentamiento;
import com.aurafarm.aurafarm.entity.Jornada;
import com.aurafarm.aurafarm.entity.Usuario;
import com.aurafarm.aurafarm.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private ActorService actorService;

	public Optional<Usuario> findById(int id) {
		return usuarioRepository.findById(id);
	}

	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
	}

	@Transactional
	public Usuario create(Usuario u) {
		Usuario res = null;
		if (actorService.comprobarDatosUnicos(u.getEmail(), u.getNombreUsuario(), -1) == 0) {
			u.setId(0);
			u.setVersion(0);
			u.setVictorias(0);
			u.setJornadas(new ArrayList<>());
			u.setEnfrentamientos(new ArrayList<>());
			u.setEnfrentamientosGanados(new ArrayList<>());
			res = usuarioRepository.save(u);
		}
		return res;
	}

	@Transactional
	public int update(int id, Usuario datos) {
		int res = 1;
		Optional<Usuario> usuarioOpt = findById(id);
		if (usuarioOpt.isPresent()) {
			if (actorService.comprobarDatosUnicos(datos.getEmail(), datos.getNombreUsuario(), id) == 0) {
				Usuario usuario = usuarioOpt.get();
				usuario.setNombreUsuario(datos.getNombreUsuario());
				usuario.setFoto(datos.getFoto());
				usuario.setEmail(datos.getEmail());
				usuario.setContrasena(datos.getContrasena());
				res = 0;
			} else {
				res = 2;
			}
		}
		return res;
	}

	@Transactional
	public Boolean delete(int id) {
		boolean res = false;
		if (usuarioRepository.existsById(id)) {
			usuarioRepository.deleteById(id);
			res = true;
		}
		return res;
	}

	public List<Jornada> findJornadas(int id) {
		List<Jornada> res = null;
		Optional<Usuario> usuarioOpt = findById(id);
		if (usuarioOpt.isPresent()) {
			res = usuarioOpt.get().getJornadas();
		}
		return res;
	}

	public List<Enfrentamiento> findEnfrentamientos(int id) {
		List<Enfrentamiento> res = null;
		Optional<Usuario> usuarioOpt = findById(id);
		if (usuarioOpt.isPresent()) {
			res = usuarioOpt.get().getEnfrentamientos();
		}
		return res;
	}

	public List<Enfrentamiento> findEnfrentamientosGanados(int id) {
		List<Enfrentamiento> res = null;
		Optional<Usuario> usuarioOpt = findById(id);
		if (usuarioOpt.isPresent()) {
			res = usuarioOpt.get().getEnfrentamientosGanados();
		}
		return res;
	}
}