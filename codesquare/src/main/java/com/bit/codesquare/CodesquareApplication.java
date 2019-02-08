package com.bit.codesquare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
@SpringBootApplication
public class CodesquareApplication {

	@Bean
	public LayoutDialect layoutDialect() {
	    return new LayoutDialect();
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(CodesquareApplication.class, args);
	}


}

