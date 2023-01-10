package com.example.demo;

import org.springframework.boot.SpringApplication;



import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
//ici on indique ou scanner les entites a mapper en db
@EntityScan("com.example.demo.entities")

public class CoursSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoursSpringbootApplication.class, args);
	}

}
