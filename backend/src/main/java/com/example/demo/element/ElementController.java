package com.example.demo.element;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "element/")
public class ElementController {

	@Autowired
	private ElementService elementService; 
	
	@GetMapping
	public List<Element> list(){
		return elementService.list();
	}
	
	@PostMapping(path="create/")
	public ResponseEntity<Object> create(@RequestBody Element element) {
		HttpStatus status = HttpStatus.OK;
		try {
			elementService.create(element);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			status = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<Object>(this.list(), status);
	}
	
	@DeleteMapping(path="delete/{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") Integer id) {
		HttpStatus status = HttpStatus.OK;
		try {
			elementService.delete(id);
		} catch (Exception e) {

			System.out.println(e.getMessage());
			status = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<Object>(this.list(), status);
	}
	
	@PutMapping(path="update/{id}")
	public ResponseEntity<Object> update(
			@PathVariable("id") Integer id,
			@RequestParam Float weight
	) {
		HttpStatus status = HttpStatus.OK;
		try {
			elementService.update(id, weight);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			status = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<Object>(this.list(), status);
	}

}
