package com.safetynet.safetynetrescuesystem.web.controller;

import java.io.File;
import java.io.IOException;
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
import com.safetyNetRescueSystem.service.DataFile;

import com.safetyNetRescueSystem.service.DataFileReader;
import com.safetyNetRescueSystem.service.FullInfoPerson;
import com.safetynet.safetynetrescuesystem.model.Firestation;
import com.safetynet.safetynetrescuesystem.model.MedicalRecord;
import com.safetynet.safetynetrescuesystem.model.Person;

@RestController
public class PersonController {

	@Autowired
	static DataFile dataFile;
	static FullInfoPerson fullInfoPerson;

	@GetMapping(value = "/Persons/{lastName}")
	public static String personsListTest2(@PathVariable String lastName)
			throws JsonParseException, JsonMappingException, IOException {

		return DataFileReader.personsReaderTest2(lastName);
	}
	@GetMapping(value = "/Persontest/{lastName}")
	public static String personsListTest(@PathVariable String lastName)
			throws JsonParseException, JsonMappingException, IOException {

		return DataFileReader.personsReaderTest(lastName);
	}

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

	@GetMapping(value = "/PersonsName/{lastName}")
	public List<Person> PersonByName(@PathVariable String lastName)
			throws JsonParseException, JsonMappingException, IOException {

		return DataFileReader.findPersonByName(lastName);
	}

	@GetMapping(value = "/Firestations/{station}")
	public static List<Firestation> firestationsList(@PathVariable int station)
			throws JsonParseException, JsonMappingException, IOException {

		return DataFileReader.findAddressFireStationById(station);
	}

	@GetMapping(value = "/MedicalRecords/{lastName}")
	public static List<MedicalRecord> MedicalRecordsList(@PathVariable("lastName") String lastName)
			throws JsonParseException, JsonMappingException, IOException {

		return DataFileReader.findMedicalRecordsByPerson("lastName");
	}

	@GetMapping(value = "/emails/{city}")
	public List<String> getPersonEmailByCity(@PathVariable("city") String city)
			throws JsonParseException, JsonMappingException, IOException {
		return DataFileReader.findEmailByCity(city);
	}

	@GetMapping(value = "/email/{lastName}")
	public String getPersonEmail(@PathVariable("lastName") String lastName)
			throws JsonParseException, JsonMappingException, IOException {
		return DataFileReader.findEmailByLastName(lastName);
	}
}
