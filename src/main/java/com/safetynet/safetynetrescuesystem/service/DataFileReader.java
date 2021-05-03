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

@Component
public class DataFileReader {

	@Autowired
	private GlobalData globalData;

	public HashMap<Object, ArrayList<FirestationPersonsDto>> findInfopersonsByStationNumberDto(
			@RequestParam String station) throws JsonParseException, JsonMappingException, IOException, ParseException {
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

								System.out.println(countOfChildren + "/" + countOfAdults);

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

	public HashMap<String, ArrayList<ChildDto>> findListOfChildren(@RequestParam String address)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		int age = 0;
		String category = null;

		HashMap<String, ArrayList<ChildDto>> listOfChildren = new HashMap<>();
		ArrayList<ChildDto> listByAddress = new ArrayList<>();
		Person personDto = new Person();

		for (Person person : globalData.getPersons()) {
			if (address.equals(person.getAddress())) {

				personDto.setFirstName(person.getFirstName());
				personDto.setLastName(person.getLastName());

				for (MedicalRecord medicalRecord : globalData.getMedicalrecords())
					if (personDto.getLastName().equalsIgnoreCase(medicalRecord.getLastName())
							&& (personDto.getFirstName().equalsIgnoreCase(medicalRecord.getFirstName()))) {

						ChildDto childDto = new ChildDto();

						ModelMapper modelMapper = new ModelMapper();
						childDto = modelMapper.map(personDto, ChildDto.class);
						age = calculOfAgeByPerson(medicalRecord.getFirstName(), medicalRecord.getLastName());
						category = findCategoryOfAgeByPerson(medicalRecord.getFirstName(), medicalRecord.getLastName());

						childDto.setAge(age);
						childDto.setCategory(category);

						listByAddress.add(childDto);
						listOfChildren.put(person.getAddress(), listByAddress);
					}
			}

		}
		return listOfChildren;
	}

	public ArrayList<String> findAddressFireStationById(@RequestParam String station)
			throws JsonParseException, JsonMappingException, IOException {
		ArrayList<String> listOfFirestations = new ArrayList<String>();

		for (Firestation firestation : globalData.getFirestations()) {
			if (station.equals(firestation.getStation()))
				listOfFirestations.add(firestation.getAddress());

		}
		return listOfFirestations;
	}

	public HashMap<String, ArrayList<AddressPersonsDto>> findListByStation(@RequestParam String address)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		int age = 0;
		HashMap<String, ArrayList<AddressPersonsDto>> listByStation = new HashMap<>();
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

						for (MedicalRecord medicalRecord : globalData.getMedicalrecords())
							if (personDto.getLastName().equalsIgnoreCase(medicalRecord.getLastName())
									&& (personDto.getFirstName().equalsIgnoreCase(medicalRecord.getFirstName()))) {

								AddressPersonsDto addressPersonsDto = new AddressPersonsDto();

								ModelMapper modelMapper = new ModelMapper();
								addressPersonsDto = modelMapper.map(personDto, AddressPersonsDto.class);

								age = calculOfAgeByPerson(medicalRecord.getFirstName(), medicalRecord.getLastName());
								addressPersonsDto.setAge(age);
								addressPersonsDto.setMedications(medicalRecord.getMedications());
								addressPersonsDto.setAllergies(medicalRecord.getAllergies());

								listOfPersonByAddress.add(addressPersonsDto);
								listByStation.put("station" + '"' + ":" + '"' + firestation.getStation(),
										listOfPersonByAddress);
							}
					}
				}
		}

		return listByStation;
	}

	public ArrayList<AddressPersonsDto> findInfopersonsByFirestationDto(@RequestParam String station)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		int age = 0;
		ArrayList<AddressPersonsDto> listOfPersonByFirestations = new ArrayList<>();
		Person personDto = new Person();

		for (Firestation firestation : globalData.getFirestations()) {
			if (station.equals(firestation.getStation()))
				for (Person person : globalData.getPersons()) {

					if (firestation.getAddress().equals(person.getAddress())) {
						personDto.setAddress(person.getAddress());
						personDto.setFirstName(person.getFirstName());
						personDto.setLastName(person.getLastName());
						personDto.setPhone(person.getPhone());

						for (MedicalRecord medicalRecord : globalData.getMedicalrecords())
							if (personDto.getLastName().equalsIgnoreCase(medicalRecord.getLastName())
									&& (personDto.getFirstName().equalsIgnoreCase(medicalRecord.getFirstName()))) {

								AddressPersonsDto addressPersonsDto = new AddressPersonsDto();

								ModelMapper modelMapper = new ModelMapper();
								addressPersonsDto = modelMapper.map(personDto, AddressPersonsDto.class);

								age = calculOfAgeByPerson(medicalRecord.getFirstName(), medicalRecord.getLastName());
								addressPersonsDto.setAge(age);
								addressPersonsDto.setMedications(medicalRecord.getMedications());
								addressPersonsDto.setAllergies(medicalRecord.getAllergies());
								listOfPersonByFirestations.add(addressPersonsDto);
							}
					}
				}
		}

		return listOfPersonByFirestations;
	}

	public ArrayList<String> findPersonsPhoneNumberByFireStationId(@RequestParam String station)
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

//	public ArrayList<String> findMedicalRecordsByPerson(@PathVariable String lastNamePerson)
//			throws JsonParseException, JsonMappingException, IOException {
//
//		ArrayList<String> ListOfMedicalRecordsbyPerson = new ArrayList<String>();
//		for (MedicalRecord medicalrecord : globalData.getMedicalrecords()) {
//			if (lastNamePerson.equals(medicalrecord.getLastName())) {
//				ListOfMedicalRecordsbyPerson.add(medicalrecord.getFirstName());
//				ListOfMedicalRecordsbyPerson.add(medicalrecord.getLastName());
//				ListOfMedicalRecordsbyPerson.add(medicalrecord.getBirthdate());
//
//				return ListOfMedicalRecordsbyPerson;
//			}
//		}
//		return null;
//	}

	public ArrayList<String> findAddressOfpersonsByFirestation(@RequestParam String address)
			throws JsonParseException, JsonMappingException, IOException, ParseException {

		ArrayList<String> AddressListOfPersonsByFirestations = new ArrayList<String>();
		for (Firestation firestation : globalData.getFirestations()) {
			if (address.equals(firestation.getAddress()))
				for (Person person : globalData.getPersons()) {

					if (firestation.getAddress().equals(person.getAddress())) {
						AddressListOfPersonsByFirestations.add(firestation.getStation());
						AddressListOfPersonsByFirestations.add(person.getFirstName());
						AddressListOfPersonsByFirestations.add(person.getLastName());
						AddressListOfPersonsByFirestations.add(person.getAddress());
						AddressListOfPersonsByFirestations.add(person.getPhone());

					}
				}
		}
		return AddressListOfPersonsByFirestations;
	}

//	public ArrayList<String> findEmailByLastName(@RequestParam String Name)
//			throws JsonParseException, JsonMappingException, IOException {
//
//		ArrayList<String> listEmails = new ArrayList<String>();
//
//		for (Person person : globalData.getPersons()) {
//			if (Name.equals(person.getLastName()))
//				listEmails.add(person.getEmail());
//		}
//		return listEmails;

//	}

	public ArrayList<String> findEmailByCity(@RequestParam String city)
			throws JsonParseException, JsonMappingException, IOException {
		ArrayList<String> listOfEmails = new ArrayList<String>();

		for (Person person : globalData.getPersons()) {

			if (city.equals(person.getCity()))
				listOfEmails.add(person.getEmail());

			// System.out.println(person.getMedicalRecord().getBirthdate());
		}
		return listOfEmails;
	}

	public ArrayList<PersonInfoDto> findPersonInfoDto(String firstName, String lastName)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		long age = 0;
		ArrayList<PersonInfoDto> personInfoDtoList = new ArrayList<>();
		Person personDto = new Person();
		personDto.setFirstName(firstName);
		personDto.setLastName(lastName);

		for (MedicalRecord medicalRecord : globalData.getMedicalrecords()) {

			if (personDto.getLastName().equalsIgnoreCase(medicalRecord.getLastName())) {

				PersonInfoDto personInfoDto = new PersonInfoDto();

				ModelMapper modelMapper = new ModelMapper();
				personInfoDto = modelMapper.map(personDto, PersonInfoDto.class);

				personInfoDto.setMedications(medicalRecord.getMedications());
				personInfoDto.setAllergies(medicalRecord.getAllergies());
				personInfoDtoList.add(personInfoDto);
				for (Person person : globalData.getPersons()) {
					if (person.getFirstName().equalsIgnoreCase(medicalRecord.getFirstName())
							&& person.getLastName().equalsIgnoreCase(medicalRecord.getLastName())) {
						age = calculOfAgeByPerson(medicalRecord.getFirstName(), medicalRecord.getLastName());
						personInfoDto.setLastName(person.getLastName());
						personInfoDto.setFirstName(person.getFirstName());
						personInfoDto.setEmail(person.getEmail());
						personInfoDto.setAddress(person.getAddress());
						personInfoDto.setAge(age);
					}
				}
			}
		}
		return personInfoDtoList;
	}

	public int calculOfAgeByPerson(String firstName, String lastName)
			throws JsonParseException, JsonMappingException, IOException, ParseException {

		int age = 0;

		for (MedicalRecord medicalRecord : globalData.getMedicalrecords()) {
			LocalDate birthDate = null;
			String category = new String();

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			birthDate = LocalDate.parse(medicalRecord.getBirthdate(), dtf);
			LocalDate today = LocalDate.now(ZoneId.systemDefault());
			age = (int) ChronoUnit.YEARS.between(birthDate, today);
			boolean child = age < 18;

			if (lastName.equals(medicalRecord.getLastName()) && (firstName.equals(medicalRecord.getFirstName()))) {

				if (child == true)
					category = "child";
				else
					category = "adult";

				return age;
			}
//			System.out.println(medicalRecord.getLastName() + "/" + medicalRecord.getFirstName() + "/" + birthDate + "/"
//					+ age + "/" + category);

		}
		return 0;
	}

	public String findCategoryOfAgeByPerson(String firstName, String lastName)
			throws JsonParseException, JsonMappingException, IOException, ParseException {

		long age = 0;

		LocalDate birthDate = null;
		for (MedicalRecord medicalRecord : globalData.getMedicalrecords()) {

			if ((lastName.equals(medicalRecord.getLastName()) && (firstName.equals(medicalRecord.getFirstName())))) {

				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
				birthDate = LocalDate.parse(medicalRecord.getBirthdate(), dtf);
				LocalDate today = LocalDate.now(ZoneId.systemDefault());
				age = ChronoUnit.YEARS.between(birthDate, today);
				boolean child = age < 18;
				String category = new String();
				if (child == true) {
					category = "child";

				} else {
					category = "adult";

				}

				return category;
			}
		}

		return null;

	}

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
