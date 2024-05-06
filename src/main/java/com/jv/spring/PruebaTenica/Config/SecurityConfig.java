package com.jv.spring.PruebaTenica.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		//security configuration: at this moment it is disabled for development
		//TODO: Enable proper security
		http.authorizeHttpRequests((requests) -> requests.requestMatchers("/localTestDB/**").authenticated()
				.requestMatchers("/h2-console/**").permitAll());
		http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());
		http.formLogin(Customizer.withDefaults());
		http.httpBasic(Customizer.withDefaults());
		http.csrf(AbstractHttpConfigurer::disable);
		http.headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
		return (SecurityFilterChain) http.build();
	}
}
