package com.aurafarm.aurafarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aurafarm.aurafarm.entity.Administrador;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Integer> {

}