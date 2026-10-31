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

import com.aurafarm.aurafarm.entity.Enfrentamiento;
import com.aurafarm.aurafarm.entity.Jornada;
import com.aurafarm.aurafarm.entity.Usuario;
import com.aurafarm.aurafarm.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/aurafarm/users")
@Tag(name = "Usuario", description = "Operaciones de gestión de usuarios, perfil y ranking")
public class UsuarioController {
	@Autowired
	private UsuarioService usuarioService;

	@Operation(summary = "Registrar un nuevo usuario", description = "Permite a un actor no autenticado crear una cuenta en el sistema.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Usuario creado con éxito"),
			@ApiResponse(responseCode = "400", description = "Datos de entrada no válidos"),
			@ApiResponse(responseCode = "409", description = "El usuario o email ya existe en el sistema")
	})
	@PostMapping("/register")
	public ResponseEntity<Usuario> create(@Valid @RequestBody Usuario u) {
		Usuario res = usuarioService.create(u);
		if (res == null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} else {
			return ResponseEntity.status(HttpStatus.CREATED).body(res);
		}
	}

	@Operation(summary = "Obtener usuario por ID", description = "Consulta los detalles de un usuario a partir de su identificador.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Usuario encontrado"),
			@ApiResponse(responseCode = "404", description = "Usuario no encontrado")
	})
	@GetMapping("/{userId}")
	public ResponseEntity<Usuario> findById(@PathVariable int userId) {
		Optional<Usuario> usuarioOpt = usuarioService.findById(userId);
		if (usuarioOpt.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(usuarioOpt.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@Operation(summary = "Actualizar datos personales", description = "Permite modificar la información personal del usuario.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
			@ApiResponse(responseCode = "400", description = "Datos de actualización no válidos"),
			@ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
			@ApiResponse(responseCode = "409", description = "Conflicto con datos existentes (email o usuario duplicado)")
	})
	@PutMapping("/{userId}/update")
	public ResponseEntity<Usuario> update(@PathVariable int userId, 
										  @Valid @RequestBody Usuario datos) {
		int codigo = usuarioService.update(userId, datos);
		if (codigo == 1) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} else if (codigo == 2) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findById(userId).get());
		}
	}

	@Operation(summary = "Eliminar perfil de usuario", description = "Solicita la eliminación del perfil del usuario en el sistema.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Perfil eliminado correctamente"),
			@ApiResponse(responseCode = "404", description = "Usuario no encontrado")
	})
	@DeleteMapping("/{userId}/delete")
	public ResponseEntity<String> delete(@PathVariable int userId) {
		if (usuarioService.delete(userId)) {
			return ResponseEntity.status(HttpStatus.OK).build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@Operation(summary = "Exportar datos personales", description = "Permite al usuario exportar su información personal.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Datos exportados correctamente"),
			@ApiResponse(responseCode = "404", description = "Usuario no encontrado")
	})
	@GetMapping("/{userId}/export")
	public ResponseEntity<Usuario> export(@PathVariable int userId) {
		Optional<Usuario> usuarioOpt = usuarioService.findById(userId);
		if (usuarioOpt.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(usuarioOpt.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@Operation(summary = "Consultar jornadas del usuario", description = "Obtiene la lista de jornadas en las que está o ha estado inscrito el usuario.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de jornadas obtenida con éxito"),
			@ApiResponse(responseCode = "404", description = "Usuario no encontrado")
	})
	@GetMapping("/{userId}/jornadas")
	public ResponseEntity<List<Jornada>> findJornadas(@PathVariable int userId) {
		List<Jornada> res = usuarioService.findJornadas(userId);
		if (res == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}
	}

	@Operation(summary = "Consultar enfrentamientos del usuario", description = "Obtiene los enfrentamientos disputados o asignados al usuario.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de enfrentamientos obtenida con éxito"),
			@ApiResponse(responseCode = "404", description = "Usuario no encontrado")
	})
	@GetMapping("/{userId}/enfrentamientos")
	public ResponseEntity<List<Enfrentamiento>> findEnfrentamientos(@PathVariable int userId) {
		List<Enfrentamiento> res = usuarioService.findEnfrentamientos(userId);
		if (res == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}
	}

	@Operation(summary = "Consultar victorias del usuario", description = "Obtiene la lista de enfrentamientos ganados por el usuario.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de victorias obtenida con éxito"),
			@ApiResponse(responseCode = "404", description = "Usuario no encontrado")
	})
	@GetMapping("/{userId}/wins")
	public ResponseEntity<List<Enfrentamiento>> findEnfrentamientosGanados(@PathVariable int userId) {
		List<Enfrentamiento> res = usuarioService.findEnfrentamientosGanados(userId);
		if (res == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}
	}

	@Operation(summary = "Consultar ranking de usuarios", description = "Devuelve la clasificación de usuarios ordenada por enfrentamientos ganados/victorias.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ranking obtenido con éxito"),
			@ApiResponse(responseCode = "404", description = "No se encontraron datos de ranking")
	})
	@GetMapping("/ranking")
	public ResponseEntity<List<Usuario>> findByEnfrentamientosGanadosRanking() {
		Optional<List<Usuario>> res = usuarioService.findByEnfrentamientosGanadosRanking();
		if (res.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(res.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}