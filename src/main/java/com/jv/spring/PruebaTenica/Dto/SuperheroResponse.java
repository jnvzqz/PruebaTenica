package com.jv.spring.PruebaTenica.Dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuperheroResponse {
	/**
	 * List of the returned superheroes 
	 */
	private List<SuperheroDto> content;
	
	/**
	 * Number of pages requested
	 */
    private int pageNo;
    
    /**
	 * Size of the pages
	 */
    private int pageSize;
    
    /**
	 * Total amount of elements in the pages
	 */
    private long totalElements;
    
    /**
	 * Total amount of pages
	 */
    private int totalPages;
    
    /**
	 * Marks whether the last element is last or not
	 */
    private boolean last;
}
