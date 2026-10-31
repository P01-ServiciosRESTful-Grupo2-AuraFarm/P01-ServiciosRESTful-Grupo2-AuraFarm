package com.aurafarm.aurafarm.controller;

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

@RestController
@RequestMapping("/admins")
public class AdministradorController {
	@Autowired
	private AdministradorService administradorService;
	@Autowired
	private UsuarioService usuarioService;

	@PostMapping("/register")
	public ResponseEntity<Administrador> create(@Valid @RequestBody Administrador a) {
		Administrador res = administradorService.create(a);
		if (res == null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} else {
			return ResponseEntity.status(HttpStatus.CREATED).body(res);
		}
	}

	@GetMapping("/{adminId}")
	public ResponseEntity<Administrador> findById(@PathVariable int adminId) {
		Optional<Administrador> adminOpt = administradorService.findById(adminId);
		if (adminOpt.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(adminOpt.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PutMapping("/{adminId}/update")
	public ResponseEntity<Administrador> update(@PathVariable int adminId, @Valid @RequestBody Administrador datos) {
		int codigo = administradorService.update(adminId, datos);
		if (codigo == 1) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} else if (codigo == 2) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(administradorService.findById(adminId).get());
		}
	}

	@DeleteMapping("/{adminId}/delete")
	public ResponseEntity<String> delete(@PathVariable int adminId) {
		if (administradorService.delete(adminId)) {
			return ResponseEntity.status(HttpStatus.OK).build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping("/{adminId}/export")
	public ResponseEntity<Administrador> export(@PathVariable int adminId) {
		Optional<Administrador> adminOpt = administradorService.findById(adminId);
		if (adminOpt.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK)
					.header(HttpHeaders.CONTENT_DISPOSITION,
							"attachment; filename=\"datos-administrador-" + adminId + ".json\"")
					.body(adminOpt.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping("/{adminId}/users")
	public ResponseEntity<List<Usuario>> findAllUsers(@PathVariable int adminId) {
		if (administradorService.findById(adminId).isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findAll());
		}
	}

	@GetMapping("/{adminId}/admins")
	public ResponseEntity<List<Administrador>> findAllAdmins(@PathVariable int adminId) {
		if (administradorService.findById(adminId).isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(administradorService.findAll());
		}
	}
}