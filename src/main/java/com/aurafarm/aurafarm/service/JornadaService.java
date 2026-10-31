package com.aurafarm.aurafarm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aurafarm.aurafarm.entity.Jornada;
import com.aurafarm.aurafarm.entity.TipoPlazo;
import com.aurafarm.aurafarm.repository.JornadaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JornadaService {

	@Autowired
    private JornadaRepository jornadaRepository;
	
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
    
    
}