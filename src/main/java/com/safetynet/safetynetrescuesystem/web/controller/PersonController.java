package com.safetynet.safetynetrescuesystem.web.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.safetynet.safetynetrescuesystem.dto.PersonInfoDto;
import com.safetynet.safetynetrescuesystem.model.Firestation;
import com.safetynet.safetynetrescuesystem.model.MedicalRecord;
import com.safetynet.safetynetrescuesystem.model.Person;

import com.safetynet.safetynetrescuesystem.service.DataFileReader;
import com.safetynet.safetynetrescuesystem.service.GlobalData;

@RestController
public class PersonController {

	@Autowired
	private GlobalData globalData;

	@Autowired
	private DataFileReader dataFileReader;

	@GetMapping(value = "/firestation")
	public ArrayList<String> personsByfirestationsList(@RequestParam(name = "stationNumber") String station)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		return dataFileReader.findInfopersonsByFirestation(station);
	}

	@GetMapping(value = "/childAlert")
	public ArrayList<String> todo(@RequestParam("address") String address)
			throws JsonParseException, JsonMappingException, IOException {
		return todo(null);
	}

	@GetMapping(value = "/phoneAlert")
	public ArrayList<String> getPersonEmaild(@RequestParam("firestation") String station)
			throws JsonParseException, JsonMappingException, IOException {
		return dataFileReader.findPersonsPhoneNumberByFireStationId(station);
	}

	@GetMapping(value = "/fire")
	public ArrayList<String> fire(@RequestParam("address") String address)
			throws JsonParseException, JsonMappingException, IOException {
		return fire(null);
	}

	@GetMapping(value = "flood/stations")
	public ArrayList<String> firestationsList(@RequestParam("stations") String station)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		System.out.println(globalData.getFirestations());
		return dataFileReader.findInfopersonsByFirestation(station);
	}

	@GetMapping(value = "/personInfo")
	public HashMap<String, Object> getAllergies(@RequestParam(name = "firstName") String firstName,
			@RequestParam(name = "lastName") String lastName)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		return dataFileReader.findAllergiesByPerson(lastName);
	}
	
	@GetMapping(value = "/personInfoDto")
	public ArrayList<PersonInfoDto> fgetAllergies(@RequestParam(name = "firstName") String firstName,
			@RequestParam(name = "lastName") String lastName)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		return dataFileReader.findPersonInfoDto(firstName, lastName);
	}

	@GetMapping(value = "/communityEmail")
	public List<String> getPersonEmailByCity(@RequestParam(name = "city") String city)
			throws JsonParseException, JsonMappingException, IOException {

		return dataFileReader.findEmailByCity(city);
	}

/////////////////////////////////
	@GetMapping(value = "/MedicalRecords/{lastNamePerson}")
	public ArrayList<String> MedicalRecordsList(@PathVariable("lastNamePerson") String lastNamePerson)
			throws JsonParseException, JsonMappingException, IOException {

		return dataFileReader.findMedicalRecordsByPerson(lastNamePerson);
	}

	@GetMapping(value = "/email/{lastName}")
	public ArrayList<String> getPersonEmail(@PathVariable("lastName") String lastName)
			throws JsonParseException, JsonMappingException, IOException {
		return dataFileReader.findEmailByLastName(lastName);
	}

	@GetMapping(value = "/Medication/{lastName}/{firstName}")
	public HashMap<String, Object> getMedications(@PathVariable("lastName") String lastName,
			@PathVariable("firstName") String firstName) throws JsonParseException, JsonMappingException, IOException {
		return dataFileReader.findMedicationsByPerson(lastName, firstName);
	}

	@GetMapping(value = "/Age/{lastName}/{firstName}")
	public HashMap<String, Long> getAge(@PathVariable("lastName") String lastName,
			@PathVariable("firstName") String firstName)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		return dataFileReader.findAgeByPerson(lastName, firstName);
	}

	@PostMapping(value = "/firestation")
	public void addFirestation(@RequestBody Firestation firestation) {

	}

}
