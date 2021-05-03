package com.safetynet.safetynetrescuesystem.service;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestBody;
import java.util.Set;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.safetynet.safetynetrescuesystem.model.MedicalRecord;

@Service
public class MedicalRecordEndpoint {
	@Autowired
	private GlobalData globalData;

	public ResponseEntity<MedicalRecord> postMedicalRecord(@RequestBody MedicalRecord medicalRecord)
			throws JsonGenerationException, JsonMappingException, IOException {
		globalData.getMedicalrecords().add(medicalRecord);
		System.out.println(globalData.getMedicalrecords());
		return new ResponseEntity<MedicalRecord>(medicalRecord, HttpStatus.CREATED);
	}

	public ResponseEntity<MedicalRecord> putMedicalRecord(@RequestBody MedicalRecord medicalRecord)
			throws JsonGenerationException, JsonMappingException, IOException {

		List<MedicalRecord> medicalRecords = globalData.getMedicalrecords();
		MedicalRecord medicalRecordToUpdate = null;

		for (Integer i = 0; i < medicalRecords.size() && medicalRecordToUpdate == null; i++) {

			if (medicalRecord.getLastName().equals(medicalRecords.get(i).getLastName()) && medicalRecord.getFirstName().equals(medicalRecords.get(i).getFirstName())  ) {
				medicalRecordToUpdate = medicalRecords.get(i);
								
			}

		}
		if (medicalRecordToUpdate != null) {
			medicalRecordToUpdate.setBirthdate(medicalRecord.getBirthdate());
			medicalRecordToUpdate.setMedications(medicalRecord.getMedications());
			medicalRecordToUpdate.setAllergies(medicalRecord.getAllergies());
			
			System.out.println(globalData.getMedicalrecords());
			return new ResponseEntity<MedicalRecord>(medicalRecord, HttpStatus.OK);

		}
		return new ResponseEntity<MedicalRecord>(medicalRecord, HttpStatus.BAD_REQUEST);
	}

	
	public ResponseEntity<MedicalRecord> deleteMedicalRecord(@RequestBody MedicalRecord medicalRecord)
			throws JsonGenerationException, JsonMappingException, IOException {


		List<MedicalRecord> medicalRecords = globalData.getMedicalrecords();
		MedicalRecord medicalRecordToDelete = null;

		for (Integer i = 0; i < medicalRecords.size() && medicalRecordToDelete == null; i++) {

			if (medicalRecord.getLastName().equals(medicalRecords.get(i).getLastName()) && medicalRecord.getFirstName().equals(medicalRecords.get(i).getFirstName())  ) {
				medicalRecordToDelete = medicalRecords.get(i);
								
			}

		}
		if (medicalRecordToDelete != null) {
			medicalRecord.remove(medicalRecordToDelete);
			System.out.println(globalData.getMedicalrecords());
			return new ResponseEntity<MedicalRecord>(medicalRecord, HttpStatus.OK);

		}
		return new ResponseEntity<MedicalRecord>(medicalRecord, HttpStatus.BAD_REQUEST);
	}
}
