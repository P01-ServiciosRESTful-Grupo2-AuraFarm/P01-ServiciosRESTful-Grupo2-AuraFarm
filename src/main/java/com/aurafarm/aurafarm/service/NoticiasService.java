package com.aurafarm.aurafarm.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.aurafarm.aurafarm.entity.Noticias;
import com.aurafarm.aurafarm.repository.NoticiasRepository;

import jakarta.transaction.Transactional;

public class NoticiasService {
	@Autowired
	private NoticiasRepository noticiasRepository;
	
	@Transactional
	public Noticias crear(Noticias noticia) {
		return noticiasRepository.save(noticia);
	}
	
	@Transactional
	public Noticias actualizar(int id, Noticias noticiaActualizada) {
		Optional<Noticias> optNoticias = noticiasRepository.findById(id);
		
		if (optNoticias.isPresent()) {
			Noticias noticiasExistente = optNoticias.get();
			
			noticiasExistente.setTitulo(noticiaActualizada.getTitulo());
			noticiasExistente.setVersion(noticiaActualizada.getVersion());
			noticiasExistente.setImagen(noticiaActualizada.getImagen());
			noticiasExistente.setContenido(noticiaActualizada.getContenido());
			noticiasExistente.setImagen(noticiaActualizada.getImagen());
			
			return noticiasRepository.save(noticiasExistente);
		}else {
			return null;
		}
		
	}
	
	public 
	
}
