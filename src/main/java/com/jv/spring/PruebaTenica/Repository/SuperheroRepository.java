package com.jv.spring.PruebaTenica.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;	
import com.jv.spring.PruebaTenica.Entity.SuperheroEntity;

@Repository
public interface SuperheroRepository extends JpaRepository<SuperheroEntity, Long> {
	
	//finds all superheroes
	List<SuperheroEntity> findAll();
	//finds a superhero by the provided id 
	SuperheroEntity findById(long id);
	//finds all superheroes whose name contains a specific string 
	List<SuperheroEntity> findByHeroNameContainingIgnoreCase(String heroName);	
	
}
