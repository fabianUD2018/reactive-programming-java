package com.fabian.co.webfluxcourse.services;

import com.fabian.co.webfluxcourse.models.documents.Categoria;
import com.fabian.co.webfluxcourse.models.documents.Producto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoService {

	public Flux<Producto> findAll();
	public Mono<Producto> findById(String id);
	public Mono<Producto> save(Producto p);
	public Mono<Void>  delete(Producto p);
	
	public Flux<Categoria> findAllCategoria();
	public Mono<Categoria> findCategoriaById(String id);
	public Mono<Categoria> saveCategoria(Categoria p);
}
