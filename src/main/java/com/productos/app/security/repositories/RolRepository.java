package com.productos.app.security.repositories;

import com.productos.app.security.enums.RolNombre;
import com.productos.app.security.model.RolModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<RolModel, Integer>{
	
	Optional<RolModel> findByRolNombre(RolNombre rolNombre);
	
}
