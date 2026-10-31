package com.aurafarm.aurafarm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aurafarm.aurafarm.entity.Enfrentamiento;
import com.aurafarm.aurafarm.service.EnfrentamientoService;

@RestController
@RequestMapping("enfrentamiento")
public class EnfrentamientoController {
	@Autowired
	private EnfrentamientoService enfrenServicio;
	
	@GetMapping
	private ResponseEntity<List<Enfrentamiento>> getAll(){
		
	}
	
}
