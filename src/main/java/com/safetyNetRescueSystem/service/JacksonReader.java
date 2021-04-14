package com.safetyNetRescueSystem.service;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonReader {

	public DataFile personReader(String[] args) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		DataFile dataFile = mapper.readValue(new File("src\\main\\resources\\data.json"), DataFile.class);
		return dataFile;

	}

	public static void readDataFilePersons(String[] args) throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();

		DataFile dataFile = mapper.readValue(new File("src\\main\\resources\\data.json"), DataFile.class);

		System.out.println(dataFile.getPersons());
	}

	public static void readDataFileFirestations(String[] args)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();

		DataFile dataFile = mapper.readValue(new File("src\\main\\resources\\data.json"), DataFile.class);

		System.out.println(dataFile.getFirestation());
	}

	public static void readDataFileMedicalrecords(String[] args)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();

		DataFile dataFile = mapper.readValue(new File("src\\main\\resources\\data.json"), DataFile.class);

		System.out.println(dataFile.getMedicalrecords());
	}

	/**
	 * public static void readFirestationsV2(String args[]){
	 * 
	 * ObjectMapper mapper = new ObjectMapper(); String jsonString =
	 * "{\"Firestation{\"address=\":\"address\",\"station=\"station\"}}";
	 * 
	 * //map json to student try{ DataFileFirestations dataFileFirestations =
	 * mapper.readValue(jsonString, DataFileFirestations.class);
	 * 
	 * System.out.println(dataFileFirestations);
	 * 
	 * jsonString =
	 * mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dataFileFirestations);
	 * 
	 * System.out.println(jsonString); } catch (JsonParseException e) {
	 * e.printStackTrace();} catch (JsonMappingException e) { e.printStackTrace(); }
	 * catch (IOException e) { e.printStackTrace(); } }
	 **/
}
