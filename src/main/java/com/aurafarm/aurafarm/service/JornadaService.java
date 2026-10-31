package com.aurafarm.aurafarm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aurafarm.aurafarm.entity.Jornada;
import com.aurafarm.aurafarm.entity.TipoPlazo;
import com.aurafarm.aurafarm.entity.Usuario;
import com.aurafarm.aurafarm.repository.JornadaRepository;
import com.aurafarm.aurafarm.repository.UsuarioRepository;

@Service
public class JornadaService {

	@Autowired
    private JornadaRepository jornadaRepository;
	
	@Autowired
    private UsuarioRepository usuarioRepository;
	
	@Autowired
    private EnfrentamientoService enfrentamientoService;

    public List<Jornada> obtenerTodas() {
        return jornadaRepository.findAll();
    }

    public List<Jornada> obtenerJornadasPublicas() {
        List<Jornada> todas = jornadaRepository.findAll();
        List<Jornada> publicas = new ArrayList<>();
        for (Jornada j : todas) {
            if (j.getPlazo() == TipoPlazo.ABIERTO) {
                publicas.add(j);
            }
        }
        return publicas;
    }

    public Jornada obtenerPorId(int id) {
        Optional<Jornada> optJornada = jornadaRepository.findById(id);
        if (optJornada.isPresent()) {
            return optJornada.get();
        }
        return null;
    }

    @Transactional
    public Jornada crear(Jornada jornada) {
        if (jornada.getMaxInscripciones() >= 0 && jornada.getMaxInscripciones() % 2 != 0) {
            throw new IllegalArgumentException("El número máximo de inscripciones debe ser un número par.");
        }
        if (jornada.getPlazo() == null) {
            jornada.setPlazo(TipoPlazo.ABIERTO);
        }
        return jornadaRepository.save(jornada);
    }

    @Transactional
    public Jornada actualizar(int id, Jornada datosActualizados) {
        Optional<Jornada> optJornada = jornadaRepository.findById(id);
        
        if (optJornada.isPresent()) {
            Jornada jornadaExistente = optJornada.get();

            if (datosActualizados.getMaxInscripciones() >= 0 && datosActualizados.getMaxInscripciones() % 2 != 0) {
                throw new IllegalArgumentException("El número máximo de inscripciones debe ser un número par.");
            }

            jornadaExistente.setNombre(datosActualizados.getNombre());
            jornadaExistente.setDescripcion(datosActualizados.getDescripcion());
            jornadaExistente.setFecha(datosActualizados.getFecha());
            jornadaExistente.setHoraInicio(datosActualizados.getHoraInicio());
            jornadaExistente.setHoraFin(datosActualizados.getHoraFin());
            if (datosActualizados.getMaxInscripciones() >= 0) {
                jornadaExistente.setMaxInscripciones(datosActualizados.getMaxInscripciones());
            }

            return jornadaRepository.save(jornadaExistente);
        }
        
        return null;
    }

    @Transactional
    public boolean eliminar(int id) {
        Optional<Jornada> optJornada = jornadaRepository.findById(id);
        if (optJornada.isPresent()) {
        	for (Usuario u : usuarioRepository.encontrarInscritosPorJornada(id)) {
				u.getJornadas().remove(optJornada.get());
				usuarioRepository.save(u);
			}
            jornadaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public Jornada cambiarEstadoPlazo(int id) {
        Optional<Jornada> optJornada = jornadaRepository.findById(id);
        if (optJornada.isPresent()) {
            Jornada jornada = optJornada.get();
            if (jornada.getPlazo() == TipoPlazo.ABIERTO) {
                jornada.setPlazo(TipoPlazo.CERRADO);
                enfrentamientoService.generarCruces(jornada);
            } else {
                if (!enfrentamientoService.eliminarCruces(jornada)) {
                    throw new IllegalStateException("No se puede reabrir: ya hay resultados registrados.");
                }
                jornada.setPlazo(TipoPlazo.ABIERTO);
            }
            return jornadaRepository.save(jornada);
        }
        return null;
    }
    
    @Transactional
    public Usuario inscribirUsuario(int jornadaId, int usuarioId) {
        Jornada jornada = jornadaRepository.findById(jornadaId)
                .orElseThrow(() -> new IllegalArgumentException("La jornada no existe."));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("El usuario no existe."));

        if (jornada.getPlazo() != TipoPlazo.ABIERTO) {
            throw new IllegalStateException("El plazo de inscripción para esta jornada está cerrado.");
        }

        if (usuario.getJornadas().contains(jornada)) {
            throw new IllegalStateException("El usuario ya se encuentra inscrito en esta jornada.");
        }

        long inscritosActuales = usuarioRepository.countByJornadasContaining(jornada.getId());
        if (inscritosActuales >= jornada.getMaxInscripciones()) {
            throw new IllegalStateException("No hay plazas disponibles en esta jornada.");
        }

        usuario.getJornadas().add(jornada);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario darseDeBajaUsuario(int jornadaId, int usuarioId) {
        Jornada jornada = jornadaRepository.findById(jornadaId).orElseThrow(() -> new IllegalArgumentException("La jornada no existe."));

        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new IllegalArgumentException("El usuario no existe."));

        if (jornada.getPlazo() != TipoPlazo.ABIERTO) {
            throw new IllegalStateException("No es posible darse de baja con el plazo cerrado.");
        }

        if (!usuario.getJornadas().contains(jornada)) {
            throw new IllegalStateException("El usuario no está inscrito en esta jornada.");
        }

        usuario.getJornadas().remove(jornada);
        return usuarioRepository.save(usuario);
    }
    
}