package com.safetynet.safetynetrescuesystem.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetynetrescuesystem.model.Person;

@RestController
public class PersonController {
@RequestMapping(value="/Persons",method=RequestMethod.GET)
public String listePersons() {
	return "un exemple de person";
}

//Récupérer une personne par son Prénom/Nom

@GetMapping(value="/Persons/{firstName}/{lastName}")
public Person afficherUnePerson(@PathVariable String firstName, String lastName) {
  Person person=new Person(firstName, lastName, new String("5 rue Jean Moquet"),new String("Hambourg"),new String("97451"), new String("44 55 55 66"),new String("ert@gmail.com")) ;
  return person;
}
}
