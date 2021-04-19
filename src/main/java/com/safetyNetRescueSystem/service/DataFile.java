package com.safetyNetRescueSystem.service;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetrescuesystem.model.Firestation;
import com.safetynet.safetynetrescuesystem.model.MedicalRecord;
import com.safetynet.safetynetrescuesystem.model.Person;

@Component("dataFile")
public class DataFile implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private static DataFile dataFile = new DataFile();

	private DataFile() {
	}

	public List<Person> persons;
	public List<Firestation> firestations;
	public List<MedicalRecord> medicalrecords;

	public DataFile(List<Person> persons, List<Firestation> firestations, List<MedicalRecord> medicalrecords) {
		this.persons = persons;
		this.firestations = firestations;
		this.medicalrecords = medicalrecords;
	}

	public List<Person> getPersons() {

		return persons;
	}

	public List<Person> getPerson(String lastName) {
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

	public static class PersonAllInfo {
		private String firstName;
		private String lastName;
		private String address;
		private String city;
		private String zip;
		private String phone;
		private String email;

		// constructeur par défaut
		public PersonAllInfo() {
		}

		public PersonAllInfo(String firstName, String lastName, String address, String city, String zip, String phone,
				String email) {

			this.firstName = firstName;
			this.lastName = lastName;
			this.address = address;
			this.city = city;
			this.zip = zip;
			this.phone = phone;
			this.email = email;

		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getZip() {
			return zip;
		}

		public void setZip(String zip) {
			this.zip = zip;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		@Override
		public String toString() {
			return "Person{" + "firstName=" + firstName + ", lastname='" + lastName + '\'' + ", address=" + address
					+ '\'' + ", city=" + city + '\'' + ", zip=" + zip + '\'' + ", phone=" + phone + ", email=" + email
					+ '}';
		}
	}

	public List<MedicalRecord> getMedicalrecords() {
		return medicalrecords;
	}

	public void setMedicalrecords(List<MedicalRecord> medicalrecords) {
		this.medicalrecords = medicalrecords;
	}
	
	public static DataFile getInstance() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		DataFile dataFileAccess = mapper.readValue(new File("src\\main\\resources\\data.json"), DataFile.class);
		return dataFileAccess;
	}
	public Object readResolve() throws JsonParseException, JsonMappingException, IOException
    {
       return DataFile.getInstance( );
    }  

	@Override
	public String toString() {
		return "DataFile{\"persons\": " + "persons=" + persons + ", firestations=" + firestations + ", medicalrecords="
				+ medicalrecords + '}';
	}

}
