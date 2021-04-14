package com.safetyNetRescueSystem.service;


import java.util.List;

import com.safetynet.safetynetrescuesystem.model.Firestation;
import com.safetynet.safetynetrescuesystem.model.MedicalRecord;
import com.safetynet.safetynetrescuesystem.model.Person;



public class DataFile {
	
	public DataFile() {
	}
	public List<Person> persons;
	private List<Firestation> firestations;
	private List<MedicalRecord> medicalrecords;

	public DataFile(List<Person> persons, List<Firestation> firestations, List<MedicalRecord> medicalrecords) {
		this.persons = persons;
		this.firestations = firestations;
		this.medicalrecords = medicalrecords;
	}

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public List<Firestation> getFirestation() {
		return firestations;
	}

	public void setFirestations(List<Firestation> firestation) {
		this.firestations = firestation;
	}

	public List<MedicalRecord> getMedicalrecords() {
		return medicalrecords;
	}

	public void setMedicalrecords(List<MedicalRecord> medicalrecords) {
		this.medicalrecords = medicalrecords;
	}

	@Override
	public String toString() {
		return "DataFile{\"persons\": " + "persons=" + persons + ", firestations=" + firestations + ", medicalrecords="
				+ medicalrecords + '}';
	}
}
