package com.safetynet.safetynetrescuesystem.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.safetynet.safetynetrescuesystem.model.Firestation;
import com.safetynet.safetynetrescuesystem.model.Person;

@Service
public class PersonEndpoint {

	@Autowired
	private GlobalData globalData;

	
	public ResponseEntity<Person> postPerson(@RequestBody Person person)
			throws JsonGenerationException, JsonMappingException, IOException {
		globalData.getPersons().add(person);System.out.println(globalData.getPersons());
		return new ResponseEntity<Person>(person, HttpStatus.CREATED);
		
	}

	
	public ResponseEntity<Person> putPerson(@RequestBody Person person)
			throws JsonGenerationException, JsonMappingException, IOException {

		List<Person> persons = globalData.getPersons();
		Person personToUpdate = null;

		for (Integer i = 0; i < persons.size() && personToUpdate == null; i++) {

			if (person.getLastName().equals(persons.get(i).getLastName()) && person.getFirstName().equals(persons.get(i).getFirstName())) {
				personToUpdate = persons.get(i);
			}

		}
		if (personToUpdate != null) {
			personToUpdate.setAddress(person.getAddress());
			personToUpdate.setCity(person.getCity());
			personToUpdate.setEmail(person.getEmail());
			personToUpdate.setPhone(person.getPhone());
			personToUpdate.setZip(person.getZip());
			
			System.out.println(globalData.getPersons());
			return new ResponseEntity<Person>(person, HttpStatus.OK);

		}
		return new ResponseEntity<Person>(person, HttpStatus.BAD_REQUEST);
	}

	
	public ResponseEntity<Person> deletePerson(@RequestBody Person person)
			throws JsonGenerationException, JsonMappingException, IOException {

		List<Person> persons = globalData.getPersons();
		Person personToDelete = null;

		for (Integer i = 0; i < persons.size() && personToDelete == null; i++) {

			if (person.getLastName().equals(persons.get(i).getLastName()) && person.getFirstName().equals(persons.get(i).getFirstName())) {
				personToDelete = persons.get(i);
			}

		}
		if (personToDelete != null) {
			person.remove(personToDelete);
			System.out.println(globalData.getPersons());
			return new ResponseEntity<Person>(person, HttpStatus.OK);

		}
		return new ResponseEntity<Person>(person, HttpStatus.BAD_REQUEST);
	}
}
