package com.safetynet.safetynetrescuesystem.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetyNetRescueSystem.service.DataFile;

import com.safetyNetRescueSystem.service.DataFileReader;
import com.safetynet.safetynetrescuesystem.model.Firestation;
import com.safetynet.safetynetrescuesystem.model.MedicalRecord;
import com.safetynet.safetynetrescuesystem.model.Person;

@RestController
public class PersonController {

	
	public List<Person> findAll() {
		return dataFile.persons;
	}

	@Autowired
	static DataFile dataFile;

	@GetMapping(value = "/Persons/{lastName}")
	public List<Person> PersonByName(@PathVariable String lastName)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		dataFile = mapper.readValue(new File("src\\main\\resources\\data.json"), DataFile.class);
		return DataFileReader.findPersonByName(lastName);
	}

	@GetMapping(value = "/Firestations/{station}")
	public static List<Firestation> firestationsList(@PathVariable int station)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		dataFile = mapper.readValue(new File("src\\main\\resources\\data.json"), DataFile.class);
		return DataFileReader.findFireStationStationNumber(station);
	}

	@GetMapping(value = "/MedicalRecords/{lastName}")
	public static List<MedicalRecord> MedicalRecordsList(@PathVariable String lastName)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();

		dataFile = mapper.readValue(new File("src\\main\\resources\\data.json"), DataFile.class);

		return DataFileReader.findMedicalRecordsByPerson(lastName);
	}
}
