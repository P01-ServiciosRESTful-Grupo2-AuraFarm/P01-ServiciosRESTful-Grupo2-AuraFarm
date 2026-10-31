package com.aurafarm.aurafarm.controller;

import com.aurafarm.aurafarm.entity.Jornada;
import com.aurafarm.aurafarm.service.JornadaService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jornadas")
public class JornadaController {

	@Autowired
    private JornadaService jornadaService;

    @GetMapping("/publicas")
    public ResponseEntity<List<Jornada>> listarJornadasPublicas() {
        return ResponseEntity.ok(jornadaService.obtenerJornadasPublicas());
    }

    @GetMapping
    public ResponseEntity<List<Jornada>> listarTodas() {
        return ResponseEntity.ok(jornadaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Jornada> obtenerPorId(@PathVariable int id) {
        Jornada jornada = jornadaService.obtenerPorId(id);
        if (jornada != null) {
            return ResponseEntity.ok(jornada);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Jornada> crear(@Valid @RequestBody Jornada jornada) {
        Jornada nuevaJornada = jornadaService.crear(jornada);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaJornada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Jornada> actualizar(@PathVariable int id, @Valid @RequestBody Jornada jornada) {
        Jornada jornadaActualizada = jornadaService.actualizar(id, jornada);
        if (jornadaActualizada != null) {
            return ResponseEntity.ok(jornadaActualizada);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        if (jornadaService.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/cambiar-plazo")
    public ResponseEntity<Jornada> cambiarPlazo(@PathVariable int id) {
        Jornada jornada = jornadaService.cambiarEstadoPlazo(id);
        if (jornada != null) {
            return ResponseEntity.ok(jornada);
        }
        return ResponseEntity.notFound().build();
    }
}