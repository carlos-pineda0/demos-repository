package com.example.stream.namesink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

@SpringBootApplication
public class NameSinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(NameSinkApplication.class, args);
	}


}
