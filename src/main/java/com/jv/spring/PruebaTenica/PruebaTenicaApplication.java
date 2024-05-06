package com.jv.spring.PruebaTenica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
public class PruebaTenicaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PruebaTenicaApplication.class, args);
	}
	
}
