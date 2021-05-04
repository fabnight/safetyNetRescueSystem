package com.safetynet.safetynetrescuesystem.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.safetynet.safetynetrescuesystem.model.MedicalRecord;

@Service
public class MedicalRecordService {
	private static final Logger logger = LogManager.getLogger("MedicalRecordService");
	@Autowired
	private GlobalData globalData;

	public MedicalRecord postMedicalRecord(MedicalRecord medicalRecord) {
		globalData.getMedicalrecords().add(medicalRecord);
		System.out.println(globalData.getMedicalrecords());
		return medicalRecord;
	}

	public MedicalRecord putMedicalRecord(MedicalRecord medicalRecord) {

		List<MedicalRecord> medicalRecords = globalData.getMedicalrecords();
		MedicalRecord medicalRecordToUpdate = null;

		for (Integer i = 0; i < medicalRecords.size() && medicalRecordToUpdate == null; i++) {

			if (medicalRecord.getLastName().equals(medicalRecords.get(i).getLastName())
					&& medicalRecord.getFirstName().equals(medicalRecords.get(i).getFirstName())) {
				medicalRecordToUpdate = medicalRecords.get(i);

			}

		}
		if (medicalRecordToUpdate != null) {
			medicalRecordToUpdate.setBirthdate(medicalRecord.getBirthdate());
			medicalRecordToUpdate.setMedications(medicalRecord.getMedications());
			medicalRecordToUpdate.setAllergies(medicalRecord.getAllergies());

			System.out.println(globalData.getMedicalrecords());

		}
		return medicalRecord;
	}

	public ResponseEntity<MedicalRecord> deleteMedicalRecord(MedicalRecord medicalRecord) {

		List<MedicalRecord> medicalRecords = globalData.getMedicalrecords();
		MedicalRecord medicalRecordToDelete = null;

		for (Integer i = 0; i < medicalRecords.size() && medicalRecordToDelete == null; i++) {

			if (medicalRecord.getLastName().equals(medicalRecords.get(i).getLastName())
					&& medicalRecord.getFirstName().equals(medicalRecords.get(i).getFirstName())) {
				medicalRecordToDelete = medicalRecords.get(i);

			}

		}
		if (medicalRecordToDelete != null) {
			medicalRecords.remove(medicalRecordToDelete);
			System.out.println(globalData.getMedicalrecords());
			return new ResponseEntity<MedicalRecord>(medicalRecord, HttpStatus.OK);

		}
		return new ResponseEntity<MedicalRecord>(medicalRecord, HttpStatus.BAD_REQUEST);
	}
}
