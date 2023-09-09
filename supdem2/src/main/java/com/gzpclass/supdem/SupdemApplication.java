package com.gzpclass.supdem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
@EnableAutoConfiguration
public class SupdemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SupdemApplication.class, args);
	}

	protected SpringApplicationBuilder config(SpringApplicationBuilder applicationBuilder){
		return applicationBuilder.sources(SupdemApplication.class);
	}

}
