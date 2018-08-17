package org.tech.demo.base.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tech.demo.base.persistence.model.PersonEntity;
import org.tech.demo.base.services.PersonService;

@RestController
@RequestMapping("/person")
@CrossOrigin(origins = "http://localhost:4200") //angular app
public class RequestMappingShortcutsController {

	@Autowired
	PersonService personService;

	@GetMapping("/{id}")
	public PersonEntity getById(@PathVariable Long id) {
		return personService.processAndReturnPerson(id);
	}

	@GetMapping
	public Collection<PersonEntity> getByAge(@RequestParam(required = false) Integer age) {
		if (age == null) {
			return personService.processAndReturnPeople();
		} else {
			return personService.getYoungerThan(age);
		}
	}

	@PostMapping
	public PersonEntity post(@RequestBody PersonEntity person) {
		// do some validation here
		return personService.persistPerson(person);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		personService.removePerson(id);
	}

}
