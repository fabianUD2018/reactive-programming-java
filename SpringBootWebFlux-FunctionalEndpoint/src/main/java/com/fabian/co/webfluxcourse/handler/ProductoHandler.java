package com.fabian.co.webfluxcourse.handler;

import java.io.File;
import java.net.URI;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.FormFieldPart;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriBuilder;

import com.fabian.co.webfluxcourse.models.documents.Categoria;
import com.fabian.co.webfluxcourse.models.documents.Producto;
import com.fabian.co.webfluxcourse.services.ProductoService;

import reactor.core.publisher.Mono;

@Component
public class ProductoHandler {
	@Autowired
	private ProductoService servicio;
	
	@Value("${path}")
	private String path;
	public Mono<ServerResponse> crearConFoto(ServerRequest request){

        Mono<Producto> producto = request.multipartData().map(multipart -> {
        	FormFieldPart nombre = (FormFieldPart) multipart.toSingleValueMap().get("nombre");
        	FormFieldPart precio = (FormFieldPart) multipart.toSingleValueMap().get("precio");
        	FormFieldPart categoriaId = (FormFieldPart) multipart.toSingleValueMap().get("categoria.id");
        	FormFieldPart categoriaNombre = (FormFieldPart) multipart.toSingleValueMap().get("categoria.nombre");
        	
        	Categoria categoria = new Categoria(categoriaNombre.value());
        	categoria.setId(categoriaId.value());
        	return new Producto(nombre.value(), Double.parseDouble(precio.value()), categoria);
        });
		
		return request.multipartData().map(multipart -> multipart.toSingleValueMap().get("file"))
				.cast(FilePart.class)
				.flatMap(file -> producto
						.flatMap(p -> {
							
					p.setFoto(UUID.randomUUID().toString() + "-" + file.filename()
					.replace(" ", "-")
					.replace(":", "")
					.replace("\\", ""));
					
					p.setCreatedAt(new Date());
					
					return file.transferTo(new File(path + p.getFoto())).then(servicio.save(p));
				})).flatMap(p -> ServerResponse.created(URI.create("/api/v2/productos/".concat(p.getId())))
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.body(p, Producto.class));
	}
	
	public Mono<ServerResponse> upload(ServerRequest request){
		String id = request.pathVariable("id");
		return request.multipartData().map(multipart -> multipart.toSingleValueMap().get("file"))
				.cast(FilePart.class)
				.flatMap(file -> servicio.findById(id)
						.flatMap(p -> {
							
					p.setFoto(UUID.randomUUID().toString() + "-" + file.filename()
					.replace(" ", "-")
					.replace(":", "")
					.replace("\\", ""));
					return file.transferTo(new File(path + p.getFoto())).then(servicio.save(p));
				})).flatMap(p -> ServerResponse.created(URI.create("/api/v2/productos/".concat(p.getId())))
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.body(p, Producto.class))
				.switchIfEmpty(ServerResponse.notFound().build());
	}
	

	public Mono<ServerResponse> listar(ServerRequest request) {
		return ServerResponse.ok().body(servicio.findAll(), Producto.class);
	}

	public Mono<ServerResponse> postProducto(ServerRequest request) {
		Mono<Producto> producto = request.bodyToMono(Producto.class);

		return producto.flatMap(p -> {
			if (p.getCreatedAt() == null)
				p.setCreatedAt(new Date());

			return servicio.save(p);
		}).flatMap(p -> ServerResponse.ok().body(Mono.just(p), Producto.class));
		/*
		 * return ServerResponse.ok().body( producto.flatMap(p -> servicio.save(p)) ,
		 * Producto.class);
		 */
	}

	public Mono<ServerResponse> obtenerProducto(ServerRequest request) {
		String id = request.pathVariable("id");
		Mono<Producto> producto = servicio.findById(id);

		return producto.flatMap(p -> ServerResponse.ok().body(Mono.just(p), Producto.class))
				.switchIfEmpty(ServerResponse.notFound().build());
	}

	public Mono<ServerResponse> modificarProducto(ServerRequest request) {
		Mono<Producto> p = request.bodyToMono(Producto.class);
		Mono<Producto> pDB = servicio.findById(request.pathVariable("id"));
		return p.zipWith(pDB, (r, db) -> {
			db.setCategoria(r.getCategoria());
			db.setCreatedAt(new Date());
			db.setNombre(r.getNombre());
			db.setPrecio(r.getPrecio());
			return db;
		}).flatMap(pM -> ServerResponse.ok().body(servicio.save(pM), Producto.class));
	}
	
	public Mono<ServerResponse> eliminar(ServerRequest request) {
		Mono<Producto> pDB = servicio.findById(request.pathVariable("id"));
		return pDB.flatMap(pM -> ServerResponse.ok()
				.body(servicio.delete(pM), Producto.class))
				.switchIfEmpty(ServerResponse.notFound().build());
	}
}
