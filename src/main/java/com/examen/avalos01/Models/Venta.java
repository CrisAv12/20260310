package com.examen.avalos01.Models;

public class Venta {
	private int id,emp,clien,prod;
	private double cantidad;
	
	public Venta() {
		super();
	}

	public Venta(int id, int emp, int clien, int prod, double cantidad) {
		super();
		this.id = id;
		this.emp = emp;
		this.clien = clien;
		this.prod = prod;
		this.cantidad = cantidad;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEmp() {
		return emp;
	}

	public void setEmp(int emp) {
		this.emp = emp;
	}

	public int getClien() {
		return clien;
	}

	public void setClien(int clien) {
		this.clien = clien;
	}

	public int getProd() {
		return prod;
	}

	public void setProd(int prod) {
		this.prod = prod;
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
	
	
	
}
