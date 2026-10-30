package com.aurafarm.aurafarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.aurafarm.aurafarm.entity.Noticias;

@Repository
public interface NoticiasRepository extends JpaRepository<Noticias, Integer>{
	
}
