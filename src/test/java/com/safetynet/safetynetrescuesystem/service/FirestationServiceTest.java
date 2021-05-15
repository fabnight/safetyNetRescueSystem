package com.safetynet.safetynetrescuesystem.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import com.safetynet.safetynetrescuesystem.dto.AddressPersonsDto;
import com.safetynet.safetynetrescuesystem.dto.FirestationPersonsDto;
import com.safetynet.safetynetrescuesystem.model.Firestation;

@Service
@SpringBootTest
public class FirestationServiceTest {

	@Autowired
	private GlobalData globalData;
	@Autowired
	private FirestationService firestationService;

	@Test
	void testFindListByStation() {
		HashMap<Object, ArrayList<AddressPersonsDto>> listByStation = firestationService
				.findListByStation("947 E. Rose Dr");
		assertEquals(listByStation.size(), 1);
	}

//
	@Test
	void testFindInfopersonsByStationNumberDto() {

		HashMap<Object, ArrayList<FirestationPersonsDto>> infoPersonFirestation = firestationService
				.findInfopersonsByStationNumberDto("3");
		assertEquals(infoPersonFirestation.size(), 1);
	}

//
	@Test
	void testFindPersonsPhoneNumberByFireStationId() {
		ArrayList<String> listPhones = firestationService.findPersonsPhoneNumberByFireStationId("2");
		assertEquals(listPhones.get(0), "841-874-6513");
	}

	@Test
	void findInfopersonsByFirestationDto() {

		List<String> stations = Arrays.asList("2", "3");
		ArrayList<AddressPersonsDto> infoPersonsForAListOfStations = firestationService
				.findInfopersonsByFirestationDto(stations);
		assertEquals(infoPersonsForAListOfStations.size(), 18);

	}

	@Test
	void testPostFirestation() {
		Firestation firestation = new Firestation();
		firestation.setAddress("New addressTest");
		firestation.setStation("9");

		firestationService.postFirestation(firestation);

		assertEquals(globalData.getFirestations().get(globalData.getFirestations().size() - 1).getAddress(),
				firestation.getAddress());

	}

	@Test
	void testPostAnExistingFirestationShouldFail() {
		try {
			Firestation firestation = new Firestation();
			firestation.setAddress("1509 Culver St");
			firestation.setStation("9");

			firestationService.postFirestation(firestation);

		} catch (Exception e) {

			e.printStackTrace();

			assertEquals(
					"This firestation address was already found in database, please use a PUT query if you want to amend this firestation",
					e.getMessage());
		}
	}

	@Test
	void testPutAnInexistingFirestationShouldFail() {
		try {
			Firestation firestation = new Firestation();
			firestation.setAddress("Wrong Address for test");
			firestation.setStation("9");

			firestationService.putFirestation(firestation);

		} catch (Exception e) {

			e.printStackTrace();

			assertEquals("This firestation was not found in database, please check the address", e.getMessage());
		}
	}

	@Test
	void testDeleteAnInexistingFirestationShouldFail() throws Exception {
		try {
			Firestation firestation = new Firestation();
			firestation.setAddress("Wrong Address for test");
			firestation.setStation("9");

			firestationService.deleteFirestation(firestation);

		} catch (Exception e) {

			e.printStackTrace();

			assertEquals("This firestation was not found in database, please check the station number and the address",
					e.getMessage());
		}
	}

}
