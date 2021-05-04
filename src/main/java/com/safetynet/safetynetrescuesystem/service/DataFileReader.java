package com.safetynet.safetynetrescuesystem.service;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import com.safetynet.safetynetrescuesystem.dto.AddressPersonsDto;
import com.safetynet.safetynetrescuesystem.dto.ChildDto;
import com.safetynet.safetynetrescuesystem.dto.CountOfPersonsByCategoryDto;
import com.safetynet.safetynetrescuesystem.dto.FirestationPersonsDto;
import com.safetynet.safetynetrescuesystem.dto.PersonInfoDto;
import com.safetynet.safetynetrescuesystem.model.Firestation;
import com.safetynet.safetynetrescuesystem.model.MedicalRecord;
import com.safetynet.safetynetrescuesystem.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Component
public class DataFileReader {

	@Autowired
	private GlobalData globalData;
	private static final Logger logger = LogManager.getLogger("DataFileReader");

	

//	public final int CountChildren(String firstName, String lastName)
//			throws JsonParseException, JsonMappingException, IOException, ParseException {
//
//		long age = 0;
//
//		LocalDate birthDate = null;
//		int countOfAdults = 0;
//		int countOfChildren = 0;
//		for (MedicalRecord medicalRecord : globalData.getMedicalrecords()) {
//
//			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
//			birthDate = LocalDate.parse(medicalRecord.getBirthdate(), dtf);
//			LocalDate today = LocalDate.now(ZoneId.systemDefault());
//			age = ChronoUnit.YEARS.between(birthDate, today);
//			boolean child = age < 18;
//			String category = new String();
//
//			if (child == true) {
//				category = "child";
//				countOfChildren++;
//			} else {
//				category = "adult";
//				countOfAdults++;
//
//			}
//			if (lastName.equals(medicalRecord.getLastName()) && (firstName.equals(medicalRecord.getFirstName()))) {
//
//				System.out.println(medicalRecord.getLastName() + "/" + medicalRecord.getFirstName() + "/" + birthDate
//						+ "/" + age + "/" + category);
//
//				System.out.println(countOfChildren + "/" + countOfAdults);
//				return countOfChildren;
//			}
//		}
//		return 0;
//	}

}
