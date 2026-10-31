package com.aurafarm.aurafarm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aurafarm.aurafarm.entity.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {
	@Query("SELECT a FROM Actor a WHERE a.email = ?1")
	public Optional<Actor> findByEmail(String email);

	@Query("SELECT a FROM Actor a WHERE a.nombreUsuario = ?1")
	public Optional<Actor> findByNombreUsuario(String nombreUsuario);
}