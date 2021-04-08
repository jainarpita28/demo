package com.example.demo.element;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElementRepository extends JpaRepository<Element, Integer>{

	//Search by Name Symbol or position
	Optional<Element> findElementByNameOrSymbolOrPosition(String name, String symbol, Integer position);
	
}
