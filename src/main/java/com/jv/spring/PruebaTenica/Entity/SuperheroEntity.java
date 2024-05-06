package com.jv.spring.PruebaTenica.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//HACER @GETTER Y @SETTER CON LOMBOOK
@Entity
@Table(name = "SUPERHERO")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SuperheroEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)

	private Long id;
	private String heroName;
	private String realName;
	private String superpower;

	@Override
	public String toString() {
		return String.format("SuperheroesEntity[id=%d, heroName='%s', realName='%s', superpower='%s']", id,
				heroName, realName, superpower);
	}

}
