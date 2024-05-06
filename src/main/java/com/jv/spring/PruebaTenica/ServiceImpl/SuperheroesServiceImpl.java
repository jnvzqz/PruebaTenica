package com.jv.spring.PruebaTenica.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jv.spring.PruebaTenica.Dto.SuperheroDto;
import com.jv.spring.PruebaTenica.Entity.SuperheroEntity;
import com.jv.spring.PruebaTenica.Exception.SuperheroNotFoundException;
import com.jv.spring.PruebaTenica.Mapper.MapStructMapper;
import com.jv.spring.PruebaTenica.Repository.SuperheroRepository;
import com.jv.spring.PruebaTenica.Service.SuperheroesService;

@Service
public class SuperheroesServiceImpl implements SuperheroesService {

	@Autowired
	private SuperheroRepository superheroRepo;
	@Autowired
	private MapStructMapper structMapper;
	//Method to create a new superhero
	@Override
	public SuperheroDto createSuperhero(SuperheroDto sh) {
		// We take the object from the DTO an turn it into a DB object and then save it  to the DB
		// for this task I decided to create ids sequentially for practical reasons, but a random UUID could be assigned as well
		SuperheroEntity superheroEntity = structMapper.superheroDtoToSuperheroEntity(sh);
		superheroRepo.saveAndFlush(superheroEntity);
		SuperheroDto newSuperhero = structMapper.superheroEntityToSuperheroDto(superheroEntity);
		return newSuperhero;
	}
	
	//Method to list all the superheroes
	@Override
	public List<SuperheroDto> getAllSuperheroes() {
		// We take all the superheroes from the DB
		List<SuperheroEntity> superheroes = superheroRepo.findAll();
		List<SuperheroDto> allSuperheroes = new ArrayList<>();
		// To transform the DB objects into a DTO we go through the list, convert each  object and add them to the final list
		for (SuperheroEntity sh : superheroes) {
			allSuperheroes.add(structMapper.superheroEntityToSuperheroDto(sh));
		}
		// Finally we return the list with all the superheroes
		return allSuperheroes;
	}
	
	//Method to get a specific superhero by the provided id
	@Override
	public SuperheroDto getSuperheroById(Long id) {
		//First we have to make sure that the superhero exists in the DB, if not, an exception is thrown
		SuperheroEntity superhero= superheroRepo.findById(id).orElseThrow(
				() -> new SuperheroNotFoundException("Superhero with id: " + id + " was not found in the DB"));
		SuperheroDto foundSuperhero = structMapper.superheroEntityToSuperheroDto(superhero);
		return foundSuperhero;
	}
	
	//Method to get all the superheroes that share a string in their name
	@Override
	public List<SuperheroDto> getAllSuperheroesContainingString(String string) {
		List<SuperheroEntity> superheroes = superheroRepo.findByHeroNameContainingIgnoreCase(string);
		List<SuperheroDto> allSuperheroesWithString = new ArrayList<>();
		// To transform the DB objects into a DTO we go through the list, convert each object and add them to the final list
		for (SuperheroEntity sh : superheroes) {
			allSuperheroesWithString.add(structMapper.superheroEntityToSuperheroDto(sh));
		}
		// Finally we return the list with all the superheroes
		return allSuperheroesWithString;
	}
	
	//Method to update an existing superhero
	@Override
	public SuperheroDto patchSuperhero(Long id, SuperheroDto patchedSuperhero) {
		//First we have to make sure that the superhero exists in the DB, if not, an exception is thrown
		SuperheroEntity superheroEntity = superheroRepo.findById(id).orElseThrow(
				() -> new SuperheroNotFoundException("Superhero with id: " + id + " was not found in the DB"));
		//Then we copy the new values to the DB object and save it
		structMapper.patch(structMapper.superheroDtoToSuperheroEntity(patchedSuperhero), superheroEntity);
		superheroRepo.saveAndFlush(superheroEntity);
		//Finally we take the new object and return it as a DTO with the new changes
		patchedSuperhero = structMapper.superheroEntityToSuperheroDto(superheroEntity);
		return patchedSuperhero;
	}

	//Method to delete an existing superhero
	@Override
	public void deleteSuperhero(Long id) {
		//Before deleting the object we check if it exists in the database
		superheroRepo.findById(id).orElseThrow(
				() -> new SuperheroNotFoundException("Superhero with id: " + id + " was not found in the DB"));
		superheroRepo.deleteById(id);		
	}

	
}
