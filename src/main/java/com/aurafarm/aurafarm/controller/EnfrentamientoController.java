package com.aurafarm.aurafarm.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aurafarm.aurafarm.entity.Enfrentamiento;
import com.aurafarm.aurafarm.entity.Usuario;
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
    @Operation(summary = "Listar enfrentamientos por jornada ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de enfrentamientos obtenida"),
            @ApiResponse(responseCode = "404", description = "Jornada no encontrada") })
    public ResponseEntity<List<Enfrentamiento>> getByJornada(@PathVariable int jornadaId) {
        if (jornadaService.obtenerPorId(jornadaId) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(enfrentamientoService.getEnfrentamientosByJornada(jornadaId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar un enfrentamiento por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Enfrentamiento encontrado"),
            @ApiResponse(responseCode = "404", description = "Enfrentamiento no encontrado") })
    public ResponseEntity<Enfrentamiento> findById(@PathVariable int id) {
        if (enfrentamientoService.getEnfrentamientoById(id).isPresent()) {
			return ResponseEntity.ok(enfrentamientoService.getEnfrentamientoById(id).get());
		}else {
			return ResponseEntity.notFound().build();
		}
    }

    @GetMapping("/{id}/participantes")
    @Operation(summary = "Listar los participantes de un enfrentamiento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida"),
            @ApiResponse(responseCode = "404", description = "Enfrentamiento no encontrado") })
    public ResponseEntity<List<Usuario>> getParticipantes(@PathVariable int id) {
        if (enfrentamientoService.getEnfrentamientoById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(enfrentamientoService.getParticipantes(id));
    }

    @PutMapping("/{id}/resultado/{ganadorId}")
    @Operation(summary = "Registrar el resultado de un enfrentamiento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Enfrentamiento no pendiente o ganador no válido"),
            @ApiResponse(responseCode = "404", description = "Enfrentamiento no encontrado") })
    public ResponseEntity<Enfrentamiento> registrarResultado(@PathVariable int id, @PathVariable int ganadorId) {
        Enfrentamiento actualizado = enfrentamientoService.registrarResultado(id, ganadorId);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.notFound().build();
    }
}
