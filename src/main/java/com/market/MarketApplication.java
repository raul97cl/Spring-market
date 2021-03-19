package com.market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class MarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketApplication.class, args);
	}
	
	/*@Bean
	public CommandLineRunner initData(UserService usuarioServicio, ProductService productoServicio) {
		return args -> {

			User usuario = new User("Luis Miguel", "López Magaña", null, "luismi.lopez@openwebinars.net", "luismi");
			usuario = usuarioServicio.signUp(usuario);

			User usuario2 = new User("Antonio", "García Martín", null, "antonio.garcia@openwebinars.net", "antonio");
			usuario2 = usuarioServicio.signUp(usuario2);

			
			List<Product> listado = Arrays.asList(new Product("Bicicleta de montaña", 100.0f,
					"https://cdn.pixabay.com/photo/2013/07/13/13/46/bicycle-161524_960_720.png", usuario),
					new Product("Golf GTI Serie 2", 2500.0f,
							"https://www.minicar.es/large/Volkswagen-Golf-GTi-G60-Serie-II-%281990%29-Norev-1%3A18-i22889.jpg",
							usuario2),
					new Product("Raqueta de tenis", 10.5f, "https://cdn.pixabay.com/photo/2014/04/03/10/01/tennis-309621_960_720.png", usuario),
					new Product("Xbox One X", 425.0f, "https://images.vibbo.com/635x476/860/86038583196.jpg", usuario),
					new Product("Trípode flexible", 10.0f, "https://cdn.pixabay.com/photo/2011/03/24/12/10/tripod-5870_960_720.jpg", usuario2),
					new Product("Iphone 7 128 GB", 350.0f, "https://store.storeimages.cdn-apple.com/4667/as-images.apple.com/is/image/AppleInc/aos/published/images/i/ph/iphone7/rosegold/iphone7-rosegold-select-2016?wid=470&hei=556&fmt=jpeg&qlt=95&op_usm=0.5,0.5&.v=1472430205982", usuario2));
			
			listado.forEach(productoServicio::save);
			

		};
		
	
	}*/
}
