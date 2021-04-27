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
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.safetynet.safetynetrescuesystem.model.Firestation;
import com.safetynet.safetynetrescuesystem.model.MedicalRecord;
import com.safetynet.safetynetrescuesystem.model.Person;

import com.safetynet.safetynetrescuesystem.service.DataFileReader;
import com.safetynet.safetynetrescuesystem.service.GlobalData;

@RestController
public class PersonController {

	@Autowired
	private GlobalData globalData;

	@GetMapping(value = "/Firestations/stationNumber/{station}")
	public ArrayList<String> personsByfirestationsList(@PathVariable String station, Person firstName, Person LastName)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		System.out.println(globalData.getFirestations());
		return DataFileReader.findInfopersonsByFirestation(station, firstName, LastName);
	}

	@GetMapping(value = "phoneAlert/firestation/{station}")
	public ArrayList<String> getPersonEmaild(@PathVariable("station") String station)
			throws JsonParseException, JsonMappingException, IOException {
		return DataFileReader.findPersonsPhoneNumberByFireStationId(station);
	}

	@GetMapping(value = "flood/stations/{station}")
	public ArrayList<String> firestationsList(@PathVariable String station, MedicalRecord firstName,
			MedicalRecord LastName) throws JsonParseException, JsonMappingException, IOException, ParseException {
		System.out.println(globalData.getFirestations());
		return DataFileReader.findInfopersonsByFirestation(station, firstName, LastName);
	}

	@GetMapping(value = "/MedicalRecords/{lastNamePerson}")
	public static ArrayList<String> MedicalRecordsList(@PathVariable("lastNamePerson") String lastNamePerson)
			throws JsonParseException, JsonMappingException, IOException {

		return DataFileReader.findMedicalRecordsByPerson(lastNamePerson);
	}

	@GetMapping(value = "/communityEmailcity/{city}")
	public List<String> getPersonEmailByCity(@PathVariable("city") String city)
			throws JsonParseException, JsonMappingException, IOException {
		// String FirstName= "John";
		// tring LastName="Boyd";
		// Person person = new Person();
		// System.out.println(person.getMedicalRecord().getBirthdate());

		return DataFileReader.findEmailByCity(city);
	}

	@GetMapping(value = "/email/{lastName}")
	public ArrayList<String> getPersonEmail(@PathVariable("lastName") String lastName)
			throws JsonParseException, JsonMappingException, IOException {
		return DataFileReader.findEmailByLastName(lastName);
	}

	@GetMapping(value = "/Medication/{lastName}/{firstName}")
	public HashMap<String, Object> getMedications(@PathVariable("lastName") String lastName,
			@PathVariable("firstName") String firstName) throws JsonParseException, JsonMappingException, IOException {
		return DataFileReader.findMedicationsByPerson(lastName, firstName);
	}

	@GetMapping(value = "/Age/{lastName}/{firstName}")
	public HashMap<String, Long> getAge(@PathVariable("lastName") String lastName,
			@PathVariable("firstName") String firstName)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		return DataFileReader.findAgeByPerson(lastName, firstName);
	}

	@GetMapping(value = "/Allergies/{lastName}/{firstName}")
	public HashMap<String, Object> getAllergies(@PathVariable("lastName") String lastName,
			@PathVariable("firstName") String firstName)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		return DataFileReader.findAllergiesByPerson(lastName);
	}

	@PostMapping(value = "/firestation")
	public void addFirestation(@RequestBody Firestation firestation) {

	}

}
