package com.aurafarm.aurafarm.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.aurafarm.aurafarm.entity.Enfrentamiento;
import com.aurafarm.aurafarm.entity.Jornada;
import com.aurafarm.aurafarm.entity.Usuario;
import com.aurafarm.aurafarm.service.AdministradorService;
import com.aurafarm.aurafarm.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/aurafarm/admins")
@Tag(name = "Administrador", description = "Operaciones de gestión del perfil de administrador y supervisión de usuarios")
public class AdministradorController {
	@Autowired
	private AdministradorService administradorService;
	@Autowired
	private UsuarioService usuarioService;

	@Operation(summary = "Registrar un nuevo administrador", description = "Permite registrar un nuevo perfil con privilegios de administrador.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Administrador creado exitosamente"),
			@ApiResponse(responseCode = "400", description = "Datos de entrada no válidos"),
			@ApiResponse(responseCode = "409", description = "Conflicto: El usuario o email ya existe")
	})
	@PostMapping("/register")
	public ResponseEntity<Administrador> create(@Valid @RequestBody Administrador a) {
		Administrador res = administradorService.create(a);
		if (res == null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} else {
			return ResponseEntity.status(HttpStatus.CREATED).body(res);
		}
	}

	@Operation(summary = "Obtener administrador por ID", description = "Consulta la información detallada del perfil de administrador.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Administrador encontrado"),
			@ApiResponse(responseCode = "404", description = "Administrador no encontrado")
	})
	@GetMapping("/{adminId}")
	public ResponseEntity<Administrador> findById(@PathVariable int adminId) {
		Optional<Administrador> adminOpt = administradorService.findById(adminId);
		if (adminOpt.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(adminOpt.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@Operation(summary = "Actualizar perfil de administrador", description = "Permite a un administrador modificar sus datos personales.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Datos actualizados correctamente"),
			@ApiResponse(responseCode = "400", description = "Datos de entrada no válidos"),
			@ApiResponse(responseCode = "404", description = "Administrador no encontrado"),
			@ApiResponse(responseCode = "409", description = "Conflicto con datos existentes (email o usuario en uso)")
	})
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

	@Operation(summary = "Eliminar cuenta de administrador", description = "Solicita la baja o eliminación del perfil de administrador.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Administrador eliminado con éxito"),
			@ApiResponse(responseCode = "404", description = "Administrador no encontrado")
	})
	@DeleteMapping("/{adminId}/delete")
	public ResponseEntity<String> delete(@PathVariable int adminId) {
		if (administradorService.delete(adminId)) {
			return ResponseEntity.status(HttpStatus.OK).build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@Operation(summary = "Exportar datos del administrador", description = "Descarga la información personal del administrador en formato de archivo JSON.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Archivo descargado con éxito"),
			@ApiResponse(responseCode = "404", description = "Administrador no encontrado")
	})
	@GetMapping("/{adminId}/export")
	public ResponseEntity<Administrador> export(@PathVariable int adminId) {
		Optional<Administrador> adminOpt = administradorService.findById(adminId);
		if (adminOpt.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(adminOpt.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@Operation(summary = "Revisar todos los usuarios del sistema", description = "Permite al administrador ver la lista completa de usuarios registrados.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente"),
			@ApiResponse(responseCode = "404", description = "Administrador solicitante no encontrado")
	})
	@GetMapping("/{adminId}/users")
	public ResponseEntity<List<Usuario>> findAllUsers(@PathVariable int adminId) {
		if (administradorService.findById(adminId).isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findAll());
		}
	}

	@Operation(summary = "Listar administradores", description = "Permite ver todos los perfiles de administradores registrados en el sistema.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de administradores obtenida exitosamente"),
			@ApiResponse(responseCode = "404", description = "Administrador solicitante no encontrado")
	})
	@GetMapping("/{adminId}/admins")
	public ResponseEntity<List<Administrador>> findAllAdmins(@PathVariable int adminId) {
		if (administradorService.findById(adminId).isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(administradorService.findAll());
		}
	}
}