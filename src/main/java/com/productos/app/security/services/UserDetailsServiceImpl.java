package com.productos.app.security.services;

import com.productos.app.security.model.UsuarioModel;
import com.productos.app.security.model.UsuarioPrincipalModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//Convierte la clase Usuario en Usuario Principal.
//Media entre la clase Usuario y Usuario Principal.
//Es la clase de SpringSecurity especifica
//Para obtener los datos del usuario y sus privilegios

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	UsuarioService usuarioService;
	
	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		System.out.println(usernameOrEmail);
		UsuarioModel usuarioModel = usuarioService.getByNombreUsuarioOrEmail(usernameOrEmail).get();
		return UsuarioPrincipalModel.build(usuarioModel);
	}

	
	
}
