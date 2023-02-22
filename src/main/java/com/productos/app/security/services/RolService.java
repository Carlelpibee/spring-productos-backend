package com.productos.app.security.services;

import com.productos.app.security.enums.RolNombre;
import com.productos.app.security.model.RolModel;
import com.productos.app.security.repositories.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional

public class RolService {

	@Autowired
	RolRepository rolRepository;
	
	public Optional<RolModel> getByRolNombre (RolNombre rolNombre){
		return rolRepository.findByRolNombre(rolNombre);
	}
	
	public void save(RolModel rolModel) {
		rolRepository.save(rolModel);
	}

	public Boolean existsByRolNombre(RolNombre rolNombre) {
		Optional<RolModel> rolOptional = rolRepository.findByRolNombre(rolNombre);
		return rolOptional.isPresent();
	}
}
