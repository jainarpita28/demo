package com.example.demo.element;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Service
@EnableTransactionManagement
public class ElementService {

	@Autowired
	private ElementRepository elementRepository;
	
	public List<Element> list(){
		return elementRepository.findAll();
	}

	public void create(Element element) {
		if(elementRepository.findElementByNameOrSymbolOrPosition(element.getName(), element.getSymbol(), element.getPosition()).isPresent()) {
			throw new IllegalStateException("elememt exist");
		}
		elementRepository.save(element);
	}

	public void delete(Integer id) {
		elementRepository.deleteById(id);
	}

	public void update(Integer id, Float weight) {
		
		Element element = elementRepository.findById(id).orElseThrow(()-> new IllegalStateException("element does not exist"));
		element.setWeight(weight);
		elementRepository.save(element);
		
	}

	public void batch(List<String> elementsText) throws IOException, ParseException {
		
		List <Element> elements = new ArrayList<>();
		for (String elementText : elementsText) {
		
			String[] columns = elementText.split(",");
		    
			if(columns.length<6) {
				throw new IOException("not the desired element");
		    }
			
			Element element = new Element(columns[0], columns[1], columns[2], columns[3], columns[4], columns[5]);
			
//			if(elementRepository.findElementByNameOrSymbolOrPosition(element.getName(), element.getSymbol(), element.getPosition()).isPresent()) {
//				throw new IllegalStateException("elememt exist");
//			}
			
			elements.add(element);
			
		}
		
		this.storeAll(elements);
		
	}

	@Transactional(rollbackOn = SqlExceptionHelper.class)
	private void storeAll(List<Element> elements) {
		try {

			elementRepository.saveAll(elements);
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
			throw new IllegalStateException("unable to store records");
		}
		
	}
	
}
