package com.safetynet.safetynetrescuesystem.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetrescuesystem.model.Firestation;
import com.safetynet.safetynetrescuesystem.model.MedicalRecord;
import com.safetynet.safetynetrescuesystem.model.Person;

@Service
public final class GlobalData {

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
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
