package com.jv.spring.PruebaTenica.Service;

import java.util.List;

import com.jv.spring.PruebaTenica.Dto.SuperheroDto;
import com.jv.spring.PruebaTenica.Dto.SuperheroResponse;

public interface SuperheroService {
	
	SuperheroDto createSuperhero(SuperheroDto sh);

	SuperheroResponse getAllSuperheroes(int pages, int pageSize);
	
	SuperheroDto getSuperheroById(Long id);

	List<SuperheroDto> getAllSuperheroesContainingString(String string);	

	SuperheroDto patchSuperhero(Long id, SuperheroDto sh);

	void deleteSuperhero(Long id);

	
	
}
