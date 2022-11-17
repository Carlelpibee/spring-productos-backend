package com.productos.app.security.model;

import com.productos.app.security.enums.RolNombre;

import javax.persistence.*;

@Entity
@Table(name="roles")
public class RolModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Enumerated(EnumType.STRING)
	private RolNombre rolNombre;

	public RolModel() {
		super();
	}

	public RolModel(RolNombre rolNombre) {
		super();
		this.rolNombre = rolNombre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public RolNombre getRolNombre() {
		return rolNombre;
	}

	public void setRolNombre(RolNombre rolNombre) {
		this.rolNombre = rolNombre;
	}
	
	
	
}
