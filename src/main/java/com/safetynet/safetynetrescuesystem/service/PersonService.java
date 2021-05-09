package com.safetynet.safetynetrescuesystem.service;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.safetynet.safetynetrescuesystem.dto.ChildDto;
import com.safetynet.safetynetrescuesystem.dto.PersonInfoDto;
import com.safetynet.safetynetrescuesystem.model.MedicalRecord;
import com.safetynet.safetynetrescuesystem.model.Person;

@Service
public class PersonService {
	private static final Logger logger = LogManager.getLogger("PersonService");
	@Autowired
	private GlobalData globalData;

	// http://localhost:8080/childAlert?address=<address>

	public HashMap<String, ArrayList<ChildDto>> findListOfChildren(String address)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		int age = 0;
		String category = null;

		HashMap<String, ArrayList<ChildDto>> listOfChildren = new HashMap<>();
		ArrayList<ChildDto> listByAddress = new ArrayList<>();
		Person personDto = new Person();

		for (Person person : globalData.getPersons()) {
			if (address.equals(person.getAddress())) {

				personDto.setFirstName(person.getFirstName());
				personDto.setLastName(person.getLastName());

				for (MedicalRecord medicalRecord : globalData.getMedicalrecords())
					if (personDto.getLastName().equalsIgnoreCase(medicalRecord.getLastName())
							&& (personDto.getFirstName().equalsIgnoreCase(medicalRecord.getFirstName()))) {

						ChildDto childDto = new ChildDto();

						ModelMapper modelMapper = new ModelMapper();
						childDto = modelMapper.map(personDto, ChildDto.class);
						age = calculOfAgeByPerson(medicalRecord.getFirstName(), medicalRecord.getLastName());
						category = findCategoryOfAgeByPerson(medicalRecord.getFirstName(), medicalRecord.getLastName());

						childDto.setAge(age);
						childDto.setCategory(category);

						listByAddress.add(childDto);
						listOfChildren.put(person.getAddress(), listByAddress);
					}
			}

		}
		return listOfChildren;
	}

	// http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>

	public ArrayList<PersonInfoDto> findPersonInfoDto(String firstName, String lastName)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		long age = 0;
		ArrayList<PersonInfoDto> personInfoDtoList = new ArrayList<>();
		Person personDto = new Person();
		personDto.setFirstName(firstName);
		personDto.setLastName(lastName);

		for (MedicalRecord medicalRecord : globalData.getMedicalrecords()) {

			if (personDto.getLastName().equalsIgnoreCase(medicalRecord.getLastName())) {

				PersonInfoDto personInfoDto = new PersonInfoDto();

				ModelMapper modelMapper = new ModelMapper();
				personInfoDto = modelMapper.map(personDto, PersonInfoDto.class);

				personInfoDto.setMedications(medicalRecord.getMedications());
				personInfoDto.setAllergies(medicalRecord.getAllergies());
				personInfoDtoList.add(personInfoDto);
				for (Person person : globalData.getPersons()) {
					if (person.getFirstName().equalsIgnoreCase(medicalRecord.getFirstName())
							&& person.getLastName().equalsIgnoreCase(medicalRecord.getLastName())) {
						age = calculOfAgeByPerson(medicalRecord.getFirstName(), medicalRecord.getLastName());
						personInfoDto.setLastName(person.getLastName());
						personInfoDto.setFirstName(person.getFirstName());
						personInfoDto.setEmail(person.getEmail());
						personInfoDto.setAddress(person.getAddress());
						personInfoDto.setAge(age);
					}
				}
			}
		}
		return personInfoDtoList;
	}

	// http://localhost:8080/communityEmail?city=<city>

	public ArrayList<String> findEmailByCity(String city) {
		ArrayList<String> listOfEmails = new ArrayList<String>();

		for (Person person : globalData.getPersons())
			try {
				if (city.equals(person.getCity()))
					listOfEmails.add(person.getEmail());

			} catch (Exception ex) {
				logger.error("unable to access to the data file", ex);
			} finally {logger.info("executed with success");
			}
		return listOfEmails;
	}

	public ResponseEntity<Person> postPerson(Person personToPost)
			throws JsonGenerationException, JsonMappingException, IOException {
		List<Person> persons = globalData.getPersons();
		final int sz = persons.size();
		for (Integer i = 0; i < sz; i++) {

			if (personToPost.getLastName().equals(persons.get(i).getLastName())
					&& personToPost.getFirstName().equals(persons.get(i).getFirstName())) {
				personToPost = persons.get(i);
				logger.error(
						"This person was already found in database, please use a PUT query if you want to amend this person");
				return new ResponseEntity<Person>(personToPost, HttpStatus.CONFLICT);
			} else if (i == persons.size() - 1) {

				globalData.getPersons().add(personToPost);
				logger.info("new person created");

			}
		}
		return new ResponseEntity<Person>(personToPost, HttpStatus.CREATED);
	}

	public ResponseEntity<Person> putPerson(Person person)
			throws JsonGenerationException, JsonMappingException, IOException {

		List<Person> persons = globalData.getPersons();
		Person personToUpdate = null;

		for (Integer i = 0; i < persons.size() && personToUpdate == null; i++) {

			if (person.getLastName().equals(persons.get(i).getLastName())
					&& person.getFirstName().equals(persons.get(i).getFirstName())) {
				personToUpdate = persons.get(i);
			}

		}
		if (personToUpdate != null) {
			personToUpdate.setAddress(person.getAddress());
			personToUpdate.setCity(person.getCity());
			personToUpdate.setEmail(person.getEmail());
			personToUpdate.setPhone(person.getPhone());
			personToUpdate.setZip(person.getZip());

			logger.info("person amended");
			return new ResponseEntity<Person>(person, HttpStatus.OK);

		}
		logger.info("person to update not found, please check firstname and lastname");
		return new ResponseEntity<Person>(person, HttpStatus.BAD_REQUEST);

	}

	public ResponseEntity<Person> deletePerson(Person person)
			throws JsonGenerationException, JsonMappingException, IOException {

		List<Person> persons = globalData.getPersons();
		Person personToDelete = null;

		for (Integer i = 0; i < persons.size() && personToDelete == null; i++) {

			if (person.getLastName().equals(persons.get(i).getLastName())
					&& person.getFirstName().equals(persons.get(i).getFirstName())) {
				personToDelete = persons.get(i);
			}

		}
		if (personToDelete != null) {
			persons.remove(personToDelete);
			logger.info("person is now deleted");
			return new ResponseEntity<Person>(person, HttpStatus.OK);

		}
		logger.info("person to delete not found");
		return new ResponseEntity<Person>(person, HttpStatus.BAD_REQUEST);
	}

	public int calculOfAgeByPerson(String firstName, String lastName)
			throws JsonParseException, JsonMappingException, IOException, ParseException {

		int age = 0;

		for (MedicalRecord medicalRecord : globalData.getMedicalrecords()) {
			LocalDate birthDate = null;

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			birthDate = LocalDate.parse(medicalRecord.getBirthdate(), dtf);
			LocalDate today = LocalDate.now(ZoneId.systemDefault());
			age = (int) ChronoUnit.YEARS.between(birthDate, today);

//			boolean child = age < 18;
//			String category = new String();
			if (lastName.equals(medicalRecord.getLastName()) && (firstName.equals(medicalRecord.getFirstName()))) {
//
//				if (child == true)
//					category = "child";
//				else
//					category = "adult";

				return age;
			}

		}
		return 0;
	}

	public String findCategoryOfAgeByPerson(String firstName, String lastName)
			throws JsonParseException, JsonMappingException, IOException, ParseException {

		long age = 0;

		LocalDate birthDate = null;
		for (MedicalRecord medicalRecord : globalData.getMedicalrecords()) {

			if ((lastName.equals(medicalRecord.getLastName()) && (firstName.equals(medicalRecord.getFirstName())))) {

				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
				birthDate = LocalDate.parse(medicalRecord.getBirthdate(), dtf);
				LocalDate today = LocalDate.now(ZoneId.systemDefault());
				age = ChronoUnit.YEARS.between(birthDate, today);
				boolean child = age < 18;
				String category = new String();
				if (child == true) {
					category = "child";

				} else {
					category = "adult";

				}

				return category;
			}
		}

		return null;

	}
}
