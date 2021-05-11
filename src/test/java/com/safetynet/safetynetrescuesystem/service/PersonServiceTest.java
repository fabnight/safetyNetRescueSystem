package com.safetynet.safetynetrescuesystem.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetrescuesystem.model.MedicalRecord;
import com.safetynet.safetynetrescuesystem.model.Person;
@Service
@SpringBootTest
public class PersonServiceTest {
	
	

	public static GlobalData globalData;
	
	public static PersonService personService;


//	@Test
//	void testFindListOfChildren() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testFindPersonInfoDto() {
//		fail("Not yet implemented");
//	}
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

	@Test

	void testCalculOfAgeByPerson() {
		int age = 0;

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate birthDate = LocalDate.parse("01/01/2084", dtf);
		;
		LocalDate today = LocalDate.now(ZoneId.systemDefault());
		age = (int) ChronoUnit.YEARS.between(birthDate, today);

		assertThat(age < 0);
	}

	
	
	@Test
	void testCalculOfAgeByPerson2() throws JsonParseException, JsonMappingException, IOException {
		int ageOfPerson = 0;
		
		ObjectMapper mapper = new ObjectMapper();
	globalData= mapper.readValue(new File("src\\main\\resources\\data.json"), GlobalData.class);
		
	for (MedicalRecord medicalRecord : globalData.getMedicalrecords()) {	
		
	ageOfPerson= personService.calculOfAgeByPerson(medicalRecord.getFirstName(),medicalRecord.getLastName());
	}
		assertEquals(ageOfPerson, 37);

	}
	
	
//	@Test
//	void testFindCategoryOfAgeByPerson() {
//		fail("Not yet implemented");
//	}

}
