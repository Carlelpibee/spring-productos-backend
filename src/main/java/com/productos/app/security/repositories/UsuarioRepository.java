package com.productos.app.security.repositories;

import com.productos.app.security.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Integer>{

	Optional<UsuarioModel> findByNombreUsuario(String nombreUsuario);
	Optional<UsuarioModel> findByNombreUsuarioOrEmail(String nombreUsuario, String email);
	Optional<UsuarioModel> findByTokenPassword(String tokenPassword);
	boolean existsByNombreUsuario(String nombreUsuario);

	boolean existsByNombreUsuarioOrEmail(String nombreUsuario, String email);
	boolean existsByEmail(String email);
	
}
