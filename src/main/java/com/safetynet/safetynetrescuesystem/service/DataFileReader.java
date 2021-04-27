package com.safetynet.safetynetrescuesystem.service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.safetynet.safetynetrescuesystem.model.Firestation;
import com.safetynet.safetynetrescuesystem.model.MedicalRecord;
import com.safetynet.safetynetrescuesystem.model.Person;

@Component
public class DataFileReader {

	@Autowired
	private GlobalData globalData;
	
	public ArrayList<String> findInfopersonsByFirestation(@RequestParam String station, Person FirstName,
			Person LastName) throws JsonParseException, JsonMappingException, IOException, ParseException {

		ArrayList<String> listOfPersonByFirestations = new ArrayList<String>();
		for (Firestation firestation : globalData.getFirestations()) {
			if (station.equals(firestation.getStation()))
				for (Person person : globalData.getPersons()) {

					if (firestation.getAddress().equals(person.getAddress())) {
						listOfPersonByFirestations.add(person.getAddress());
						listOfPersonByFirestations.add(person.getFirstName());
						listOfPersonByFirestations.add(person.getLastName());

						for (MedicalRecord medicalrecord : globalData.getMedicalrecords())
							if (person.getLastName().equals(medicalrecord.getLastName())
									&& (person.getFirstName().equals(medicalrecord.getFirstName())))
								listOfPersonByFirestations.addAll(medicalrecord.getMedications());

						for (MedicalRecord medicalrecord : globalData.getMedicalrecords())
							if (person.getLastName().equals(medicalrecord.getLastName())
									&& (person.getFirstName().equals(medicalrecord.getFirstName())))
								listOfPersonByFirestations.addAll(medicalrecord.getAllergies());
						listOfPersonByFirestations.add(person.getPhone());

						calculOfageOfPerson();

					}
				}
		}

		return listOfPersonByFirestations;
	}

	public  ArrayList<String> findAddressFireStationById(@RequestParam String station)
			throws JsonParseException, JsonMappingException, IOException {
		ArrayList<String> listOfFirestations = new ArrayList<String>();
	
		for (Firestation firestation : globalData.getFirestations()) {
			if (station.equals(firestation.getStation()))
				listOfFirestations.add(firestation.getAddress());

		}
		return listOfFirestations;
	}

	public ArrayList<String> findPersonsPhoneNumberByFireStationId(@RequestParam String station)
			throws JsonParseException, JsonMappingException, IOException {
		ArrayList<String> listOfPhoneNumbers = new ArrayList<String>();
		
		for (Firestation firestation : globalData.getFirestations()) {
			if (station.equals(firestation.getStation()))
				for (Person person : globalData.getPersons()) {
					if (person.getAddress().equals(firestation.getAddress()))
						listOfPhoneNumbers.add(person.getPhone());
				}
		}
		return listOfPhoneNumbers;
	}

	public ArrayList<String> findMedicalRecordsByPerson(@PathVariable String lastNamePerson)
			throws JsonParseException, JsonMappingException, IOException {
		
		ArrayList<String> ListOfMedicalRecordsbyPerson = new ArrayList<String>();
		for (MedicalRecord medicalrecord : globalData.getMedicalrecords()) {
			if (lastNamePerson.equals(medicalrecord.getLastName())) {
				ListOfMedicalRecordsbyPerson.add(medicalrecord.getFirstName());
				ListOfMedicalRecordsbyPerson.add(medicalrecord.getLastName());
				ListOfMedicalRecordsbyPerson.add(medicalrecord.getBirthdate());

				return ListOfMedicalRecordsbyPerson;
			}
		}
		return null;
	}

	public ArrayList<String> findAddressOfpersonsByFirestation(@RequestParam String address)
			throws JsonParseException, JsonMappingException, IOException, ParseException {

		

		ArrayList<String> AddressListOfPersonsByFirestations = new ArrayList<String>();
		for (Firestation firestation : globalData.getFirestations()) {
			if (address.equals(firestation.getAddress()))
				for (Person person : globalData.getPersons()) {

					if (firestation.getAddress().equals(person.getAddress())) {
						AddressListOfPersonsByFirestations.add(firestation.getStation());
						AddressListOfPersonsByFirestations.add(person.getFirstName());
						AddressListOfPersonsByFirestations.add(person.getLastName());
						AddressListOfPersonsByFirestations.add(person.getAddress());
						AddressListOfPersonsByFirestations.add(person.getPhone());

					}
				}
		}
		return AddressListOfPersonsByFirestations;
	}

	public ArrayList<String> findEmailByLastName(@RequestParam String Name)
			throws JsonParseException, JsonMappingException, IOException {

		ArrayList<String> listEmails = new ArrayList<String>();
		
		for (Person person : globalData.getPersons()) {
			if (Name.equals(person.getLastName()))
				listEmails.add(person.getEmail());
		}
		return listEmails;

	}

	public ArrayList<String> findEmailByCity(@RequestParam String city)
			throws JsonParseException, JsonMappingException, IOException {
		ArrayList<String> listOfEmails = new ArrayList<String>();
		
		for (Person person : globalData.getPersons()) {

			if (city.equals(person.getCity()))
				listOfEmails.add(person.getEmail());

			// System.out.println(person.getMedicalRecord().getBirthdate());
		}
		return listOfEmails;
	}

	public HashMap<String, Object> findMedicationsByPerson(@RequestParam String lastName,
			@RequestParam String firstName) throws JsonParseException, JsonMappingException, IOException {
		HashMap<String, Object> listMedications = new HashMap<String, Object>();
		
		for (MedicalRecord medicalrecords : globalData.getMedicalrecords()) {
			if (lastName.equals(medicalrecords.getLastName()) && (firstName.equals(medicalrecords.getFirstName())))
				listMedications.put(medicalrecords.getLastName(), medicalrecords.getMedications());

		}
		return listMedications;

	}

	public HashMap<String, Object> findAllergiesByPerson(@RequestParam String lastName)
			throws JsonParseException, JsonMappingException, IOException, ParseException {

		HashMap<String, Object> listAllergies = new HashMap<String, Object>();

		

		for (MedicalRecord medicalrecords : globalData.getMedicalrecords()) {
			// DataFileReader.calculOfageOfPerson();
			if (medicalrecords.getFullName().contains(lastName)) // &&
																	// (firstName.equals(medicalrecords.getFirstName())))
				for (Person person : globalData.getPersons()) {
					if (medicalrecords.getFullName().contains(lastName))
						listAllergies.put(person.getAddress(), person.getEmail());
				}
			listAllergies.put(medicalrecords.getFullName(), medicalrecords.getMedications());
			listAllergies.put(medicalrecords.getFullName(), medicalrecords.getAllergies());

		}
		return listAllergies;
	}

	public HashMap<String, Long> findAgeByPerson(@RequestParam String lastName,
			@RequestParam String firstName)
			throws JsonParseException, JsonMappingException, IOException, ParseException {

		HashMap<String, Long> ageOfAPerson = new HashMap<String, Long>();

		

		for (MedicalRecord medicalrecords : globalData.getMedicalrecords()) {
			// DataFileReader.calculOfageOfPerson();
			if ((lastName.equals(medicalrecords.getLastName())) && (firstName.equals(medicalrecords.getFirstName())))
				ageOfAPerson.put(medicalrecords.getFullName(), calculOfageByPerson(lastName, firstName));

		}
		return ageOfAPerson;
	}

	public Long calculOfageByPerson(@RequestParam String lastName, @RequestParam String firstName)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		
		long age=0;
		for (MedicalRecord medicalRecord : globalData.getMedicalrecords()) {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			LocalDate birthDate = LocalDate.parse(medicalRecord.getBirthdate(), dtf);
			LocalDate today = LocalDate.now(ZoneId.systemDefault());
			age = ChronoUnit.YEARS.between(birthDate, today);
			boolean child = age < 18;
			String categorie = new String();
			if (child == true)
				categorie = "child";
			else
				categorie = "adult";
			if (lastName.equals(medicalRecord.getLastName()) && (firstName.equals(medicalRecord.getFirstName())))

				System.out.println(medicalRecord.getLastName() + "/" + medicalRecord.getFirstName() + "/" + birthDate
						+ "/" + age + "/" + categorie);
		}

		return age;
	}

	public void calculOfageOfPerson()
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		
		for (MedicalRecord medicalRecord : globalData.getMedicalrecords()) {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			LocalDate birthDate = LocalDate.parse(medicalRecord.getBirthdate(), dtf);
			LocalDate today = LocalDate.now(ZoneId.systemDefault());
			long age = ChronoUnit.YEARS.between(birthDate, today);
			// if (firstName.equals(medicalRecord.getFirstName()) &&
			// (lastName.equals(medicalRecord.getLastName())))
			boolean child = age < 18;
			System.out.println(birthDate + " " + age + " " + child);

		}
		return;
	}
}
