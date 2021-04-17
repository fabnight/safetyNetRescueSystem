package com.safetynet.safetynetrescuesystem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import com.safetyNetRescueSystem.service.DataFileReader;

@SpringBootApplication
public class SafetyNetRescueSystemApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SafetyNetRescueSystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	
		String lastName = null;
		DataFileReader.findPersonByName(lastName);
		//JacksonReader.readDataFilePersons(args);
		//DataFileReader.personReader(args);
		
	}
}
