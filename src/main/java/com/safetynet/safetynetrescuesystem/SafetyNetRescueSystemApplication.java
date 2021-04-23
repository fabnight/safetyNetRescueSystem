package com.safetynet.safetynetrescuesystem;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetrescuesystem.model.Person;
import com.safetynet.safetynetrescuesystem.service.DataFile;
import com.safetynet.safetynetrescuesystem.service.DataFileReader;

@SpringBootApplication
public class SafetyNetRescueSystemApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SafetyNetRescueSystemApplication.class, args);
	}
	//@Autowired
	//static DataFile dataFile;
	@Override
	public void run(String... args) throws Exception {
		
		
		
		
		//Person Tenley = new Person();
		//Person Boyd =  new Person();
		//DataFileReader.calculOfageOfPerson(Tenley,Boyd);
		
	}
}
