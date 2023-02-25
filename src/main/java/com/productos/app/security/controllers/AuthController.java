package com.productos.app.security.controllers;

import com.productos.app.dto.MensajeDTO;
import com.productos.app.security.dto.JwtDTO;
import com.productos.app.security.dto.LoginUsuarioDTO;
import com.productos.app.security.dto.NuevoUsuarioDTO;
import com.productos.app.security.enums.RolNombre;
import com.productos.app.security.jwt.JwtProvider;
import com.productos.app.security.model.RolModel;
import com.productos.app.security.model.UsuarioModel;
import com.productos.app.security.services.RolService;
import com.productos.app.security.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	UsuarioService usuarioService;
	@Autowired
	RolService rolService;
	@Autowired
	JwtProvider jwtProvider;
	
	@PostMapping("/nuevo")
	public ResponseEntity<Object> nuevo(@RequestBody NuevoUsuarioDTO nuevoUsuarioDTO, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return new ResponseEntity(new MensajeDTO("Campos mal colocados"), HttpStatus.BAD_REQUEST);
		}
		
		if (usuarioService.existsByNombreUsuario(nuevoUsuarioDTO.getNombreUsuario())) {
			return new ResponseEntity(new MensajeDTO("El nombre de usuario ya existe"), HttpStatus.BAD_REQUEST);
	
		}
		if (usuarioService.existsByEmail(nuevoUsuarioDTO.getEmail())) {
			return new ResponseEntity(new MensajeDTO("El email ya existe"), HttpStatus.BAD_REQUEST);
	
		}
		
		UsuarioModel usuarioModel = new UsuarioModel(
										nuevoUsuarioDTO.getNombre(),
										nuevoUsuarioDTO.getNombreUsuario(),
										nuevoUsuarioDTO.getEmail(),
										passwordEncoder.encode(nuevoUsuarioDTO.getPassword())
									);
		
		Set<RolModel> roles = new HashSet<>();
		roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
		roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
		/*
		if (nuevoUsuarioDTO.getRoles().contains("admin")) {
			roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
		}
		*/
		
		usuarioModel.setRoles(roles);
		usuarioService.save(usuarioModel);
		
		return new ResponseEntity(new MensajeDTO("Usuario guardado"), HttpStatus.CREATED);
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody LoginUsuarioDTO loginUsuarioDTO, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return new ResponseEntity(new MensajeDTO("Campos mal colocados"), HttpStatus.BAD_REQUEST);
		}
		
		if(!(usuarioService.existsByNombreUsuarioOrEmail(loginUsuarioDTO.getNombreUsuario()))) {
			return new ResponseEntity(new MensajeDTO("Campos mal colocados"), HttpStatus.BAD_REQUEST);
		}
		
        return Autenticacion(loginUsuarioDTO.getNombreUsuario(), loginUsuarioDTO.getPassword());
		
	}
	
	public ResponseEntity<Object> Autenticacion(String username, String password) {
		
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        String jwt = jwtProvider.generateToken(authentication);
	        JwtDTO jwtDto = new JwtDTO(jwt);
	        return new ResponseEntity(jwtDto, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity(new MensajeDTO("Campos mal colocados"), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/refresh")
	public ResponseEntity<Object> refreshToken(@RequestBody JwtDTO jwtDTO) throws ParseException {
		try {
			String token = jwtProvider.refreshToken(jwtDTO);
			JwtDTO jwt = new JwtDTO(token);
			return new ResponseEntity<Object>(jwt, HttpStatus.OK);

		}catch (Exception e){
			return new ResponseEntity<Object>(new MensajeDTO(e.getMessage()), HttpStatus.OK);
		}
	}
	
	
}











