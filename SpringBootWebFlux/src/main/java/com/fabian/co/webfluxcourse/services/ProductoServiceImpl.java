package com.fabian.co.webfluxcourse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fabian.co.webfluxcourse.models.dao.CategoriaRepository;
import com.fabian.co.webfluxcourse.models.dao.ProductoDao;
import com.fabian.co.webfluxcourse.models.documents.Categoria;
import com.fabian.co.webfluxcourse.models.documents.Producto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductoServiceImpl implements ProductoService {
	
	
	@Autowired
	private ProductoDao productoDao;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Override
	public Flux<Producto> findAll() {
		// TODO Auto-generated method stub
		return productoDao.findAll();
	}

	@Override
	public Mono<Producto> findById(String id) {
		// TODO Auto-generated method stub
		return productoDao.findById(id);
	}

	@Override
	public Mono<Producto> save(Producto p) {
		// TODO Auto-generated method stub
		return productoDao.save(p);
	}

	@Override
	public Mono<Void> delete(Producto p) {
		// TODO Auto-generated method stub
		return productoDao.delete(p);
	}

	@Override
	public Flux<Categoria> findAllCategoria() {
		// TODO Auto-generated method stub
		return categoriaRepository.findAll();
	}

	@Override
	public Mono<Categoria> findCategoriaById(String id) {
		// TODO Auto-generated method stub
		return categoriaRepository.findById(id);
	}

	@Override
	public Mono<Categoria> saveCategoria(Categoria p) {
		// TODO Auto-generated method stub
		return categoriaRepository.save(p);
	}
	
	

}
