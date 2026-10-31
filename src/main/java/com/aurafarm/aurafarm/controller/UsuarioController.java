package com.aurafarm.aurafarm.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aurafarm.aurafarm.entity.Enfrentamiento;
import com.aurafarm.aurafarm.entity.Jornada;
import com.aurafarm.aurafarm.entity.Usuario;
import com.aurafarm.aurafarm.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UsuarioController {
	@Autowired
	private UsuarioService usuarioService;

	@PostMapping("/register")
	public ResponseEntity<Usuario> create(@Valid @RequestBody Usuario u) {
		Usuario res = usuarioService.create(u);
		if (res == null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} else {
			return ResponseEntity.status(HttpStatus.CREATED).body(res);
		}
	}

	@GetMapping("/{userId}")
	public ResponseEntity<Usuario> findById(@PathVariable int userId) {
		Optional<Usuario> usuarioOpt = usuarioService.findById(userId);
		if (usuarioOpt.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(usuarioOpt.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PutMapping("/{userId}/update")
	public ResponseEntity<Usuario> update(@PathVariable int userId, @Valid @RequestBody Usuario datos) {
		int codigo = usuarioService.update(userId, datos);
		if (codigo == 1) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} else if (codigo == 2) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findById(userId).get());
		}
	}

	@DeleteMapping("/{userId}/delete")
	public ResponseEntity<String> delete(@PathVariable int userId) {
		if (usuarioService.delete(userId)) {
			return ResponseEntity.status(HttpStatus.OK).build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	// Exportar datos personales: se devuelven como archivo .json descargable
	@GetMapping("/{userId}/export")
	public ResponseEntity<Usuario> export(@PathVariable int userId) {
		Optional<Usuario> usuarioOpt = usuarioService.findById(userId);
		if (usuarioOpt.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK)
					.header(HttpHeaders.CONTENT_DISPOSITION,
							"attachment; filename=\"datos-usuario-" + userId + ".json\"")
					.body(usuarioOpt.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping("/{userId}/jornadas")
	public ResponseEntity<List<Jornada>> findJornadas(@PathVariable int userId) {
		List<Jornada> res = usuarioService.findJornadas(userId);
		if (res == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}
	}

	@GetMapping("/{userId}/enfrentamientos")
	public ResponseEntity<List<Enfrentamiento>> findEnfrentamientos(@PathVariable int userId) {
		List<Enfrentamiento> res = usuarioService.findEnfrentamientos(userId);
		if (res == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}
	}

	@GetMapping("/{userId}/wins")
	public ResponseEntity<List<Enfrentamiento>> findEnfrentamientosGanados(@PathVariable int userId) {
		List<Enfrentamiento> res = usuarioService.findEnfrentamientosGanados(userId);
		if (res == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}
	}
	
	@GetMapping("/ranking")
	public ResponseEntity<List<Usuario>> findByEnfrentamientosGanadosRanking() {
		Optional<List<Usuario>> res = usuarioService.findByEnfrentamientosGanadosRanking();
		if(res.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(res.get());
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}