package com.ca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CAHandlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CAHandlerApplication.class, args);
	}

}
