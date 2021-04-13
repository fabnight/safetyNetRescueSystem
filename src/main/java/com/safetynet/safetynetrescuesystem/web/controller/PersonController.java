package com.safetynet.safetynetrescuesystem.web.controller;

import java.util.List;

import javax.swing.text.View;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.safetynet.safetynetrescuesystem.model.Person;
import com.safetynet.safetynetrescuesystem.view.*;
@RestController
public class PersonController {
@RequestMapping(value="/Persons",method=RequestMethod.GET)
public String listePersons() {
	return "un exemple de person";
}

//Récupérer une personne par son Prénom/Nom
@JsonView(View.FilterFullInfoByName.class)
@GetMapping(value = "personInfo", produces = "application/json")
    public List<Person> getFullInfoByName(
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName") String lastName) {
        return personService.getFullInfoByName(firstName, lastName);
    }
   // @GetMapping(value="/Persons/{firstName}/{lastName}")
	//Person person=new Person(firstName, lastName, new String("5 rue Jean Moquet"),new String("Hambourg"),new String("97451"), new String("44 55 55 66"),new String("ert@gmail.com")) ;
  //return person;
}

