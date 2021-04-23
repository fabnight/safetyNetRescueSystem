package com.safetynet.safetynetrescuesystem.web.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetrescuesystem.model.Firestation;
import com.safetynet.safetynetrescuesystem.model.MedicalRecord;
import com.safetynet.safetynetrescuesystem.model.Person;
import com.safetynet.safetynetrescuesystem.service.DataFile;
import com.safetynet.safetynetrescuesystem.service.DataFileReader;
import com.safetynet.safetynetrescuesystem.service.FullInfoPerson;
import com.safetynet.safetynetrescuesystem.service.GlobalData;

@RestController
public class PersonController {

	@Autowired
	private GlobalData globalData;
	@Autowired
	static DataFile dataFile;
	static FullInfoPerson fullInfoPerson;

	

	@GetMapping(value = "/Person/{lastName}")
	public List<Person> PersonList(@PathVariable final String lastName)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		DataFile dataFile = mapper.readValue(new File("src\\main\\resources\\data.json"), DataFile.class);
		for (MedicalRecord medicalrecord : dataFile.medicalrecords) {
			if (medicalrecord.getLastName() == lastName) {

				return dataFile.getPerson(lastName);
			}
		}
		return null;
	}

	@GetMapping(value = "/Firestations/stationNumber/{station}")
	public ArrayList<String> personsByfirestationsList(@PathVariable String station, Person firstName, Person LastName)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		System.out.println(globalData.getFirestations());
		return DataFileReader.findInfopersonsByFirestation(station,firstName, LastName);
	}
	
	@GetMapping(value = "phoneAlert/firestation/{station}")
	public ArrayList<String> getPersonEmaild(@PathVariable("station") String station)
			throws JsonParseException, JsonMappingException, IOException {
		return DataFileReader.findPersonsPhoneNumberByFireStationId(station);
	}

	@GetMapping(value = "/Firestations/{station}")
	public ArrayList<String> firestationsList(@PathVariable String station, MedicalRecord firstName, MedicalRecord LastName)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		System.out.println(globalData.getFirestations());
		return DataFileReader.findInfopersonsByFirestation(station,firstName, LastName);
	}
	

	@GetMapping(value = "/MedicalRecords/{lastNamePerson}")
	public static ArrayList<String> MedicalRecordsList(@PathVariable("lastNamePerson") String lastNamePerson)
			throws JsonParseException, JsonMappingException, IOException {

		return DataFileReader.findMedicalRecordsByPerson(lastNamePerson);
	}

	@GetMapping(value = "/communityEmailcity/{city}")
	public List<String> getPersonEmailByCity(@PathVariable("city") String city)
			throws JsonParseException, JsonMappingException, IOException {
		System.out.println(globalData.getFirestations());
		
		return DataFileReader.findEmailByCity(city);
	}

	@GetMapping(value = "/email/{lastName}")
	public ArrayList<String> getPersonEmail(@PathVariable("lastName") String lastName)
			throws JsonParseException, JsonMappingException, IOException {
		return DataFileReader.findEmailByLastName(lastName);
	}
	
}
