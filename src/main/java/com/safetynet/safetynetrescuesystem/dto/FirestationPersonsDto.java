package com.safetynet.safetynetrescuesystem.dto;

import java.io.Serializable;

import org.springframework.stereotype.Service;

@Service
public class FirestationPersonsDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String firstName;
	String lastName;
	String address;
	String phone;
	String category;

	public FirestationPersonsDto() {
	}

	public FirestationPersonsDto(String firstName, String lastName, String address, String phone, String category) {

		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phone = phone;
		this.category = category;

	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	@Override
	public String toString() {
		return "AddressPersonsDto {firstName=\" + firstName + \" lastName=" + lastName
				+ ",\"address=\" + address + \", phone=\" + phone+\",category=\" + category"+"}";
	}
}
