package com.safetynet.safetynetrescuesystem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.safetynet.safetynetrescuesystem.view.JacksonReader;

@SpringBootApplication
public class SafetyNetRescueSystemApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SafetyNetRescueSystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		JacksonReader.readDataFileFirestations(args);
		JacksonReader.readFirestationsV2(args);
	}
}
