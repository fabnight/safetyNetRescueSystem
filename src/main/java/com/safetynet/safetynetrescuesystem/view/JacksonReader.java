package com.safetynet.safetynetrescuesystem.view;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetrescuesystem.model.DataFile;
import com.safetynet.safetynetrescuesystem.model.DataFilePersons;

public class JacksonReader {

	public DataFile personReader(String[] args) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		DataFile dataFile = mapper.readValue(new File("src\\main\\resources\\data.json"), DataFile.class);
		return dataFile;

	}

	public static void readDataFile(String[] args) throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		DataFile dataFile = mapper.readValue(new File("src\\main\\resources\\data.json"), DataFile.class);

		System.out.println(dataFile);
	}

	public static void readDataFilePersons(String[] args) throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		DataFilePersons dataFilePersons = mapper.readValue(new File("src\\main\\resources\\data.json"),
				DataFilePersons.class);

		System.out.println(dataFilePersons);
				
	}
}
