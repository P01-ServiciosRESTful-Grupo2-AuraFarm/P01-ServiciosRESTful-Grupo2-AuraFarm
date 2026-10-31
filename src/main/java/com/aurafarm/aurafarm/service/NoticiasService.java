package com.aurafarm.aurafarm.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.aurafarm.aurafarm.entity.Noticias;
import com.aurafarm.aurafarm.repository.NoticiasRepository;

import jakarta.transaction.Transactional;

public class NoticiasService {
	@Autowired
	private NoticiasRepository noticiasRpeository;
	
	@Transactional
	public Noticias crear(Noticias noticia) {
		return noticiasRpeository.save(noticia);
	}
	
	public Noticias actualizar()
	
}
