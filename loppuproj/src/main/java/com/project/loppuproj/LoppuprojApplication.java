package com.project.loppuproj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "com.project.loppuproj" })
public class LoppuprojApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoppuprojApplication.class, args);
	}

}
