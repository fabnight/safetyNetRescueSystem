package com.safetynet.safetynetrescuesystem.service;

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

@Service
@SpringBootTest
public class PersonServiceTest {

	@Autowired
	private GlobalData globalData;
	@Autowired
	private PersonService personService;

	@Test
	void testFindListOfChildren() {
		
		HashMap<String, ArrayList<ChildDto>> listOfChildren=null;
				
		listOfChildren=personService.findListOfChildren("1509 Culver St");
		
		assertEquals(listOfChildren.size(),1);
		
	}

	@Test
	void testFindPersonInfoDto() {
		ArrayList<PersonInfoDto>infoPerson=null;	
	infoPerson=personService.findPersonInfoDto("Allison", "Boyd");
	assertEquals(infoPerson.size(),6);
	}
//
//	@Test
//	void testFindEmailByCity() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testPostPerson() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testPutPerson() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testDeletePerson() {
//		fail("Not yet implemented");
//	}

//	@Test

//	void testCalculOfAgeByPerson() {
//		int age = 0;
//
//		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
//		LocalDate birthDate = LocalDate.parse("01/01/2084", dtf);
//		;
//		LocalDate today = LocalDate.now(ZoneId.systemDefault());
//		age = (int) ChronoUnit.YEARS.between(birthDate, today);
//
//		assertThat(age < 0);
//	}

	@Test
	void testCalculOfAgeByPerson() throws JsonParseException, JsonMappingException, IOException {
		int ageOfPerson = 0;

		ageOfPerson = personService.calculOfAgeByPerson("John", "Boyd");

		assertEquals(ageOfPerson, 37);

	}

	@Test
void testFindCategoryOfAgeByPersonforAnAdult() {
		String categoryOfAge=null;
		categoryOfAge = personService.findCategoryOfAgeByPerson("John", "Boyd");

		assertEquals(categoryOfAge, "adult");
	}
	
	@Test
	void testFindCategoryOfAgeByPersonforAChild() {
			String categoryOfAge=null;
			categoryOfAge = personService.findCategoryOfAgeByPerson("Tenley", "Boyd");

			assertEquals(categoryOfAge, "child");
		}
	@Test
	void testFindEmailsForTheCityCulver() {
			List<String> listOfEmails=null;
			listOfEmails = personService.findEmailByCity("Culver");

			assertEquals(listOfEmails.size(),23);
			assertEquals(listOfEmails.get(0),"jaboyd@email.com");
		}
}
