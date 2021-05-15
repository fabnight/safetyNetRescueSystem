package com.safetynet.safetynetrescuesystem.service;

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

import com.safetynet.safetynetrescuesystem.dto.AddressPersonsDto;
import com.safetynet.safetynetrescuesystem.dto.CountOfPersonsByCategoryDto;
import com.safetynet.safetynetrescuesystem.dto.FirestationDto;
import com.safetynet.safetynetrescuesystem.dto.FirestationPersonsDto;
import com.safetynet.safetynetrescuesystem.model.Firestation;
import com.safetynet.safetynetrescuesystem.model.MedicalRecord;
import com.safetynet.safetynetrescuesystem.model.Person;

/**
 * Is the service for endpoints that deals with firestations info
 * 
 * @see com.safetynet.safetynetrescuesystem.web.controller
 *
 */
@Component
public class FirestationService {
	private static final Logger logger = LogManager.getLogger("FirestationService");
	@Autowired
	private GlobalData globalData;
	@Autowired
	private PersonService personService;

	// http://localhost:8080/firestation?stationNumber=<station_number>

	public HashMap<Object, ArrayList<FirestationPersonsDto>> findInfopersonsByStationNumberDto(String station) {

		HashMap<Object, ArrayList<FirestationPersonsDto>> findInfopersonsByStationNumberDto = new HashMap<>();
		try {
			ArrayList<FirestationPersonsDto> listOfPersonByFirestations = new ArrayList<>();
			Person personDto = new Person();
			int age = 0;
			LocalDate birthDate = null;
			int countOfAdults = 0;
			int countOfChildren = 0;
			String category = new String();
			for (Firestation firestation : globalData.getFirestations()) {
				// find station number in data as in parameters
				if (station.equals(firestation.getStation()))
					// find all persons addresses that match with addresses managed by the station
					// number in parameters
					for (Person person : globalData.getPersons()) {
						// fill the list of persons with personal details
						if (firestation.getAddress().equals(person.getAddress())) {
							personDto.setAddress(person.getAddress());
							personDto.setFirstName(person.getFirstName());
							personDto.setLastName(person.getLastName());
							personDto.setPhone(person.getPhone());
							// calculate age of the person and define if is a child or an adult by matching
							// firstName+LastName
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
			// count number of adults and children
			ModelMapper modelMapper = new ModelMapper();
			CountOfPersonsByCategoryDto countOfPersonsByCategoryDto = new CountOfPersonsByCategoryDto();
			countOfPersonsByCategoryDto = modelMapper.map(personDto, CountOfPersonsByCategoryDto.class);
			countOfPersonsByCategoryDto.setCountOfChildren(countOfChildren);
			countOfPersonsByCategoryDto.setCountOfAdults(countOfAdults);
			findInfopersonsByStationNumberDto.put(countOfPersonsByCategoryDto, listOfPersonByFirestations);

		} catch (Exception ex) {
			logger.error("unable to access to the data file", ex);

		} finally {
			logger.info("findInfopersonsByStationNumber executed with success");
		}
		return findInfopersonsByStationNumberDto;
	}

	// http://localhost:8080/phoneAlert?firestation=<firestation_number>

	public ArrayList<String> findPersonsPhoneNumberByFireStationId(String station) {

		ArrayList<String> listOfPhoneNumbers = new ArrayList<String>();
		try {
			for (Firestation firestation : globalData.getFirestations())
				if (station.equals(firestation.getStation()))
					for (Person person : globalData.getPersons())
						if (person.getAddress().equals(firestation.getAddress()))
							listOfPhoneNumbers.add(person.getPhone());

		} catch (Exception ex) {
			logger.error("unable to access to the data file", ex);

		} finally {
			logger.info("phoneAlert executed with success");
		}
		return listOfPhoneNumbers;
	}

	// http://localhost:8080/fire?address=<address>

	public HashMap<Object, ArrayList<AddressPersonsDto>> findListByStation(String address) {
		int age = 0;
		// match an address in parameters with the station number
		HashMap<Object, ArrayList<AddressPersonsDto>> listByStation = new HashMap<>();
		try {
			FirestationDto stationDto = new FirestationDto();
			ArrayList<AddressPersonsDto> listOfPersonByAddress = new ArrayList<>();
			Person personDto = new Person();
			// match the address in parameters with persons addresses
			for (Firestation firestation : globalData.getFirestations()) {
				if (address.equals(firestation.getAddress()))
					for (Person person : globalData.getPersons()) {
						// add the person details
						if (firestation.getAddress().equals(person.getAddress())) {
							personDto.setAddress(person.getAddress());
							personDto.setFirstName(person.getFirstName());
							personDto.setLastName(person.getLastName());
							personDto.setPhone(person.getPhone());
							stationDto.setStation(firestation.getStation());

							// match firstName and Lastname to find birthdate and calculate age and add
							// medical informations
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
		} catch (Exception ex) {
			logger.error("unable to access to the data file", ex);

		} finally {
			logger.info("fireAddress executed with success");
		}
		return listByStation;
	}

	// http://localhost:8080/flood/stations?stations=<a list of station_numbers>

	public ArrayList<AddressPersonsDto> findInfopersonsByFirestationDto(List<String> stations) {
		int age = 0;

		ArrayList<AddressPersonsDto> listOfPersonByFirestations = new ArrayList<>();
		try {
			Person personDto = new Person();
			// match the station number in parameters with the addresses managed by the
			// stations
			for (Firestation firestation : globalData.getFirestations()) {
				for (String value : stations)
					if (value.equals(firestation.getStation()))
					// match the addresses with the addresses of the persons
					{
						if (value.equals(firestation.getStation()))
							for (Person person : globalData.getPersons()) {
								// add person details
								if (firestation.getAddress().equals(person.getAddress())) {
									personDto.setAddress(person.getAddress());
									personDto.setFirstName(person.getFirstName());
									personDto.setLastName(person.getLastName());
									personDto.setPhone(person.getPhone());
									// match firstName and Lastname to find birthdate and calculate age and add
									// medical informations
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
		} catch (Exception ex) {
			logger.error("unable to access to the data file", ex);

		} finally {
			logger.info("flood stations executed with success");
		}
		return listOfPersonByFirestations;
	}

//POST
	public ResponseEntity<Firestation> postFirestation(Firestation firestationToPost) {
		List<Firestation> firestations = globalData.getFirestations();
		final int sz = firestations.size();
		for (Integer i = 0; i < sz; i++) {
			// check if the address is not already paired with a station number in
			// firestations data
			if (firestationToPost.getAddress().equals(firestations.get(i).getAddress())) {
				firestationToPost = firestations.get(i);
				logger.error(
						"This firestation address was already found in database, please use a PUT query if you want to amend this firestation");
				return new ResponseEntity<Firestation>(firestationToPost, HttpStatus.CONFLICT);
			} else if (i == firestations.size() - 1) {

				globalData.getFirestations().add(firestationToPost);
				logger.info("new firestation created");

			}
		}
		return new ResponseEntity<Firestation>(firestationToPost, HttpStatus.CREATED);
	}

//PUT
	public ResponseEntity<Firestation> putFirestation(Firestation firestation) {
		List<Firestation> firestations = globalData.getFirestations();
		Firestation stationToUpdate = null;

		for (Integer i = 0; i < firestations.size() && stationToUpdate == null; i++) {
			// check that the address is well already paired with a station number in
			// firestations data
			if (firestation.getAddress().equals(firestations.get(i).getAddress())) {
				stationToUpdate = firestations.get(i);
			}
		}

		if (stationToUpdate != null) {
			stationToUpdate.setStation(firestation.getStation());
			logger.info("firestation is amended");
			return new ResponseEntity<Firestation>(firestation, HttpStatus.OK);
		}
		logger.error("This firestation was not found in database, please check the address");
		return new ResponseEntity<Firestation>(firestation, HttpStatus.BAD_REQUEST);
	}

//DELETE
	public ResponseEntity<Firestation> deleteFirestation(Firestation firestation) throws Exception {

		List<Firestation> firestations = globalData.getFirestations();
		Firestation firestationToDelete = null;

		for (Integer i = 0; i < firestations.size() && firestationToDelete == null; i++) {
			// check that the address is well already paired with a station number in
			// firestations data
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
