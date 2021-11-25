package com.fabian.co.webfluxcourse.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.fabian.co.webfluxcourse.models.documents.Producto;

public interface ProductoDao extends ReactiveMongoRepository<Producto, String> {

}
