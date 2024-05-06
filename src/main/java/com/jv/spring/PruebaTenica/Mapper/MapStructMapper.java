package com.jv.spring.PruebaTenica.Mapper;

import org.mapstruct.Mapper;

import com.jv.spring.PruebaTenica.Dto.SuperheroDto;
import com.jv.spring.PruebaTenica.Entity.SuperheroEntity;

@Mapper(
	    componentModel = "spring"
	)
public interface MapStructMapper {

	SuperheroDto superheroEntityToSuperheroDto(SuperheroEntity superheroes);
	
    SuperheroEntity superheroDtoToSuperheroEntity (SuperheroDto superheroes);
}