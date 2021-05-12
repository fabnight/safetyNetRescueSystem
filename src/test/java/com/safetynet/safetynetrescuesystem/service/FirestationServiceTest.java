package com.safetynet.safetynetrescuesystem.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import com.safetynet.safetynetrescuesystem.dto.AddressPersonsDto;
import com.safetynet.safetynetrescuesystem.dto.FirestationPersonsDto;

@Service
@SpringBootTest
public class FirestationServiceTest {
	
	@Autowired
	private GlobalData globalData;
	@Autowired
	private FirestationService firestationService;

	@Test
	void testFindInfopersonsByStationNumberDto() {
		
		HashMap<Object, ArrayList<FirestationPersonsDto>> infoPersonFirestation= firestationService.findInfopersonsByStationNumberDto("3");
		assertEquals(infoPersonFirestation.size(),1);
	}
//
	@Test
	void testFindPersonsPhoneNumberByFireStationId() {
		ArrayList<String> listPhones = firestationService.findPersonsPhoneNumberByFireStationId("2");
		assertEquals(listPhones.get(0),"841-874-6513");
	}
//
	@Test
	void testFindListByStation() {
		HashMap<Object, ArrayList<AddressPersonsDto>>listByStation= firestationService.findListByStation("947 E. Rose Dr");
		assertEquals(listByStation.size(),1);
	}
//
//	@Test
//	void testFindInfopersonsByFirestationDto() {
//		fail("Not yet implemented");
//	}
//
//	
}
