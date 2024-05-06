package com.jv.spring.PruebaTenica.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.HeadersBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jv.spring.PruebaTenica.Dto.SuperheroDto;
import com.jv.spring.PruebaTenica.Entity.SuperheroEntity;
import com.jv.spring.PruebaTenica.Service.SuperheroesService;

import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
public class SuperheroesController {

	@Autowired
	private SuperheroesService superheroesService;
	//All the logic is removed from the controller and done in the service
	@PostMapping("/addSuperhero")
	public ResponseEntity<SuperheroDto> addSuperhero(@RequestBody SuperheroDto superheroDto) {
		SuperheroDto newSuperhero = superheroesService.createSuperhero(superheroDto);
		log.info("New superhero created with id: "+ newSuperhero.getId());
		return ResponseEntity.ok(newSuperhero) ;
	}

	@GetMapping("/getAllSuperheroes")
    public List<SuperheroDto> getAllSuperheroes() {
        return superheroesService.getAllSuperheroes().stream().collect(Collectors.toList());
    }
	
	@GetMapping("/getSuperheroById/{id}")
	public ResponseEntity<SuperheroDto> getSuperheroById(@PathVariable Long id) {
		SuperheroDto superhero = superheroesService.getSuperheroById(id);
        return ResponseEntity.ok(superhero);
    }	
	@GetMapping("/getSuperheroByName/{name}")
	public List<SuperheroDto> getSuperheroByName(@PathVariable String name) {
		return superheroesService.getAllSuperheroesContainingString(name).stream().collect(Collectors.toList());
    }	
	
	@PatchMapping("/patchSuperhero/{id}")
	public ResponseEntity<SuperheroDto> patchSuperhero(@PathVariable Long id, @RequestBody SuperheroEntity superheroEntity) {		
		SuperheroDto patchedSuperhero =superheroesService.patchSuperhero(id, superheroEntity);
		return ResponseEntity.ok(patchedSuperhero) ;
	}
	
	@DeleteMapping("/deleteSuperhero/{id}")
	public ResponseEntity<Void> deleteSuperhero(@PathVariable Long id) {		
		superheroesService.deleteSuperhero(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	

}
