package com.aurafarm.aurafarm.controller;

import com.aurafarm.aurafarm.entity.Noticias;
import com.aurafarm.aurafarm.service.NoticiasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aurafarm/noticias")
@Tag(name = "Noticia", description = "Operaciones relacionadas con la gestión de noticias")
public class NoticiasController {

    private final NoticiasService noticiasService;

    public NoticiasController(NoticiasService noticiasService) {
        this.noticiasService = noticiasService;
    }

    @Operation(summary = "Listar todas las noticias", description = "Permite a cualquier usuario visualizar las noticias publicadas.")
    @ApiResponse(responseCode = "200", description = "Lista de noticias obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<Noticias>> obtenerTodas() {
        return ResponseEntity.ok(noticiasService.findAll());
    }

    @Operation(summary = "Buscar noticia por ID", description = "Consulta la información detallada de una noticia específica.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Noticia encontrada"),
        @ApiResponse(responseCode = "404", description = "Noticia no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Noticias> obtenerPorId(@PathVariable int id) {
    	if (noticiasService.findById(id).isPresent()) {
    		return ResponseEntity.status(HttpStatus.OK).body(noticiasService.findById(id).get());
    	}else {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
    }

    @Operation(summary = "Crear una nueva noticia", description = "Permite a un administrador publicar una noticia.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Noticia creada con éxito"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<Noticias> crear(@Valid @RequestBody Noticias noticia) {
        Noticias creada = noticiasService.crear(noticia);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @Operation(summary = "Modificar una noticia", description = "Permite a un administrador actualizar una noticia existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Noticia actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Noticia no encontrada"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Noticias> actualizar(@PathVariable int id, @Valid @RequestBody Noticias noticia) {
        Noticias actualizada = noticiasService.actualizar(id, noticia);
        if (actualizada != null) {
            return ResponseEntity.ok(actualizada);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "Eliminar una noticia", description = "Permite a un administrador eliminar una noticia del sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Noticia eliminada con éxito"),
        @ApiResponse(responseCode = "404", description = "Noticia no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        boolean eliminado = noticiasService.eliminar(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}