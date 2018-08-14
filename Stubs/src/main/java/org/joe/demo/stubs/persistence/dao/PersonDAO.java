package org.joe.demo.stubs.persistence.dao;

import java.util.Collection;

import org.joe.demo.stubs.persistence.model.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonDAO extends JpaRepository<PersonEntity, Long> {
	
	@Query(value = "SELECT * FROM public.person WHERE person.age <= :age", nativeQuery = true)
	Collection<PersonEntity> getPeopleByAge(@Param("age") Integer age);

}
