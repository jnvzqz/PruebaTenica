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
	
	/**
	 * Creates a new superhero by taking the received DTO and turning it into a DB object and saving it
	 * into the DB. 
	 * The ids are set to be generated sequentially.
	 * 
	 * @params sh The received DTO with the data of the request.
	 * @return newSuperhero The new object added to the DB after it is turned back into a DTO. 
	 */	
	@Override
	public SuperheroDto createSuperhero(SuperheroDto sh) {
		SuperheroEntity superheroEntity = structMapper.superheroDtoToSuperheroEntity(sh);
		superheroRepo.saveAndFlush(superheroEntity);
		SuperheroDto newSuperhero = structMapper.superheroEntityToSuperheroDto(superheroEntity);
		return newSuperhero;
	}
	

	/**
	 * Lists all the superheroes by retrieving them from the DB and converting them into a DTO
	 * by going through the list, converting them individually into a DTO and adding them into 
	 * the final list.
	 *
	 * @return allSuperheroes The list of all the superheroes.
	 */
	@Override
	public List<SuperheroDto> getAllSuperheroes() {
		List<SuperheroEntity> superheroes = superheroRepo.findAll();
		List<SuperheroDto> allSuperheroes = new ArrayList<>();
		for (SuperheroEntity sh : superheroes) {
			allSuperheroes.add(structMapper.superheroEntityToSuperheroDto(sh));
		}
		return allSuperheroes;
	}
	
	/**
	 * Gets an specific superhero from the DB by the provided id and returning it as a DTO. 
	 * If the specified id does not exist in the DB an exception ins thrown.
	 * 
	 * @params id The specified id
	 * @return foundSuperhero The superhero found by the id
	 * @throws SuperheroNotFoundException Exception that notifies that the specified id does not exist in the DB
	 */
	@Override
	public SuperheroDto getSuperheroById(Long id) {
		SuperheroEntity superhero= superheroRepo.findById(id).orElseThrow(
				() -> new SuperheroNotFoundException("Superhero with id: " + id + " was not found in the DB"));
		SuperheroDto foundSuperhero = structMapper.superheroEntityToSuperheroDto(superhero);
		return foundSuperhero;
	}
	
	/**
	 * Lists all the superheroes that share a string in their name by retrieving them from the DB and converting 
	 * them into a DTO by going through the list, converting them individually into a DTO and adding them into 
	 * the final list.
	 * 
	 * @param string The string specified in the petition.
	 * @return allSuperheroesWithString All the superheroes found that share that string
	 */
	@Override
	public List<SuperheroDto> getAllSuperheroesContainingString(String string) {
		List<SuperheroEntity> superheroes = superheroRepo.findByHeroNameContainingIgnoreCase(string);
		List<SuperheroDto> allSuperheroesWithString = new ArrayList<>();
		for (SuperheroEntity sh : superheroes) {
			allSuperheroesWithString.add(structMapper.superheroEntityToSuperheroDto(sh));
		}
		return allSuperheroesWithString;
	}
	
	/**
	 * Updates an existing superhero by retrieving it from the DB and patching the changed fields with a mapper
	 * and then returning the new patched object from the DB. If the specified id does not exist in the DB 
	 * an exception ins thrown.
	 * 
	 * @params id The specified id.
	 * @return patchedSuperhero The superhero that was updated with the new changes.
	 * @throws SuperheroNotFoundException Exception that notifies that the specified id does not exist in the DB
	 */
	@Override
	public SuperheroDto patchSuperhero(Long id, SuperheroDto patchedSuperhero) {
		SuperheroEntity superheroEntity = superheroRepo.findById(id).orElseThrow(
				() -> new SuperheroNotFoundException("Superhero with id: " + id + " was not found in the DB"));
		structMapper.patch(structMapper.superheroDtoToSuperheroEntity(patchedSuperhero), superheroEntity);
		superheroRepo.saveAndFlush(superheroEntity);
		patchedSuperhero = structMapper.superheroEntityToSuperheroDto(superheroEntity);
		return patchedSuperhero;
	}

	/**
	 * Deletes an specific superhero from the DB by the specified id. 
	 * If the specified id does not exist in the DB an exception ins thrown.
	 * 
	 * @params id The specified id
	 * @throws SuperheroNotFoundException Exception that notifies that the specified id does not exist in the DB
	 */	@Override
	public void deleteSuperhero(Long id) {
		superheroRepo.findById(id).orElseThrow(
				() -> new SuperheroNotFoundException("Superhero with id: " + id + " was not found in the DB"));
		superheroRepo.deleteById(id);		
	}

	
}
