package com.safetynet.safetynetrescuesystem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.safetyNetRescueSystem.service.JacksonReader;

@SpringBootApplication
public class SafetyNetRescueSystemApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SafetyNetRescueSystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//JacksonReader.readDataFilePersons(args);
		//JacksonReader.readDataFileFirestations(args);
		//JacksonReader.readDataFileMedicalrecords(args);
	}
}
