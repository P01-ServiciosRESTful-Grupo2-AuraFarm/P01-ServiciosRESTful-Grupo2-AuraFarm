package com.aurafarm.aurafarm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aurafarm.aurafarm.entity.Jornada;
import com.aurafarm.aurafarm.entity.Usuario;
import com.aurafarm.aurafarm.service.JornadaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/aurafarm/jornadas")
@Tag(name = "Jornada", description = "Operaciones relacionadas con la gestión y consulta de jornadas")
public class JornadaController {

	@Autowired
    private JornadaService jornadaService;

	@Operation(summary = "Listar jornadas públicas", description = "Permite consultar las jornadas disponibles y públicas para todos los usuarios.")
    @ApiResponse(responseCode = "200", description = "Lista de jornadas públicas obtenida exitosamente")
    @GetMapping("/publicas")
    public ResponseEntity<List<Jornada>> listarJornadasPublicas() {
        return ResponseEntity.ok(jornadaService.obtenerJornadasPublicas());
    }

    @Operation(summary = "Listar todas las jornadas", description = "Obtiene el listado completo de todas las jornadas del sistema (área administrativa).")
    @ApiResponse(responseCode = "200", description = "Lista completa obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<Jornada>> listarTodas() {
        return ResponseEntity.ok(jornadaService.obtenerTodas());
    }

    @Operation(summary = "Obtener jornada por ID", description = "Devuelve la información detallada de una jornada específica mediante su identificador.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Jornada encontrada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Jornada no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Jornada> obtenerPorId(@PathVariable int id) {
        Jornada jornada = jornadaService.obtenerPorId(id);
        if (jornada != null) {
            return ResponseEntity.ok(jornada);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Crear una nueva jornada", description = "Permite a un administrador registrar una nueva jornada.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Jornada creada con éxito"),
        @ApiResponse(responseCode = "400", description = "Datos de la jornada no válidos")
    })
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Jornada jornada) {
    	try {
            return ResponseEntity.status(HttpStatus.CREATED).body(jornadaService.crear(jornada));
		} catch (IllegalArgumentException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
    }

    @Operation(summary = "Actualizar una jornada", description = "Permite a un administrador modificar los datos de una jornada existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Jornada actualizada con éxito"),
        @ApiResponse(responseCode = "400", description = "Datos de la jornada no válidos"),
        @ApiResponse(responseCode = "404", description = "Jornada no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable int id, @Valid @RequestBody Jornada jornada) {
    	try {
    		Jornada jornadaActualizada = jornadaService.actualizar(id, jornada);
            if (jornadaActualizada != null) {
                return ResponseEntity.ok(jornadaActualizada);
            }
            return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
    }

    @Operation(summary = "Eliminar una jornada", description = "Permite a un administrador eliminar una jornada del sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Jornada eliminada con éxito"),
        @ApiResponse(responseCode = "404", description = "Jornada no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        if (jornadaService.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Cambiar plazo de inscripción", description = "Permite a un administrador abrir o cerrar el plazo de inscripción de una jornada.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estado del plazo cambiado con éxito"),
        @ApiResponse(responseCode = "404", description = "Jornada no encontrada")
    })
    @PatchMapping("/{id}/cambiar-plazo")
    public ResponseEntity<?> cambiarPlazo(@PathVariable int id) {
    	try {
            Jornada jornada = jornadaService.cambiarEstadoPlazo(id);
            if (jornada != null) {
                return ResponseEntity.ok(jornada);
            }
            return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
    }

    @PostMapping("/{jornadaId}/inscripcion/{usuarioId}")
    @Operation(summary = "Inscribir usuario en una jornada", description = "Valida plazo abierto, plazas disponibles y no duplicidad.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inscripción realizada con éxito"),
            @ApiResponse(responseCode = "400", description = "Regla de negocio violada (plazo cerrado, sin plazas o ya inscrito)"),
            @ApiResponse(responseCode = "404", description = "Jornada o usuario no encontrado")
    })
    public ResponseEntity<?> inscribir(@PathVariable int jornadaId, @PathVariable int usuarioId) {
        try {
            Usuario usuarioActualizado = jornadaService.inscribirUsuario(jornadaId, usuarioId);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ex.getMessage());
        }
    }

    @DeleteMapping("/{jornadaId}/inscripcion/{usuarioId}")
    @Operation(summary = "Darse de baja de una jornada", description = "Permite cancelar la inscripción de un usuario en una jornada activa.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Baja efectuada con éxito"),
            @ApiResponse(responseCode = "400", description = "El usuario no estaba inscrito o el plazo está cerrado"),
            @ApiResponse(responseCode = "404", description = "Jornada o usuario no encontrado")
    })
    public ResponseEntity<?> darseDeBaja(@PathVariable int jornadaId, @PathVariable int usuarioId) {
        try {
            Usuario usuarioActualizado = jornadaService.darseDeBajaUsuario(jornadaId, usuarioId);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ex.getMessage());
        }
    }
}