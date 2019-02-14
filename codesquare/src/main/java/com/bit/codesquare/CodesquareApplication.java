package com.bit.codesquare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
@SpringBootApplication
public class CodesquareApplication {

	@Bean
	public LayoutDialect layoutDialect() {
	    return new LayoutDialect();
	}
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(CodesquareApplication.class, args);

	}


}
