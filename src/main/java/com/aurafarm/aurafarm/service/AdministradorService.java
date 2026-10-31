package com.aurafarm.aurafarm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aurafarm.aurafarm.entity.Administrador;
import com.aurafarm.aurafarm.entity.Usuario;
import com.aurafarm.aurafarm.service.AdministradorService;
import com.aurafarm.aurafarm.service.UsuarioService;

import jakarta.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;

import com.aurafarm.aurafarm.repository.AdministradorRepository;

public class AdministradorService {
	@Autowired
	private AdministradorRepository administradorRepository;
	@Autowired
	private ActorService actorService;

	public Optional<Administrador> findById(int id) {
		return administradorRepository.findById(id);
	}

	public List<Administrador> findAll() {
		return administradorRepository.findAll();
	}

	// Devuelve null si el email o el nombre de usuario ya estan registrados
	@Transactional
	public Administrador create(Administrador a) {
		Administrador res = null;
		if (actorService.comprobarDatosUnicos(a.getEmail(), a.getNombreUsuario(), -1) == 0) {
			a.setId(0);
			a.setVersion(0);
			res = administradorRepository.save(a);
		}
		return res;
	}
}
