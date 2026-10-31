package com.aurafarm.aurafarm.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.aurafarm.aurafarm.entity.Administrador;
import com.aurafarm.aurafarm.repository.AdministradorRepository;
@Service
public class AdministradorService {
	@Autowired
	private AdministradorRepository administradorRepository;
	@Autowired
	private ActorService actorService;

	public Optional<Administrador> findById(int id) {
		return administradorRepository.findById(id);
	}

	public List<Administrador> findAll() {
		return administradorRepository.findAll();
	}

	@Transactional
	public Administrador create(Administrador a) {
		Administrador res = null;
		if (actorService.comprobarDatosUnicos(a.getEmail(), a.getNombreUsuario(), -1) == 0) {
			a.setId(0);
			a.setVersion(0);
			res = administradorRepository.save(a);
		}
		return res;
	}

	@Transactional
	public int update(int id, Administrador datos) {
		int res = 1;
		Optional<Administrador> adminOpt = findById(id);
		if (adminOpt.isPresent()) {
			if (actorService.comprobarDatosUnicos(datos.getEmail(), datos.getNombreUsuario(), id) == 0) {
				Administrador admin = adminOpt.get();
				admin.setNombreUsuario(datos.getNombreUsuario());
				admin.setFoto(datos.getFoto());
				admin.setEmail(datos.getEmail());
				admin.setContrasena(datos.getContrasena());
				res = 0;
			} else {
				res = 2;
			}
		}
		return res;
	}

	@Transactional
	public Boolean delete(int id) {
		boolean res = false;
		if (administradorRepository.existsById(id)) {
			administradorRepository.deleteById(id);
			res = true;
		}
		return res;
	}
}
