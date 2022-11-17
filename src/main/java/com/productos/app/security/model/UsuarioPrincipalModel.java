package com.productos.app.security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UsuarioPrincipalModel implements UserDetails{
		
	private String nombre;
	private String nombreUsuario;
	private String email;
	private String password;
	//Cambia roles por authorities:Spring Security
	private Collection<? extends GrantedAuthority> authorities;

	//Se sobrescriben todos los métodos : UserDetails
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public String getUsername() {
		return nombreUsuario;
	}
	
	//Solo han sido cambiados a : true
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	//Personalizado : Agregado
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	//Se generan los constructores
	
	public UsuarioPrincipalModel(String nombre, String nombreUsuario, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.nombre = nombre;
		this.nombreUsuario = nombreUsuario;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	//Se genera el método Build : Obtener usuario
	public static UsuarioPrincipalModel build(UsuarioModel usuarioModel) {
		//1. Obtener los roles
		//2. Convertirlos a authorities
		//(Se convierte la clase rol a la clase GrantedAuthority)
		Set<RolModel> rolesUsuario = usuarioModel.getRoles();
		List<GrantedAuthority> authorities = rolesUsuario.stream().map(rol->new SimpleGrantedAuthority(rol.getRolNombre().name())).collect(Collectors.toList());
		
		
		//FormaSimplificada:
		//List<GrantedAuthority> authorities = usuarioModel.getRoles()stream().map(rol->new SimpleGrantedAuthority(rol.getRolNombre().name())).collect(Collectors.toList());;
		
		return new UsuarioPrincipalModel(usuarioModel.getNombre(),
				usuarioModel.getNombreUsuario(),
				usuarioModel.getEmail(), 
				usuarioModel.getPassword(), 
				authorities);
	}
	

}


















