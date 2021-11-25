package com.fabian.co.webfluxcourse.models.documents;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection="productos")
public class Producto {

	@Id
	private String id;
	
	private String nombre;
	
	private Double precio;
	
	private Categoria categoria;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdAt;
	
	private String foto;
	

	public Producto() {
		super();
	}

	
	
	
	public Producto(String nombre, Double precio) {
		super();
		this.nombre = nombre;
		this.precio = precio;
	}

	public Producto(String nombre, Double precio, Categoria c) {
		super();
		this.nombre = nombre;
		this.precio = precio;
		this.categoria = c;
	}



	public Producto(String nombre, Double precio, Date createdAt) {
		super();
		this.nombre = nombre;
		this.precio = precio;
		this.createdAt = createdAt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}




	public String getFoto() {
		return foto;
	}




	public void setFoto(String foto) {
		this.foto = foto;
	}




	public Categoria getCategoria() {
		return categoria;
	}




	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	
	

}
