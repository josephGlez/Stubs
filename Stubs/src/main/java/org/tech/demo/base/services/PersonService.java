package org.tech.demo.base.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tech.demo.base.persistence.dao.PersonDAO;
import org.tech.demo.base.persistence.model.PersonEntity;

/**
 * This is where you're business logic should reside. By placing @Transactional
 * at the class level, all functions are readOnly transactionals
 *
 */
@Transactional
@Service
public class PersonService {

	@Autowired
	PersonDAO personDao;

	public Collection<PersonEntity> processAndReturnPeople() {
		return personDao.findAll();
	}

	public PersonEntity processAndReturnPerson(Long id) {
		return personDao.findById(id).orElse(new PersonEntity());
	}

	@Transactional(readOnly = false)
	public PersonEntity persistPerson(PersonEntity person) {
		return personDao.saveAndFlush(person);
	}

	@Transactional(readOnly = false)
	public void removePerson(Long id) {
		if (personDao.existsById(id)) {
			personDao.deleteById(id);
		}
	}

	public Collection<PersonEntity> getYoungerThan(int age) {
		return personDao.getPeopleByAge(age);
	}

}
