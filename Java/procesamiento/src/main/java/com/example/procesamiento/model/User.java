package com.example.procesamiento.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nombre;
	private String centroVisita;
	private int edad;
	private String email;
	private String password;
	private String rol;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_center", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "center_id"))
	private Set<Center> centers = new HashSet<>();

	public Set<Center> getCenters() {
		return centers;
	}

	public void setCenters(Set<Center> centers) {
		this.centers = centers;
	}

	public User() {
	}

	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public User(int id, String nombre, String centroVisita, int edad, String email, String password, String rol) {
		this.id = id;
		this.nombre = nombre;
		this.centroVisita = centroVisita;
		this.email = email;
		this.password = password;
		this.rol = rol;
		this.edad = edad;
	}

	public User(String nombre, String centroVisita, int edad, String email, String rol) {
		this.nombre = nombre;
		this.centroVisita = centroVisita;
		this.email = email;
		this.rol = rol;
		this.edad = edad;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCentroVisita() {
		return centroVisita;
	}

	public void setCentroVisita(String centroVisita) {
		this.centroVisita = centroVisita;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

}