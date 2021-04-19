package com.safetynet.safetynetrescuesystem;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetyNetRescueSystem.service.DataFile;
import com.safetyNetRescueSystem.service.DataFileReader;

@SpringBootApplication
public class SafetyNetRescueSystemApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SafetyNetRescueSystemApplication.class, args);
	}
	@Autowired
	static DataFile dataFile;
	@Override
	public void run(String... args) throws Exception {
	
		//String Name = null;
		String city = null;
		//DataFileReader.personsReaderTest2(lastName);
		//DataFileReader.findPersonByName(lastName);
		//DataFileReader.findEmailByLastName(Name);
		DataFileReader.findEmailByCity(city);
		//DataFileReader.personReader(args);
		
	}
}
