package com.fabian.co.webfluxcourse;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.fabian.co.webfluxcourse.models.dao.CategoriaRepository;
import com.fabian.co.webfluxcourse.models.dao.ProductoDao;
import com.fabian.co.webfluxcourse.models.documents.Categoria;
import com.fabian.co.webfluxcourse.models.documents.Producto;

import reactor.core.publisher.Flux;

@EnableEurekaClient
@SpringBootApplication
public class SpringBootWebFluxApplication implements CommandLineRunner {

	@Autowired
	private ProductoDao dao;
	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ReactiveMongoTemplate template;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebFluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		template.dropCollection("productos").subscribe();
		template.dropCollection("categorias").subscribe();

		Categoria fritos = new Categoria("fritos");
		Categoria horneados = new Categoria("horneados");
		Categoria tipicos = new Categoria("tipicos");

		Flux.just(fritos, horneados, tipicos).flatMap(p -> {
			return categoriaRepository.insert(p);
		}).thenMany(Flux.just(new Producto("empanada", 1000.0, fritos), new Producto("arepa", 1300.0, tipicos),
				new Producto("pastel", 2000.0, fritos), new Producto("butifarra", 1300.0, tipicos),
				new Producto("morcilla", 4000.0, tipicos)).flatMap(p -> {
					p.setCreatedAt(new Date());
					return dao.insert(p);
				})).subscribe(r -> System.out.println(r.getNombre()));

	}

}
