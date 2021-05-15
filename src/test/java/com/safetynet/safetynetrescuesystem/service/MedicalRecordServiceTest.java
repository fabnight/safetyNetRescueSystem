package com.safetynet.safetynetrescuesystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import com.safetynet.safetynetrescuesystem.model.MedicalRecord;

@Service
@SpringBootTest
class MedicalRecordServiceTest {

	@Autowired
	private GlobalData globalData;
	@Autowired
	private MedicalRecordService medicalRecordService;

	@Test
	void testPostMedicalRecord() throws Exception {

		MedicalRecord medicalRecord = new MedicalRecord();
		medicalRecord.setLastName("Test");
		medicalRecord.setFirstName("Test");
		medicalRecord.setBirthdate("03/06/1984");
		ArrayList<String> medical = new ArrayList<String>();
		medical.add("aznol:350mg");
		medical.add("hydrapermazol:100mg");
		medicalRecord.setMedications(medical);

		ArrayList<String> allergies = new ArrayList<String>();
		allergies.add("paracetamol");
		allergies.add("aspirin");
		medicalRecord.setAllergies(allergies);

		medicalRecordService.postMedicalRecord(medicalRecord);
		
		assertEquals(globalData.getMedicalrecords().get(globalData.getMedicalrecords().size() - 1).getLastName(),
				medicalRecord.getLastName());
	}

	@Test
	void testPostAnExistingMedicalRecordShouldFail() throws Exception {
		try {
			MedicalRecord medicalRecord = new MedicalRecord();
			medicalRecord.setLastName("Boyd");
			medicalRecord.setFirstName("John");

			medicalRecordService.postMedicalRecord(medicalRecord);

		} catch (Exception e) {

			e.printStackTrace();

			assertEquals(
					"This person has already got medical records in database, please use a PUT query if you want to amend its medicalrecords",
					e.getMessage());
		}
	}

	@Test
	void testPutMedicalRecord() throws Exception {

		MedicalRecord medicalRecord = new MedicalRecord();
		medicalRecord.setLastName("Boyd");
		medicalRecord.setFirstName("John");
		medicalRecord.setBirthdate("03/06/1984");
		ArrayList<String> medical = new ArrayList<String>();
		medical.add("aznol:350mg");
		medical.add("hydrapermazol:100mg");
		medicalRecord.setMedications(medical);

		ArrayList<String> allergies = new ArrayList<String>();
		allergies.add("paracetamol");
		allergies.add("aspirin");
		medicalRecord.setAllergies(allergies);

		medicalRecordService.putMedicalRecord(medicalRecord);
		ArrayList<String> listAllergies = globalData.getMedicalrecords().get(0).getAllergies();
		System.out.println(globalData.getMedicalrecords());
		assertEquals(listAllergies, allergies);

	}

	@Test
	void testPutAMedicalRecordForAnInexistingPersonShouldFail() {
		try {
			MedicalRecord medicalRecord = new MedicalRecord();
			medicalRecord.setLastName("Garnier");
			medicalRecord.setFirstName("Fabrice");

			medicalRecordService.putMedicalRecord(medicalRecord);

		} catch (Exception e) {

			e.printStackTrace();

			assertEquals("person to update not found, please check firstname and lastname", e.getMessage());
		}
	}

	@Test
	void testDeleteAnInexistingMedicalRecordShouldFail() throws Exception {
		try {
			MedicalRecord medicalRecord = new MedicalRecord();
			medicalRecord.setLastName("Garnier");
			medicalRecord.setFirstName("Fabrice");

			medicalRecordService.deleteMedicalRecord(medicalRecord);

		} catch (Exception e) {

			e.printStackTrace();

			assertEquals("person to delete medical records not found", e.getMessage());
		}
	}
}