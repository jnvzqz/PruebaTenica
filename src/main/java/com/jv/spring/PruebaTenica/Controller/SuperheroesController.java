package com.jv.spring.PruebaTenica.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jv.spring.PruebaTenica.Advice.TrackExecutionTime;
import com.jv.spring.PruebaTenica.Dto.SuperheroDto;
import com.jv.spring.PruebaTenica.Dto.SuperheroResponse;
import com.jv.spring.PruebaTenica.Service.SuperheroService;

import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
public class SuperheroesController {
	
	/**
	 * All the logic is removed from the controller and done in the service.
	 */
	
	/**
	 * Post mapping for the creation of a new superhero.
	 * 
	 * @param superheroDto The body of the request in dto format.
	 * @Return newSuperhero The created new superhero and the response.
	 */
	@Autowired
	private SuperheroService superheroesService;
	@TrackExecutionTime
	@PostMapping("/addSuperhero")
	public ResponseEntity<SuperheroDto> addSuperhero(@RequestBody SuperheroDto superheroDto) {
		SuperheroDto newSuperhero = superheroesService.createSuperhero(superheroDto);
		log.info("New superhero created with id: "+ newSuperhero.getId());
		return ResponseEntity.ok(newSuperhero) ;
	}
	/**
	 * Get mapping for the listing of all superheroes.
	 * 
	 * @param pages The number of pages.
	 * @param pageSize The size of the page.
	 * @return allSuperheroes The list of all the superheroes with pagination.
	 */
	@TrackExecutionTime
	@GetMapping("/getAllSuperheroes")
    public SuperheroResponse getAllSuperheroes(
    		@RequestParam(value = "pages", defaultValue = "0", required = false) int pages,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize){
        return superheroesService.getAllSuperheroes(pages, pageSize);
    }
	
	/**
	 * Get mapping for the listing of an specific superhero.
	 * 
	 * @param Id The specified id.
	 * @Return superhero The superhero found by the id and the response.
	 */
	@TrackExecutionTime
	@GetMapping("/getSuperheroById/{id}")
	public ResponseEntity<SuperheroDto> getSuperheroById(@PathVariable Long id) {
		SuperheroDto superhero = superheroesService.getSuperheroById(id);
		log.info("New superhero with id: "+ superhero.getId() + "was found in the DB");
        return ResponseEntity.ok(superhero);
    }
	
	/**
	 * Get mapping for listing of all superheroes that share a string in their name.
	 * 
	 * @param name The string specified in the petition.
	 * @Return superheroesContainingString All the superheroes found that share that string.
	 */
	@TrackExecutionTime
	@GetMapping("/getSuperheroByName/{name}")
	public List<SuperheroDto> getSuperheroByName(@PathVariable String name) {
		return superheroesService.getAllSuperheroesContainingString(name);
    }
	
	/**
	 * Patch mapping for updating a superhero.
	 * 
	 * @param Id The specified id.
	 * @param superheroDto The body of the request in dto format.
	 * @Return patchedSuperhero The updated superhero.
	 */
	@TrackExecutionTime
	@PatchMapping("/patchSuperhero/{id}")
	public ResponseEntity<SuperheroDto> patchSuperhero(@PathVariable Long id,@RequestBody SuperheroDto superheroDto) {		
		SuperheroDto patchedSuperhero =superheroesService.patchSuperhero(id, superheroDto);
		log.info("New superhero with id: "+ patchedSuperhero.getId() + "was updated in the DB");
		return ResponseEntity.ok(patchedSuperhero) ;
	}
	
	/**
	 * Delete mapping for the deletion of a superhero.
	 * 
	 * @param Id The specified id.
	 * @Return response The response of the petition.
	 */
	@TrackExecutionTime
	@DeleteMapping("/deleteSuperhero/{id}")
	public ResponseEntity<Void> deleteSuperhero(@PathVariable Long id) {		
		superheroesService.deleteSuperhero(id);
		log.info("Superhero with id: "+ id + "was deleted from the DB");
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
