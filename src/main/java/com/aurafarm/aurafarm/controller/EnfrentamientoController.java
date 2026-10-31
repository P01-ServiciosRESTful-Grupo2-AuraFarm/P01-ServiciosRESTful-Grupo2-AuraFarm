package com.aurafarm.aurafarm.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aurafarm.aurafarm.entity.Enfrentamiento;
import com.aurafarm.aurafarm.service.EnfrentamientoService;
import com.aurafarm.aurafarm.service.JornadaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/aurafarm/enfrentamiento")
@Tag(name = "Enfrentamiento", description = "Operaciones relacionadas con los enfrentamientos")
public class EnfrentamientoController {
	@Autowired
	private EnfrentamientoService enfrentamientoService;
	
	@Autowired
	private JornadaService jornadaService;

	@GetMapping("/jornada/{jornadaId}")
	public ResponseEntity<List<Enfrentamiento>> getByJornada(@PathVariable int jornadaId) {
		if (jornadaService.obtenerPorId(jornadaId) == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(enfrentamientoService.getEnfrentamientosByJornada(jornadaId));
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Buscar un enfrentamiento por ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Enfrentamiento encontrado"),
			@ApiResponse(responseCode = "404", description = "Enfrentamiento no encontrado") })
	public ResponseEntity<Enfrentamiento> findById(@PathVariable int id) {
		Optional<Enfrentamiento> enfrentamiento = enfrentamientoService.getEnfrentamientoById(id);
		if (enfrentamiento.isPresent()) {
			return ResponseEntity.ok(enfrentamiento.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
}
