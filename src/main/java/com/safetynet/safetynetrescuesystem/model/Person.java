package com.safetynet.safetynetrescuesystem.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


public class Person {

	protected String firstName;
	protected String lastName;
	protected String address;
	protected String city;
	protected String zip;
	protected String phone;
	protected String email;
	
	

	// constructeur par défaut
	public Person() {
	}

	// constructeur pour les tests
	public Person(String firstName, String lastName, String address, String city, String zip, String phone,
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
		return "Person{" + "firstName=" + firstName + ", lastname='" + lastName + '\'' + ", address=" + address + '\''
				+ ", city=" + city + '\'' + ", zip=" + zip + '\'' + ", phone=" + phone + ", email=" + email + '}';
	}

	
	
}