package com.jv.spring.PruebaTenica.Service;

import java.util.List;

import com.jv.spring.PruebaTenica.Dto.SuperheroDto;
import com.jv.spring.PruebaTenica.Dto.SuperheroResponse;

public interface SuperheroService {

	SuperheroResponse getAllSuperheroes(int pages, int pageSize);

	List<SuperheroDto> getAllSuperheroesContainingString(String string);

	SuperheroDto createSuperhero(SuperheroDto sh);

	SuperheroDto getSuperheroById(Long id);

	void deleteSuperhero(Long id);

	SuperheroDto patchSuperhero(Long id, SuperheroDto sh);
	
}
