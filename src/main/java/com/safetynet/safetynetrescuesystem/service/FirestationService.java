package com.safetynet.safetynetrescuesystem.service;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.safetynet.safetynetrescuesystem.dto.AddressPersonsDto;
import com.safetynet.safetynetrescuesystem.dto.CountOfPersonsByCategoryDto;
import com.safetynet.safetynetrescuesystem.dto.FirestationDto;
import com.safetynet.safetynetrescuesystem.dto.FirestationPersonsDto;
import com.safetynet.safetynetrescuesystem.model.Firestation;
import com.safetynet.safetynetrescuesystem.model.MedicalRecord;
import com.safetynet.safetynetrescuesystem.model.Person;

@Component
public class FirestationService {
	private static final Logger logger = LogManager.getLogger("FirestationService");
	@Autowired
	private GlobalData globalData;
	@Autowired
	private PersonService personService;

	// http://localhost:8080/firestation?stationNumber=<station_number>

	public HashMap<Object, ArrayList<FirestationPersonsDto>> findInfopersonsByStationNumberDto(String station)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		HashMap<Object, ArrayList<FirestationPersonsDto>> findInfopersonsByStationNumberDto = new HashMap<>();
		ArrayList<FirestationPersonsDto> listOfPersonByFirestations = new ArrayList<>();
		Person personDto = new Person();
		int age = 0;
		LocalDate birthDate = null;
		int countOfAdults = 0;
		int countOfChildren = 0;
		String category = new String();
		for (Firestation firestation : globalData.getFirestations()) {
			if (station.equals(firestation.getStation()))
				for (Person person : globalData.getPersons()) {

					if (firestation.getAddress().equals(person.getAddress())) {
						personDto.setAddress(person.getAddress());
						personDto.setFirstName(person.getFirstName());
						personDto.setLastName(person.getLastName());
						personDto.setPhone(person.getPhone());

						for (MedicalRecord medicalRecord : globalData.getMedicalrecords()) {
							DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
							birthDate = LocalDate.parse(medicalRecord.getBirthdate(), dtf);
							LocalDate today = LocalDate.now(ZoneId.systemDefault());
							age = (int) ChronoUnit.YEARS.between(birthDate, today);
							boolean child = age < 18;
							if (personDto.getLastName().equalsIgnoreCase(medicalRecord.getLastName())
									&& (personDto.getFirstName().equalsIgnoreCase(medicalRecord.getFirstName()))) {
								if (child == true) {
									category = "child";
									countOfChildren++;
								} else {
									category = "adult";
									countOfAdults++;

								}

								FirestationPersonsDto firestationPersonsDto = new FirestationPersonsDto();

								ModelMapper modelMapper = new ModelMapper();
								firestationPersonsDto = modelMapper.map(personDto, FirestationPersonsDto.class);

								firestationPersonsDto.setCategory(category);

								listOfPersonByFirestations.add(firestationPersonsDto);

							}

						}
					}
				}
		}
		ModelMapper modelMapper = new ModelMapper();
		CountOfPersonsByCategoryDto countOfPersonsByCategoryDto = new CountOfPersonsByCategoryDto();
		countOfPersonsByCategoryDto = modelMapper.map(personDto, CountOfPersonsByCategoryDto.class);
		countOfPersonsByCategoryDto.setCountOfChildren(countOfChildren);
		countOfPersonsByCategoryDto.setCountOfAdults(countOfAdults);
		findInfopersonsByStationNumberDto.put(countOfPersonsByCategoryDto, listOfPersonByFirestations);

		return findInfopersonsByStationNumberDto;
	}

	// http://localhost:8080/phoneAlert?firestation=<firestation_number>

	public ArrayList<String> findPersonsPhoneNumberByFireStationId(String station)
			throws JsonParseException, JsonMappingException, IOException {
		ArrayList<String> listOfPhoneNumbers = new ArrayList<String>();

		for (Firestation firestation : globalData.getFirestations()) {
			if (station.equals(firestation.getStation()))
				for (Person person : globalData.getPersons()) {
					if (person.getAddress().equals(firestation.getAddress()))
						listOfPhoneNumbers.add(person.getPhone());
				}
		}
		return listOfPhoneNumbers;
	}

	// http://localhost:8080/fire?address=<address>

	public HashMap<Object, ArrayList<AddressPersonsDto>> findListByStation(String address)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		int age = 0;
		HashMap<Object, ArrayList<AddressPersonsDto>> listByStation = new HashMap<>();

		FirestationDto stationDto = new FirestationDto();
		ArrayList<AddressPersonsDto> listOfPersonByAddress = new ArrayList<>();
		Person personDto = new Person();
		for (Firestation firestation : globalData.getFirestations()) {
			if (address.equals(firestation.getAddress()))
				for (Person person : globalData.getPersons()) {

					if (firestation.getAddress().equals(person.getAddress())) {
						personDto.setAddress(person.getAddress());
						personDto.setFirstName(person.getFirstName());
						personDto.setLastName(person.getLastName());
						personDto.setPhone(person.getPhone());
						stationDto.setStation(firestation.getStation());
						for (MedicalRecord medicalRecord : globalData.getMedicalrecords())
							if (personDto.getLastName().equalsIgnoreCase(medicalRecord.getLastName())
									&& (personDto.getFirstName().equalsIgnoreCase(medicalRecord.getFirstName()))) {

								AddressPersonsDto addressPersonsDto = new AddressPersonsDto();

								ModelMapper modelMapper = new ModelMapper();
								addressPersonsDto = modelMapper.map(personDto, AddressPersonsDto.class);

								age = personService.calculOfAgeByPerson(medicalRecord.getFirstName(),
										medicalRecord.getLastName());
								addressPersonsDto.setAge(age);
								addressPersonsDto.setMedications(medicalRecord.getMedications());
								addressPersonsDto.setAllergies(medicalRecord.getAllergies());

								listOfPersonByAddress.add(addressPersonsDto);
								listByStation.put(stationDto, listOfPersonByAddress);
							}
					}
				}
		}

		return listByStation;
	}

	// http://localhost:8080/flood/stations?stations=<a list of station_numbers>

	public ArrayList<AddressPersonsDto> findInfopersonsByFirestationDto(List<String> stations)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		int age = 0;

		ArrayList<AddressPersonsDto> listOfPersonByFirestations = new ArrayList<>();
		Person personDto = new Person();

		for (Firestation firestation : globalData.getFirestations()) {
			for (String value : stations)
				if (value.equals(firestation.getStation()))

				{
					if (value.equals(firestation.getStation()))
						for (Person person : globalData.getPersons()) {

							if (firestation.getAddress().equals(person.getAddress())) {
								personDto.setAddress(person.getAddress());
								personDto.setFirstName(person.getFirstName());
								personDto.setLastName(person.getLastName());
								personDto.setPhone(person.getPhone());

								for (MedicalRecord medicalRecord : globalData.getMedicalrecords())
									if (personDto.getLastName().equalsIgnoreCase(medicalRecord.getLastName())
											&& (personDto.getFirstName()
													.equalsIgnoreCase(medicalRecord.getFirstName()))) {

										AddressPersonsDto addressPersonsDto = new AddressPersonsDto();

										ModelMapper modelMapper = new ModelMapper();
										addressPersonsDto = modelMapper.map(personDto, AddressPersonsDto.class);

										age = personService.calculOfAgeByPerson(medicalRecord.getFirstName(),
												medicalRecord.getLastName());
										addressPersonsDto.setAge(age);
										addressPersonsDto.setMedications(medicalRecord.getMedications());
										addressPersonsDto.setAllergies(medicalRecord.getAllergies());
										listOfPersonByFirestations.add(addressPersonsDto);
									}
							}
						}
				}
		}
		return listOfPersonByFirestations;
	}

	public ResponseEntity<Firestation> postFirestation(Firestation firestationToPost)
			throws JsonGenerationException, JsonMappingException, IOException {
		List<Firestation> firestations = globalData.getFirestations();
		final int sz = firestations.size();
		for (Integer i = 0; i < sz; i++) {

			if (firestationToPost.getAddress().equals(firestations.get(i).getAddress())
					|| firestationToPost.getStation().equals(firestations.get(i).getStation())) {
				firestationToPost = firestations.get(i);
				logger.error(
						"This firestation was already found in database, please use a PUT query if you want to amend this firestation");
				return new ResponseEntity<Firestation>(firestationToPost, HttpStatus.CONFLICT);
			} else if (i == firestations.size() - 1) {

				globalData.getFirestations().add(firestationToPost);
				logger.info("new firestation created");

			}
		}
		return new ResponseEntity<Firestation>(firestationToPost, HttpStatus.CREATED);
	}

	public ResponseEntity<Firestation> putFirestation(Firestation firestation)
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
			logger.info("firestation is amended");
			return new ResponseEntity<Firestation>(firestation, HttpStatus.OK);

		}
		logger.error("This firestation was not found in database, please check the adrress");
		return new ResponseEntity<Firestation>(firestation, HttpStatus.BAD_REQUEST);

	}

	public ResponseEntity<Firestation> deleteFirestation(Firestation firestation)
			throws JsonGenerationException, JsonMappingException, IOException {

		List<Firestation> firestations = globalData.getFirestations();
		Firestation firestationToDelete = null;
		for (Integer i = 0; i < firestations.size() && firestationToDelete == null; i++) {

			if (firestation.getAddress().equals(firestations.get(i).getAddress())
					&& firestation.getStation().equals(firestations.get(i).getStation())) {
				firestationToDelete = firestations.get(i);
			}

		}
		if (firestationToDelete != null) {
			firestations.remove(firestationToDelete);
			logger.info("firestation is deleted");
			return new ResponseEntity<Firestation>(firestationToDelete, HttpStatus.OK);

		}
		logger.error("This firestation was not found in database, please check the station number and the address");
		return new ResponseEntity<Firestation>(firestationToDelete, HttpStatus.BAD_REQUEST);
	}
}
