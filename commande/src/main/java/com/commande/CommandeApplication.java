package com.commande;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class CommandeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommandeApplication.class, args);
	}

}
