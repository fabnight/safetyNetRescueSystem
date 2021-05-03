package com.safetynet.safetynetrescuesystem.web.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

import com.safetynet.safetynetrescuesystem.service.DataFileReader;
import com.safetynet.safetynetrescuesystem.service.FirestationEndpoint;
import com.safetynet.safetynetrescuesystem.service.GlobalData;
import com.safetynet.safetynetrescuesystem.service.MedicalRecordEndpoint;
import com.safetynet.safetynetrescuesystem.service.PersonEndpoint;

@RestController
public class PersonController {

	@Autowired
	private GlobalData globalData;

	@Autowired
	private DataFileReader dataFileReader;
	@Autowired
	private PersonEndpoint personEndpoint;
	@Autowired
	private FirestationEndpoint firestationEndpoint;
	@Autowired
	private MedicalRecordEndpoint medicalRecordEndpoint;

	@GetMapping(value = "/firestation")
	public HashMap<Object, ArrayList<FirestationPersonsDto>> personsByfirestationsList(
			@RequestParam(name = "stationNumber") String station)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		return dataFileReader.findInfopersonsByStationNumberDto(station);
	}

	@GetMapping(value = "/childAlert")
	public HashMap<String, ArrayList<ChildDto>> todo(@RequestParam("address") String address)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		return dataFileReader.findListOfChildren(address);
	}

	@GetMapping(value = "/phoneAlert")
	public ArrayList<String> getPersonEmaild(@RequestParam("firestation") String station)
			throws JsonParseException, JsonMappingException, IOException {
		return dataFileReader.findPersonsPhoneNumberByFireStationId(station);
	}
//todo : voir comment préciser la station
	@GetMapping(value = "/fire")
	public HashMap<String, ArrayList<AddressPersonsDto>> fire(@RequestParam("address") String address)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		return dataFileReader.findListByStation(address);
	}

	@GetMapping(value = "flood/stations")
	public ArrayList<AddressPersonsDto> firestationsList(@RequestParam("stations") String station)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		System.out.println(globalData.getFirestations());
		return dataFileReader.findInfopersonsByFirestationDto(station);
	}

	@GetMapping(value = "/personInfo")
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
//	@GetMapping(value = "/MedicalRecords/{lastNamePerson}")
//	public ArrayList<String> MedicalRecordsList(@PathVariable("lastNamePerson") String lastNamePerson)
//			throws JsonParseException, JsonMappingException, IOException {
//
//		return dataFileReader.findMedicalRecordsByPerson(lastNamePerson);
//	}

//	@GetMapping(value = "/email/{lastName}")
//	public ArrayList<String> getPersonEmail(@PathVariable("lastName") String lastName)
//			throws JsonParseException, JsonMappingException, IOException {
//		return dataFileReader.findEmailByLastName(lastName);
//	}
/////////////////////////////////////////

	@PostMapping(value = "/person")
	public ResponseEntity<Person> postPerson(@RequestBody Person person)
			throws JsonGenerationException, JsonMappingException, IOException {
		return personEndpoint.postPerson(person);
	}

	@PutMapping(value = "/person")
	public ResponseEntity<Person> putPerson(@RequestBody Person person)
			throws JsonGenerationException, JsonMappingException, IOException {
		return personEndpoint.putPerson(person);
	}

	@DeleteMapping(value = "/person")
	public ResponseEntity<Person> deletePerson(@RequestBody Person person)
			throws JsonGenerationException, JsonMappingException, IOException {
		return personEndpoint.deletePerson(person);
	}

	@PostMapping(value = "/firestation")
	public ResponseEntity<Firestation> postFirestation(@RequestBody Firestation firestation)
			throws JsonGenerationException, JsonMappingException, IOException {
		return firestationEndpoint.postFirestation(firestation);
	}

	@PutMapping(value = "/firestation")
	public ResponseEntity<Firestation> putFirestation(@RequestBody Firestation firestation)
			throws JsonGenerationException, JsonMappingException, IOException {
		return firestationEndpoint.putFirestation(firestation);
	}

	@DeleteMapping(value = "/firestation")
	public ResponseEntity<Firestation> deleteFirestation(@RequestBody Firestation firestation)
			throws JsonGenerationException, JsonMappingException, IOException {
		return firestationEndpoint.deleteFirestation(firestation);
	}

	@PostMapping(value = "/medicalRecord")
	public ResponseEntity<MedicalRecord> postFirestation(@RequestBody MedicalRecord medicalRecord)
			throws JsonGenerationException, JsonMappingException, IOException {

		return medicalRecordEndpoint.postMedicalRecord(medicalRecord);
	}

	@PutMapping(value = "/medicalRecord")
	public ResponseEntity<MedicalRecord> putMedicalRecords(@RequestBody MedicalRecord medicalRecord)
			throws JsonGenerationException, JsonMappingException, IOException {
		return medicalRecordEndpoint.putMedicalRecord(medicalRecord);
	}

	@DeleteMapping(value = "/medicalRecord")
	public ResponseEntity<MedicalRecord> deleteMedicalRecords(@RequestBody MedicalRecord medicalRecord)
			throws JsonGenerationException, JsonMappingException, IOException {
		return medicalRecordEndpoint.deleteMedicalRecord(medicalRecord);
	}
}