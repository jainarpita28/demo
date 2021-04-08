package com.example.demo.element;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Element {
	
	@Id
	@Column(updatable = false, nullable = false)
	private Integer 	id;
	
	@Column(updatable = false, nullable = false, unique = true)
	private Integer		position;
	
	@Column(updatable = false, nullable = false, unique = true)
    private String 		name;
	
	@Column(nullable = false)
    private Float 		weight;
	
	@Column(updatable = false, nullable = false, unique = true)
	private String 		symbol;
        
	private Timestamp	timestamp;
	
    public Element() {}
    
	public Element(Integer id, Integer position, String name, Float weight, String symbol, Timestamp timestamp) {
		this.id = id;
		this.position = position;
		this.name = name;
		this.weight = weight;
		this.symbol = symbol;
		this.timestamp = timestamp;
	}
	
	/*
	 * constructor for parsed Strings form txt file
	 */
	public Element(String id, String name, String position, String symbol, String weight, String timestamp) throws ParseException, IOException {
		
		this.name = name;
		this.symbol = symbol;
		
		try {

			/*
			 * validating and typecasting integer column
			 */
			this.id = Integer.parseInt(id);
			this.position = Integer.parseInt(position);
			
			/*
			 * validating and typecasting float column
			 */
			this.weight = Float.parseFloat(weight);
			
			/*
			 * validating and typecasting date column
			 */
			this.timestamp = Timestamp.valueOf(timestamp);
			
		} catch (NumberFormatException e) {
			
			throw new IOException("not a valid Element");
		}
		
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPosition() {
		return position;
	}
	
	public void setPosition(Integer position) {
		this.position = position;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Float getWeight() {
		return weight;	
	}
	
	public void setWeight(Float weight) {
		this.weight = weight;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public String toString() {
		return "Element [id=" + id + ", position=" + position + ", name=" + name + ", weight=" + weight + ", symbol="
				+ symbol + "]";
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

}
