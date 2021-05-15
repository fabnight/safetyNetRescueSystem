package com.safetynet.safetynetrescuesystem.web.controller;

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

	@Autowired
	private PersonService personService;
	@Autowired
	private FirestationService firestationService;
	@Autowired
	private MedicalRecordService medicalRecordService;

	@GetMapping(value = "/firestation")
	public HashMap<Object, ArrayList<FirestationPersonsDto>> personsByfirestationsList(
			@RequestParam(name = "stationNumber") String station) {
		return firestationService.findInfopersonsByStationNumberDto(station);
	}

	@GetMapping(value = "/childAlert")
	public HashMap<String, ArrayList<ChildDto>> getChildrenByAdress(@RequestParam("address") String address) {
		return personService.findListOfChildren(address);
	}

	@GetMapping(value = "/phoneAlert")
	public ArrayList<String> getPersonPhone(@RequestParam("firestation") String station) {
		return firestationService.findPersonsPhoneNumberByFireStationId(station);
	}

	@GetMapping(value = "/fire")
	public HashMap<Object, ArrayList<AddressPersonsDto>> fire(@RequestParam("address") String address) {
		return firestationService.findListByStation(address);
	}

	@GetMapping(value = "flood/stations")
	public ArrayList<AddressPersonsDto> firestationsList(@RequestParam(name = "stations") List<String> stations) {
		return firestationService.findInfopersonsByFirestationDto(stations);
	}

	@GetMapping(value = "/personInfo")
	public ArrayList<PersonInfoDto> getAllergies(@RequestParam(name = "firstName") String firstName,
			@RequestParam(name = "lastName") String lastName) {
		return personService.findPersonInfoDto(firstName, lastName);
	}

	@GetMapping(value = "/communityEmail")
	public List<String> getPersonEmailByCity(@RequestParam(name = "city") String city) {

		return personService.findEmailByCity(city);
	}

	@PostMapping(value = "/person")
	public ResponseEntity<Person> postPerson(@RequestBody Person personToPost) {
		return personService.postPerson(personToPost);
	}

	@PutMapping(value = "/person")
	public ResponseEntity<Person> putPerson(@RequestBody Person person) throws Exception {
		return personService.putPerson(person);
	}

	@DeleteMapping(value = "/person")
	public ResponseEntity<Person> deletePerson(@RequestBody Person person) throws Exception {

		return personService.deletePerson(person);
	}

	@PostMapping(value = "/firestation")
	public ResponseEntity<Firestation> postFirestation(@RequestBody Firestation firestationToPost) {
		return firestationService.postFirestation(firestationToPost);
	}

	@PutMapping(value = "/firestation")
	public ResponseEntity<Firestation> putFirestation(@RequestBody Firestation firestation) throws Exception {
		return firestationService.putFirestation(firestation);
	}

	@DeleteMapping(value = "/firestation")
	public ResponseEntity<Firestation> deleteFirestation(@RequestBody Firestation firestation) throws Exception {
		return firestationService.deleteFirestation(firestation);
	}

	@PostMapping(value = "/medicalRecord")
	public ResponseEntity<MedicalRecord> postFirestation(@RequestBody MedicalRecord medicalRecordToPost) {

		return medicalRecordService.postMedicalRecord(medicalRecordToPost);
	}

	@PutMapping(value = "/medicalRecord")
	public ResponseEntity<MedicalRecord> putMedicalRecords(@RequestBody MedicalRecord medicalRecord) throws Exception {
		return medicalRecordService.putMedicalRecord(medicalRecord);
	}

	@DeleteMapping(value = "/medicalRecord")
	public ResponseEntity<MedicalRecord> deleteMedicalRecords(@RequestBody MedicalRecord medicalRecord) throws Exception{
		return medicalRecordService.deleteMedicalRecord(medicalRecord);
	}
}