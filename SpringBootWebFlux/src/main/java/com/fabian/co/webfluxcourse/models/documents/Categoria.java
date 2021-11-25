package com.fabian.co.webfluxcourse.models.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "categorias")
public class Categoria {
	
	@Id
	private String id;
	
	private String nombre;
	
	

	public Categoria() {
		super();
	}



	public Categoria( String nombre) {
		super();
		this.nombre = nombre;
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
	
	

}
