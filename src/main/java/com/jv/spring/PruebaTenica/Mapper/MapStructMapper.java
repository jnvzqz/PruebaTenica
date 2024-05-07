package com.jv.spring.PruebaTenica.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.jv.spring.PruebaTenica.Dto.SuperheroDto;
import com.jv.spring.PruebaTenica.Entity.SuperheroEntity;

@Mapper(
	    componentModel = "spring", 
	    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
	   )
public interface MapStructMapper {

	SuperheroDto superheroEntityToSuperheroDto(SuperheroEntity superheroes);
    SuperheroEntity superheroDtoToSuperheroEntity (SuperheroDto superheroes);
    void patch(SuperheroEntity superheroEntityPatched, @MappingTarget SuperheroEntity superheroEntity);
}