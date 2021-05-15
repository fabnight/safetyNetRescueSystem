package com.safetynet.safetynetrescuesystem.dto;

import java.io.Serializable;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

/**
 * 
 * @see PersonService
 *
 */
@Service
public class PersonInfoDto implements Serializable {

	private static final long serialVersionUID = 1L;

	String lastName;
	String firstName;
	String address;
	long age;
	String email;
	ArrayList<String> medications;
	ArrayList<String> allergies;

	public PersonInfoDto() {
	}

	public PersonInfoDto(String lastName, String firstName, String address, long age, String email,
			ArrayList<String> medications, ArrayList<String> allergies) {

		this.lastName = lastName;
		this.firstName = firstName;
		this.address = address;
		this.age = age;
		this.email = email;
		this.medications = medications;
		this.allergies = allergies;
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

	public long getAge() {
		return age;
	}

	public void setAge(long age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ArrayList<String> getMedications() {
		return medications;
	}

	public void setMedications(ArrayList<String> medications) {
		this.medications = medications;
	}

	public ArrayList<String> getAllergies() {
		return allergies;
	}

	public void setAllergies(ArrayList<String> allergies) {
		this.allergies = allergies;
	}

	@Override
	public String toString() {
		return "PersonInfoDto [lastName=" + lastName
				+ ",firstName=\" + firstName + \",address=\" + address + \"age=\" + age + \", email=" + email
				+ ", medications=\" + medications + \", allergies=" + allergies + "]";
	}
}
