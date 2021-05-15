package com.safetynet.safetynetrescuesystem.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.safetynet.safetynetrescuesystem.dto.ChildDto;
import com.safetynet.safetynetrescuesystem.dto.PersonInfoDto;
import com.safetynet.safetynetrescuesystem.model.Person;

@Service
@SpringBootTest
public class PersonServiceTest {

	@Autowired
	private GlobalData globalData;
	@Autowired
	private PersonService personService;

	@Test
	void testFindListOfChildren() {

		HashMap<String, ArrayList<ChildDto>> listOfChildren = null;

		listOfChildren = personService.findListOfChildren("1509 Culver St");

		assertEquals(listOfChildren.size(), 1);

	}

	@Test
	void testFindPersonInfoDto() {
		ArrayList<PersonInfoDto> infoPerson = null;
		infoPerson = personService.findPersonInfoDto("Allison", "Boyd");
		assertEquals(infoPerson.size(), 6);
	}

	@Test
	void testFindEmailsForTheCityCulver() {
		List<String> listOfEmails = null;
		listOfEmails = personService.findEmailByCity("Culver");

		assertEquals(listOfEmails.get(0), "jaboyd@email.com");
	}

	@Test
	void testCalculOfAgeByPerson() throws JsonParseException, JsonMappingException, IOException {
		int ageOfPerson = 0;

		ageOfPerson = personService.calculOfAgeByPerson("John", "Boyd");

		assertEquals(ageOfPerson, 37);

	}

	@Test
	void testFindCategoryOfAgeByPersonforAnAdult() {
		String categoryOfAge = null;
		categoryOfAge = personService.findCategoryOfAgeByPerson("John", "Boyd");

		assertEquals(categoryOfAge, "adult");
	}

	@Test
	void testFindCategoryOfAgeByPersonforAChild() {
		String categoryOfAge = null;
		categoryOfAge = personService.findCategoryOfAgeByPerson("Tenley", "Boyd");

		assertEquals(categoryOfAge, "child");
	}

	@Test
	void testPostPerson() throws Exception {
		Person person = new Person();
		person.setLastName("Test");
		person.setFirstName("Test");
		person.setZip("Test");
		person.setCity("Test");
		person.setEmail("test@email.com");
		person.setPhone("9999-874-6512");
		person.setAddress("9999 Test St");

		personService.postPerson(person);

		assertEquals(globalData.getPersons().get(globalData.getPersons().size()-1).getLastName(),person.getLastName());
	}

	@Test
	void testPostAnExistingPersonShouldFail() throws Exception {
		try {
			Person person = new Person();
			person.setLastName("Boyd");
			person.setFirstName("John");

			personService.postPerson(person);

		} catch (Exception e) {

			e.printStackTrace();

			assertEquals(
					"This person was already found in database, please use a PUT query if you want to amend this person",
					e.getMessage());
		}
	}

	@Test
	void testPutPerson() throws Exception {

		Person person = new Person();
		person.setLastName("Boyd");
		person.setFirstName("John");
		person.setZip("99999");
		person.setCity("Culver");
		person.setEmail("jaboyd@email.com");
		person.setPhone("841-874-6512");
		person.setAddress("1509 Culver St");

		personService.putPerson(person);
		String zip = globalData.getPersons().get(0).getZip();
		assertEquals(zip, "99999");
	}

	@Test
	void testPutAnInexistingPersonShouldFail() throws Exception {
		try {
			Person person = new Person();
			person.setLastName("Garnier");
			person.setFirstName("Fabrice");

			personService.putPerson(person);

		} catch (Exception e) {

			e.printStackTrace();

			assertEquals("person to update not found, please check firstname and lastname", e.getMessage());
		}
	}

	@Test
	void testDeleteAnInexistingPersonShouldFail() throws Exception {
		try {
			Person person = new Person();
			person.setLastName("Garnier");
			person.setFirstName("Fabrice");

			personService.deletePerson(person);

		} catch (Exception e) {

			e.printStackTrace();

			assertEquals("person to delete not found?, please check firstname and lastname", e.getMessage());
		}
	}

}