package com.jv.spring.PruebaTenica.Service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jv.spring.PruebaTenica.Dto.SuperheroDto;
import com.jv.spring.PruebaTenica.Dto.SuperheroResponse;
import com.jv.spring.PruebaTenica.Entity.SuperheroEntity;
import com.jv.spring.PruebaTenica.Mapper.MapStructMapper;
import com.jv.spring.PruebaTenica.Repository.SuperheroRepository;
import com.jv.spring.PruebaTenica.ServiceImpl.SuperheroServiceImpl;

@ExtendWith(MockitoExtension.class)
public class SuperheroServiceTest {
	
	@Mock
	private SuperheroRepository superheroRepository;
	@Spy 
	private MapStructMapper mapper = Mappers.getMapper(MapStructMapper.class);
	@InjectMocks
	private SuperheroServiceImpl superheroService;
	
	/**
	 * Test of the {@link SuperheroService#createSuperhero(SuperheroDto)} method.
	 */
	@Test
	public void SuperheroService_CreateSuperhero_ReturnsSuperheroDto() {
		SuperheroEntity supeheroEntity = SuperheroEntity.builder()
				.heroName("Spiderman")
				.realName("Peter Parker")
				.superpower("Spider powers").build();
		SuperheroDto superheroDto = SuperheroDto.builder()
				.heroName("Spiderman")
				.realName("Peter Parker")
				.superpower("Spider powers").build();
		
		when(superheroRepository.saveAndFlush(Mockito.any(SuperheroEntity.class))).thenReturn(supeheroEntity);			
		
		SuperheroDto savedSuperhero = superheroService.createSuperhero(superheroDto);
		
		Assertions.assertThat(savedSuperhero).isNotNull();
	}
	
	/**
	 * Test of the {@link SuperheroService#getAllSuperheroes(Int, int)} method.
	 */
	@Test
	public void SuperheroService_GetAllSuperheroes_ReturnsResponseDto(){
		Page<SuperheroEntity> superheroes = Mockito.mock(Page.class);
		
		when(superheroRepository.findAll(Mockito.any(Pageable.class))).thenReturn(superheroes);			

		SuperheroResponse allSuperheroes = superheroService.getAllSuperheroes(1, 10);
		
		Assertions.assertThat(allSuperheroes).isNotNull();
	}
	
	/**
	 * Test of the {@link SuperheroService#getSuperheroById(Long)} method.
	 */
	@Test
	public void SuperheroService_GetSuperheroesById_ReturnsResponseDto() {
		Long id = 1L;
		SuperheroEntity supeheroEntity = SuperheroEntity.builder()
				.heroName("Spiderman")
				.realName("Peter Parker")
				.superpower("Spider powers").build();
		
		when(superheroRepository.findById(id)).thenReturn(Optional.ofNullable(supeheroEntity));
		
		SuperheroDto foundSuperhero = superheroService.getSuperheroById(id);
		
		Assertions.assertThat(foundSuperhero).isNotNull();
	}
	
	/**
	 * Test of the {@link SuperheroService#getAllSuperheroesContainingString(String)} method.
	 */
	@Test
	public void SuperheroService_GetAllSuperheroesContainingString_ReturnsResponseDto() {
		String name = "man";
		SuperheroEntity supeheroEntity = SuperheroEntity.builder()
				.heroName("Spiderman")
				.realName("Peter Parker")
				.superpower("Spider powers").build();
		List<SuperheroEntity> superheroList = new ArrayList<SuperheroEntity>();
		superheroList.add(supeheroEntity);
		
		when(superheroRepository.findByHeroNameContainingIgnoreCase(name)).thenReturn(superheroList);
		
		List<SuperheroDto> foundSuperheroes = superheroService.getAllSuperheroesContainingString(name);
	
		Assertions.assertThat(foundSuperheroes).isNotNull();
	}
	
	/**
	 * Test of the {@link SuperheroService#patchSuperhero(Long, SuperheroDto)} method.
	 */
	@Test
	public void SuperheroService_PatchSuperhero_ReturnsResponseDto() {
		Long id = 1L;

		SuperheroEntity supeheroEntity = SuperheroEntity.builder()
				.heroName("Spiderman")
				.realName("Peter Parker")
				.superpower("Spider powers").build();
		SuperheroDto superheroDto = SuperheroDto.builder()
				.heroName("Spiderpig")
				.realName("Peter Porker")
				.superpower("Spider and pig powers").build();
		
		when(superheroRepository.findById(id)).thenReturn(Optional.ofNullable(supeheroEntity));
		when(superheroRepository.saveAndFlush(Mockito.any(SuperheroEntity.class))).thenReturn(supeheroEntity);
		
		SuperheroDto updatedSuperhero = superheroService.patchSuperhero(id, superheroDto);
		
		Assertions.assertThat(updatedSuperhero).isNotNull();
		assertEquals(updatedSuperhero.getHeroName(),superheroDto.getHeroName());
		assertEquals(updatedSuperhero.getRealName(),superheroDto.getRealName());
		assertEquals(updatedSuperhero.getSuperpower(),superheroDto.getSuperpower());
	}
	/**
	 * Test of the {@link SuperheroService#deleteSuperhero(Long)} method.
	 */
	@Test
	public void SuperheroService_DeleteSuperhero_ReturnsResponseDto() {
		Long id = 1L;
		SuperheroEntity supeheroEntity = SuperheroEntity.builder()
				.heroName("Spiderman")
				.realName("Peter Parker")
				.superpower("Spider powers").build();
		
		when(superheroRepository.findById(id)).thenReturn(Optional.ofNullable(supeheroEntity));		

		assertAll(() -> superheroService.deleteSuperhero(id));
	}

}
