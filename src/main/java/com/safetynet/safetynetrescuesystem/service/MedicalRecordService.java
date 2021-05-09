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

	public ResponseEntity<MedicalRecord> postMedicalRecord(MedicalRecord medicalRecordToPost) {
		List<MedicalRecord> medicalRecords = globalData.getMedicalrecords();
		final int sz = medicalRecords.size();

		for (Integer i = 0; i < sz; i++) {

			if (medicalRecordToPost.getLastName().equals(medicalRecords.get(i).getLastName())
					&& medicalRecordToPost.getFirstName().equals(medicalRecords.get(i).getFirstName())) {
				medicalRecordToPost = medicalRecords.get(i);
				logger.error(
						"This person has already got medical records in database, please use a PUT query if you want to amend its medicalrecords");
				return new ResponseEntity<MedicalRecord>(medicalRecordToPost, HttpStatus.CONFLICT);
			} else if (i == medicalRecords.size() - 1) {
				globalData.getMedicalrecords().add(medicalRecordToPost);
				logger.info("new medical records created");
			}
		}
		return new ResponseEntity<>(medicalRecordToPost, HttpStatus.CREATED);
	}

	public ResponseEntity<MedicalRecord> putMedicalRecord(MedicalRecord medicalRecord) {

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

			logger.info("medical records amended");
			return new ResponseEntity<MedicalRecord>(medicalRecord, HttpStatus.OK);

		}
		logger.info("person to update not found, please check firstname and lastname");
		return new ResponseEntity<MedicalRecord>(medicalRecord, HttpStatus.BAD_REQUEST);
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
			logger.info("Medical records are now deleted");
			return new ResponseEntity<MedicalRecord>(medicalRecord, HttpStatus.OK);

		}
		logger.info("person to delete medical records not found");
		return new ResponseEntity<MedicalRecord>(medicalRecord, HttpStatus.BAD_REQUEST);
	}
}
