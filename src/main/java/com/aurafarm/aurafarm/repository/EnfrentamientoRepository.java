package com.aurafarm.aurafarm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aurafarm.aurafarm.entity.Enfrentamiento;

@Repository
public interface EnfrentamientoRepository extends JpaRepository<Enfrentamiento, Integer> {

	@Query("SELECT e FROM Jornada j JOIN j.enfrentamientos e WHERE j.id = ?1")
	List<Enfrentamiento> encontrarPorJornada(int jornadaId);
}