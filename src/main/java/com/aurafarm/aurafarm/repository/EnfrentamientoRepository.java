package com.aurafarm.aurafarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aurafarm.aurafarm.entity.Enfrentamiento;

@Repository
public interface EnfrentamientoRepository extends JpaRepository<Enfrentamiento, Integer>{

}
