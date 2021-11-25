package com.fabian.co.webfluxcourse.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.fabian.co.webfluxcourse.models.documents.Categoria;

public interface CategoriaRepository extends ReactiveMongoRepository<Categoria, String> {

}
