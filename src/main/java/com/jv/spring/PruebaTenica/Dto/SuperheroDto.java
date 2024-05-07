package com.jv.spring.PruebaTenica.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SuperheroDto {
	
	@JsonProperty("Id")
	private Long id;
    @NotNull
	@JsonProperty("HeroName")
	private String heroName;
	@JsonProperty("RealName")
	private String realName;
	@JsonProperty("SuperPower")
	private String superpower;
	
	@Override
	public String toString() {
		return String.format("SuperheroesEntity[id=%d, heroName='%s', realName='%s', superpower='%s'",
		id, heroName, realName, superpower);
	}


}
