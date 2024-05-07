package com.jv.spring.PruebaTenica.ServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jv.spring.PruebaTenica.Dto.SuperheroDto;
import com.jv.spring.PruebaTenica.Dto.SuperheroResponse;
import com.jv.spring.PruebaTenica.Entity.SuperheroEntity;
import com.jv.spring.PruebaTenica.Exception.SuperheroNotFoundException;
import com.jv.spring.PruebaTenica.Mapper.MapStructMapper;
import com.jv.spring.PruebaTenica.Repository.SuperheroRepository;
import com.jv.spring.PruebaTenica.Service.SuperheroService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SuperheroServiceImpl implements SuperheroService {

	@Autowired
	private SuperheroRepository superheroRepo;
	@Autowired
	private MapStructMapper structMapper;
	

	/**
	 * Creates a new superhero by taking the received DTO and turning it into a DB object and saving it
	 * into the DB. 
	 * The ids are set to be generated sequentially.
	 * 
	 * @param sh The received DTO with the data of the request.
	 * @return newSuperhero The new object added to the DB after it is turned back into a DTO. 
	 */
	
	@Cacheable(value = "entity", cacheManager = "springCM")
	@Override
	public SuperheroDto createSuperhero(SuperheroDto sh) {
		SuperheroEntity superheroEntity = structMapper.superheroDtoToSuperheroEntity(sh);
		superheroRepo.saveAndFlush(superheroEntity);
		SuperheroDto newSuperhero = structMapper.superheroEntityToSuperheroDto(superheroEntity);
		return newSuperhero;
	}
	

	/**
	 * Lists all the superheroes by retrieving them from the DB and converting them into a DTO
	 * by going through the list, converting them into a DTO and adding them into 
	 * the final list.
	 *
	 * @param pages The number of pages.
	 * @param pageSize The size of the page.
	 * @return allSuperheroes The list of all the superheroes.
	 */
	
	@Override
	public SuperheroResponse getAllSuperheroes(int pages, int pageSize) {
		Pageable pageable = PageRequest.of(pages, pageSize);
		Page<SuperheroEntity> superheroes = superheroRepo.findAll(pageable);
		List<SuperheroEntity> superheroesList = superheroes.getContent();
		List<SuperheroDto> allSuperheroes = superheroesList.stream()
				.map(s -> structMapper.superheroEntityToSuperheroDto(s)).collect(Collectors.toList());

		allSuperheroes = allSuperheroes.stream().collect(Collectors.toList());
			
		SuperheroResponse superheroResponse = new SuperheroResponse();
        superheroResponse.setContent(allSuperheroes);
        superheroResponse.setPageNo(superheroes.getNumber());
        superheroResponse.setPageSize(superheroes.getSize());
        superheroResponse.setTotalElements(superheroes.getTotalElements());
        superheroResponse.setTotalPages(superheroes.getTotalPages());
        superheroResponse.setLast(superheroes.isLast());
		
        log.info(superheroes.getTotalElements() +" where found in the DB");

		return superheroResponse;
	}
	
	/**
	 * Gets an specific superhero from the DB by the provided id and returning it as a DTO. 
	 * If the specified id does not exist in the DB an exception ins thrown.
	 * 
	 * @param id The specified id.
	 * @return foundSuperhero The superhero found by the id.
	 * @throws SuperheroNotFoundException Exception that notifies that the specified id does not exist in the DB.
	 */
	@Cacheable(value = "entity", cacheManager = "springCM")
	@Override
	public SuperheroDto getSuperheroById(Long id) {
		SuperheroEntity superhero= superheroRepo.findById(id).orElseThrow(
				() -> new SuperheroNotFoundException("Superhero with id: " + id + " was not found in the DB"));
		SuperheroDto foundSuperhero = structMapper.superheroEntityToSuperheroDto(superhero);
		return foundSuperhero;
	}
	
	/**
	 * Lists all the superheroes that share a string in their name by retrieving them from the DB and converting 
	 * them into a DTO by going through the list, converting them into a DTO and adding them into 
	 * the final list.
	 * 
	 * @param string The string specified in the petition.
	 * @return allSuperheroesWithString All the superheroes found that share that string.
	 */
	
	@Cacheable(value = "entity", cacheManager = "springCM")
	@Override
	public List<SuperheroDto> getAllSuperheroesContainingString(String string) {
		List<SuperheroEntity> superheroes = superheroRepo.findByHeroNameContainingIgnoreCase(string);
		List<SuperheroDto> allSuperheroesWithString = superheroes.stream()
				.map(s -> structMapper.superheroEntityToSuperheroDto(s)).collect(Collectors.toList());
		
        log.info(superheroes.size() +" where found in the DB with the matching string");

		return allSuperheroesWithString;
	}
	
	/**
	 * Updates an existing superhero by retrieving it from the DB and patching the changed fields with a mapper
	 * and then returning the new patched object from the DB. If the specified id does not exist in the DB 
	 * an exception ins thrown.
	 * 
	 * @param id The specified id.
	 * @param patchedSuperhero The data to be changed in the superhero object.
	 * @return patchedSuperhero The superhero that was updated with the new changes.
	 * @throws SuperheroNotFoundException Exception that notifies that the specified id does not exist in the DB.
	 */
	
	@Cacheable(value = "entity", cacheManager = "springCM")
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
	 * @param id The specified id.
	 * @throws SuperheroNotFoundException Exception that notifies that the specified id does not exist in the DB.
	 */	
	
	@Cacheable(value = "entity", cacheManager = "springCM")
	@Override
	public void deleteSuperhero(Long id) {
		superheroRepo.findById(id).orElseThrow(
				() -> new SuperheroNotFoundException("Superhero with id: " + id + " was not found in the DB"));
		superheroRepo.deleteById(id);		
	}

	
}
