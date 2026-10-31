package com.aurafarm.aurafarm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.aurafarm.aurafarm.repository.NoticiasRepository;
import com.aurafarm.aurafarm.service.NoticiasService;

@RestController
public class NoticiasController {
	@Autowired
	private NoticiasService noticiasService;
	
	

}
