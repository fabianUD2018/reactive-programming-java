package com.fabian.co.webfluxcourse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.fabian.co.webfluxcourse.handler.ProductoHandler;
import com.fabian.co.webfluxcourse.models.documents.Producto;
import com.fabian.co.webfluxcourse.services.ProductoService;

@Configuration
public class RouterFunctionConfig {

	@Autowired
	private ProductoService servicio;
	
	@Bean
	public RouterFunction<ServerResponse> routes(){
		
		return RouterFunctions
				.route(RequestPredicates
						.GET("api/v2/productos"), 
						request->
						ServerResponse.ok().body(servicio.findAll(), Producto.class));
	}
	

	@Bean
	public RouterFunction<ServerResponse> postProducto(ProductoHandler handler){
		
		return RouterFunctions
				.route(RequestPredicates
						.POST("api/v2/producto"), 
						request-> handler.postProducto(request)
						)
				.andRoute(RequestPredicates.DELETE("api/v2/producto/{id}"), handler::eliminar);
	}
	
	@Bean
	public RouterFunction<ServerResponse> getProducto(ProductoHandler handler){
		
		return RouterFunctions
				.route(RequestPredicates
						.GET("api/v3/productos/{id}"), 
						request-> handler.obtenerProducto(request)
						);
	}
	
	@Bean
	public RouterFunction<ServerResponse> editProducto(ProductoHandler handler){
		
		return RouterFunctions
				.route(RequestPredicates
						.PUT("api/v3/producto/{id}"), 
						request-> handler.modificarProducto(request)
						);
	}
	
}
