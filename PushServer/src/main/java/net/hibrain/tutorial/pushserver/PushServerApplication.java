package net.hibrain.tutorial.pushserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class PushServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PushServerApplication.class, args);

	}
}
