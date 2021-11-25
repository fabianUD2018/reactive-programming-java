package com.fabian.co;

import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Flux.create(
				emmiter -> { 
			Timer t = new  Timer();
			t.schedule(new TimerTask() {
				Integer contador=0;

				@Override
				public void run() {
					emmiter.next(++contador);
					emmiter.complete();
					if (contador >= 5) { 
						emmiter.error(new InterruptedException("errror por el contador"));
					}
				}
			}, 1000, 1000);
			
		})
		.subscribe(i ->System.out.println(i.toString()),e-> System.err.println(e.getMessage()), ()-> System.out.println("finalizo el flux")  );
		
	}
	
	
	public void exampleInfinteFlux() throws InterruptedException {
		CountDownLatch latch = new CountDownLatch(1); 
		Flux.interval(Duration.ofSeconds(1))
		.doOnTerminate(()->latch.countDown())
		.flatMap(i->{ 
			if (i>=5 ) {
				return Flux.error(new InterruptedException("Solo hasta 5"));
			}else {
				return Flux.just(i);	
			}
			
		})
		.map(i-> "Me estoy ejecutando")
		.doOnNext(System.out::println)
		.subscribe(System.out::println, e-> System.err.print(e.getMessage()));
		//.blockLast();
		latch.await();
	}
	
	public void example1() { 
		Flux<String> nombres = Flux.just("juan", "andres", "pedro")
				
				.map(nombre -> nombre.substring(0, 1).toUpperCase().concat(nombre.substring(1)))
		.doOnNext(elemento -> System.out.println(elemento));
		
		nombres.subscribe( name -> System.out.println(name.toUpperCase()), error-> error.printStackTrace(), new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("Flujo terminado");
			}
		});
	}
	

}
