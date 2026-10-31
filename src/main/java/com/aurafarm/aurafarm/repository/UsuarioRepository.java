package com.aurafarm.aurafarm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aurafarm.aurafarm.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	@Query("SELECT a.victorias FROM Usuario ORDER BY desc")
	public Optional<List<Usuario>> findByEnfrentamientosGanadosRanking();
	
	@Query("SELECT u FROM Usuario u JOIN u.enfrentamientos e WHERE e.id = ?1")
	List<Usuario> encontrarParticipantes(int enfrentamientoId);
	@Query("SELECT u FROM Usuario u JOIN u.jornadas j WHERE j.id = ?1")
	List<Usuario> encontrarInscritosPorJornada(int jornadaId);
}