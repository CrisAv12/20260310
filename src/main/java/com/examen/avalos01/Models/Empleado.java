package com.examen.avalos01.Models;

public class Empleado {
	private int ci;
	private String nombre, apellido;
	private int telefono;
	private String email;
	
	public Empleado() {
		super();
	}

	public Empleado(int ci, String nombre, String apellido, int telefono, String email) {
		super();
		this.ci = ci;
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.email = email;
	}

	public int getCi() {
		return ci;
	}

	public void setCi(int ci) {
		this.ci = ci;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
	
	
	
}
