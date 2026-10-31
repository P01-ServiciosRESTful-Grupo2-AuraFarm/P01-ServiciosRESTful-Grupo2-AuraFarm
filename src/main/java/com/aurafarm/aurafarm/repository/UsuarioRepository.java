package com.aurafarm.aurafarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aurafarm.aurafarm.entity.Usuario;
import com.aurafarm.aurafarm.entity.Enfrentamiento;
import java.util.List;
import java.util.Optional;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	@Query("SELECT a.victorias FROM Usuario ORDER BY desc")
	public Optional<List<Integer>> findByEnfrentamientosGanados();
	

}