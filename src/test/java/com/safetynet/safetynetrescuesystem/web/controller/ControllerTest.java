package com.safetynet.safetynetrescuesystem.web.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetrescuesystem.service.FirestationService;
import com.safetynet.safetynetrescuesystem.service.MedicalRecordService;
import com.safetynet.safetynetrescuesystem.service.PersonService;

@WebMvcTest(controllers = Controller.class)
@AutoConfigureMockMvc
class ControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private FirestationService firestationService;
	@MockBean
	private PersonService personService;
	@MockBean
	private MedicalRecordService medicalRecordService;
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void testFirestation() throws Exception {
		mockMvc.perform(get("/firestation").contentType(MediaType.APPLICATION_JSON).param("stationNumber", "2"))
				.andExpect(status().isOk());

	}

	@Test
	void testgetChildrenByAdress() throws Exception {
		mockMvc.perform(get("/childAlert").contentType(MediaType.APPLICATION_JSON).param("address", "1509 Culver St"))
		.andExpect(status().isOk());
	}

	@Test
	void testGetPersonPhone() throws Exception {
		mockMvc.perform(get("/phoneAlert").contentType(MediaType.APPLICATION_JSON).param("firestation", "2"))
				.andExpect(status().isOk());

	}

	@Test
	void testFire() throws Exception {
		mockMvc.perform(get("/fire").contentType(MediaType.APPLICATION_JSON).param("address", "1509 Culver St"))
				.andExpect(status().isOk());
	}

	@Test
	void testFirestationsList() throws Exception {
		mockMvc.perform(get("/flood/stations").contentType(MediaType.APPLICATION_JSON).param("stations", "2,3"))
				.andExpect(status().isOk());

	}

	@Test
	void testGetPersonEmailByCity() throws Exception {
		mockMvc.perform(get("/communityEmail").param("city", "Culver")).andExpect(status().isOk());
	}

	@Test
	void testgetAllergies() throws Exception {
		mockMvc.perform(get("/personInfo").contentType(MediaType.APPLICATION_JSON).param("firstName","John").param("lastName","Boyd"))
				.andExpect(status().isOk());
	}

//

//

//
	@Test
	void testPostPerson() throws Exception {
		mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON).content(
				"{\"firstName\":\"John\",\"lastname\":\"Boyd\" ,\"address\":\"123, st Rover\",\"city\":\"Culver\",\"zip\":\"5678\",\"phone\":\"456 78 76\",\"email\":\"jb@email.fr\"}"))

				.andExpect(status().isOk());
	}

	@Test
	void testPutPerson() throws Exception {
		mockMvc.perform(put("/person").contentType(MediaType.APPLICATION_JSON).content(
				"{\"firstName\":\"John\",\"lastname\":\"Boyd\" ,\"address\":\"123, st Rover\",\"city\":\"Culver\",\"zip\":\"5678\",\"phone\":\"456 78 76\",\"email\":\"jb@email.fr\"}"))

				.andExpect(status().isOk());
	}

	@Test
	void testDeletePerson() throws Exception {
		mockMvc.perform(delete("/person").contentType(MediaType.APPLICATION_JSON).content(
				"{\"firstName\":\"John\",\"lastname\":\"Boyd\" ,\"address\":\"123, st Rover\",\"city\":\"Culver\",\"zip\":\"5678\",\"phone\":\"456 78 76\",\"email\":\"jb@email.fr\"}"))

				.andExpect(status().isOk());
	}

	@Test
	void testPostFirestation() throws Exception {

		mockMvc.perform(post("/firestation").contentType(MediaType.APPLICATION_JSON)
				.content("{\"address\":\"9999 Culver St\",\"station\":\"13\"}"))

				.andExpect(status().isOk());

	}

	@Test
	void testPutFirestation() throws Exception {
		mockMvc.perform(put("/firestation").contentType(MediaType.APPLICATION_JSON)
				.content("{\"address\":\"9999 Culver St\",\"station\":\"13\"}"))

				.andExpect(status().isOk());
	}

	@Test
	void testDeleteFirestation() throws Exception {
		mockMvc.perform(delete("/firestation").contentType(MediaType.APPLICATION_JSON)
				.content("{\"address\":\"9999 Culver St\",\"station\":\"13\"}"))

				.andExpect(status().isOk());
	}

	@Test
	void testPostMedicalRecord() throws Exception {
		mockMvc.perform(post("/medicalRecord").contentType(MediaType.APPLICATION_JSON).content(
				"{\"firstName\":\"John\",\"lastname\":\"Boyd\" ,\"birthdate\":\"01/01/2000\",\"medications\":[\"aznol:350mg\", \"hydrapermazol:100mg\"],\"allergies\":[\"nillacilan\"]}"))

				.andExpect(status().isOk());
	}

	@Test
	void testPutMedicalRecords() throws Exception {
		mockMvc.perform(put("/medicalRecord").contentType(MediaType.APPLICATION_JSON).content(
				"{\"firstName\":\"John\",\"lastname\":\"Boyd\" ,\"birthdate\":\"01/01/2000\",\"medications\":[\"aznol:350mg\", \"hydrapermazol:100mg\"],\"allergies\":[\"nillacilan\"]}"))

				.andExpect(status().isOk());
	}

	@Test
	void testDeleteMedicalRecords() throws Exception {
		mockMvc.perform(delete("/medicalRecord").contentType(MediaType.APPLICATION_JSON).content(
				"{\"firstName\":\"John\",\"lastname\":\"Boyd\" ,\"birthdate\":\"01/01/2000\",\"medications\":[\"aznol:350mg\", \"hydrapermazol:100mg\"],\"allergies\":[\"nillacilan\"]}"))

				.andExpect(status().isOk());
	}

}
