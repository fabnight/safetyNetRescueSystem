package com.safetynet.safetynetrescuesystem.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetrescuesystem.model.Firestation;
import com.safetynet.safetynetrescuesystem.model.MedicalRecord;
import com.safetynet.safetynetrescuesystem.model.Person;



/**
 * Singleton to parse the json file into persons,firestations, medicalrecords
 * 
 * @see ressource data.json
 *
 */
@Service
public final class GlobalData {
	private static final Logger logger = LogManager.getLogger("FirestationService");
	private List<Person> persons;
	private List<Firestation> firestations;
	private List<MedicalRecord> medicalrecords;

	public GlobalData() {

		ObjectMapper mapper = new ObjectMapper();
		Data globalData;
		try {
			globalData = mapper.readValue(new File("src\\main\\resources\\data.json"), Data.class);
			this.persons = globalData.persons;
			this.firestations = globalData.firestations;
			this.medicalrecords = globalData.medicalrecords;
			logger.info("ready to use data.json");
		} catch (IOException e) {
			logger.error("unable to use data.json", e);
			e.printStackTrace();
		}

	}

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public List<Firestation> getFirestations() {
		return firestations;
	}

	public void setFirestations(List<Firestation> firestations) {
		this.firestations = firestations;
	}

	public List<MedicalRecord> getMedicalrecords() {
		return medicalrecords;
	}

	public void setMedicalrecords(List<MedicalRecord> medicalrecords) {
		this.medicalrecords = medicalrecords;
	}

	public static class Data {

		public List<Person> persons;
		public List<Firestation> firestations;
		public List<MedicalRecord> medicalrecords;

	}

}
