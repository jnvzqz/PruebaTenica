package com.jv.spring.PruebaTenica.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SuperheroDto {
	
	 public static class SuperheroDtoBuilder {}

	/**
	 * Identifier of the superhero
	 */
	@JsonProperty("Id")
	private Long id;
	
	/**
	 * Name of the superhero
	 */
    @NotNull
	@JsonProperty("HeroName")
	private String heroName;
    
    /**
	 * Real name of the superhero
	 */
	@JsonProperty("RealName")
	private String realName;
	
	/**
	 * Superpower of the superhero
	 */
	@JsonProperty("SuperPower")
	private String superpower;
	
}
