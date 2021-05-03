package com.safetynet.safetynetrescuesystem.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestBody;
import java.util.Set;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.safetynet.safetynetrescuesystem.model.Firestation;

@Component
public class FirestationEndpoint {
	@Autowired
	private GlobalData globalData;


	public ResponseEntity<Firestation> postFirestation(@RequestBody Firestation firestation)
			throws JsonGenerationException, JsonMappingException, IOException {
		globalData.getFirestations().add(firestation);
		System.out.println(globalData.getFirestations());
		return new ResponseEntity<Firestation>(firestation, HttpStatus.CREATED);
	}

	
	public ResponseEntity<Firestation> putFirestation(@RequestBody Firestation firestation)
			throws JsonGenerationException, JsonMappingException, IOException {

		List<Firestation> firestations = globalData.getFirestations();
		Firestation stationToUpdate = null;

		for (Integer i = 0; i < firestations.size() && stationToUpdate == null; i++) {

			if (firestation.getAddress().equals(firestations.get(i).getAddress())) {
				stationToUpdate = firestations.get(i);
			}

		}
		if (stationToUpdate != null) {
			stationToUpdate.setStation(firestation.getStation());
			System.out.println(globalData.getFirestations());
			return new ResponseEntity<Firestation>(firestation, HttpStatus.OK);

		}
		return new ResponseEntity<Firestation>(firestation, HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping(value = "/firestation")
	public ResponseEntity<Firestation> deleteFirestation(@RequestBody Firestation firestation)
			throws JsonGenerationException, JsonMappingException, IOException {

		List<Firestation> firestations = globalData.getFirestations();
		Firestation stationToDelete = null;

		for (Integer i = 0; i < firestations.size() && stationToDelete == null; i++) {

			if (firestation.getStation().equals(firestations.get(i).getStation())) {
				stationToDelete = firestations.get(i);
			}

		}
		if (stationToDelete != null) {
			firestation.remove(stationToDelete);
			System.out.println(globalData.getFirestations());
			return new ResponseEntity<Firestation>(firestation, HttpStatus.OK);

		}
		return new ResponseEntity<Firestation>(firestation, HttpStatus.BAD_REQUEST);
	}
}
