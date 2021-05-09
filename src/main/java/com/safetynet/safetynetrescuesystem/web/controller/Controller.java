package com.safetynet.safetynetrescuesystem.web.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.safetynet.safetynetrescuesystem.dto.AddressPersonsDto;
import com.safetynet.safetynetrescuesystem.dto.ChildDto;
import com.safetynet.safetynetrescuesystem.dto.FirestationPersonsDto;
import com.safetynet.safetynetrescuesystem.dto.PersonInfoDto;
import com.safetynet.safetynetrescuesystem.model.Firestation;
import com.safetynet.safetynetrescuesystem.model.MedicalRecord;
import com.safetynet.safetynetrescuesystem.model.Person;
import com.safetynet.safetynetrescuesystem.service.FirestationService;
import com.safetynet.safetynetrescuesystem.service.MedicalRecordService;
import com.safetynet.safetynetrescuesystem.service.PersonService;

@RestController
public class Controller {
	private static final Logger logger = LogManager.getLogger(Controller.class);
	@Autowired
	private PersonService personService;
	@Autowired
	private FirestationService firestationService;
	@Autowired
	private MedicalRecordService medicalRecordService;

	@GetMapping(value = "/firestation")
	public HashMap<Object, ArrayList<FirestationPersonsDto>> personsByfirestationsList(
			@RequestParam(name = "stationNumber") String station)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		return firestationService.findInfopersonsByStationNumberDto(station);
	}

	@GetMapping(value = "/childAlert")
	public HashMap<String, ArrayList<ChildDto>> todo(@RequestParam("address") String address)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		return personService.findListOfChildren(address);
	}

	@GetMapping(value = "/phoneAlert")
	public ArrayList<String> getPersonEmaild(@RequestParam("firestation") String station)
			throws JsonParseException, JsonMappingException, IOException {
		return firestationService.findPersonsPhoneNumberByFireStationId(station);
	}

	@GetMapping(value = "/fire")
	public HashMap<Object, ArrayList<AddressPersonsDto>> fire(@RequestParam("address") String address)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		return firestationService.findListByStation(address);
	}

	@GetMapping(value = "flood/stations")
	public ArrayList<AddressPersonsDto> firestationsList(@RequestParam(name = "stations") List<String> stations)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		return firestationService.findInfopersonsByFirestationDto(stations);
	}

	@GetMapping(value = "/personInfo")
	public ArrayList<PersonInfoDto> fgetAllergies(@RequestParam(name = "firstName") String firstName,
			@RequestParam(name = "lastName") String lastName)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		return personService.findPersonInfoDto(firstName, lastName);
	}

	@GetMapping(value = "/communityEmail")
	public List<String> getPersonEmailByCity(@RequestParam(name = "city") String city) {

		return personService.findEmailByCity(city);
	}

	@PostMapping(value = "/person")
	public ResponseEntity<Person> postPerson(@RequestBody Person personToPost)
			throws JsonGenerationException, JsonMappingException, IOException {
		return personService.postPerson(personToPost);
	}

	@PutMapping(value = "/person")
	public ResponseEntity<Person> putPerson(@RequestBody Person person)
			throws JsonGenerationException, JsonMappingException, IOException {
		return personService.putPerson(person);
	}

	@DeleteMapping(value = "/person")
	public ResponseEntity<Person> deletePerson(@RequestBody Person person)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("person deleted");
		return personService.deletePerson(person);
	}

	@PostMapping(value = "/firestation")
	public ResponseEntity<Firestation> postFirestation(@RequestBody Firestation firestationToPost)
			throws JsonGenerationException, JsonMappingException, IOException {
		return firestationService.postFirestation(firestationToPost);
	}

	@PutMapping(value = "/firestation")
	public ResponseEntity<Firestation> putFirestation(@RequestBody Firestation firestation)
			throws JsonGenerationException, JsonMappingException, IOException {
		return firestationService.putFirestation(firestation);
	}

	@DeleteMapping(value = "/firestation")
	public ResponseEntity<Firestation> deleteFirestation(@RequestBody Firestation firestation)
			throws JsonGenerationException, JsonMappingException, IOException {
		return firestationService.deleteFirestation(firestation);
	}

	@PostMapping(value = "/medicalRecord")
	public ResponseEntity<MedicalRecord> postFirestation(@RequestBody MedicalRecord medicalRecordToPost)
			throws JsonGenerationException, JsonMappingException, IOException {

		return medicalRecordService.postMedicalRecord(medicalRecordToPost);
	}

	@PutMapping(value = "/medicalRecord")
	public ResponseEntity<MedicalRecord> putMedicalRecords(@RequestBody MedicalRecord medicalRecord)
			throws JsonGenerationException, JsonMappingException, IOException {
		return medicalRecordService.putMedicalRecord(medicalRecord);
	}

	@DeleteMapping(value = "/medicalRecord")
	public ResponseEntity<MedicalRecord> deleteMedicalRecords(@RequestBody MedicalRecord medicalRecord)
			throws JsonGenerationException, JsonMappingException, IOException {
		return medicalRecordService.deleteMedicalRecord(medicalRecord);
	}
}