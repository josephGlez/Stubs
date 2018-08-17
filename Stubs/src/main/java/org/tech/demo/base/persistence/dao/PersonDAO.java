package org.tech.demo.base.persistence.dao;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.tech.demo.base.persistence.model.PersonEntity;

//@RepositoryRestResource exposes all JpaRepository DAO methods as RESTful endpoints
// http://localhost:8081/people
@RepositoryRestResource(path = "people")
@CrossOrigin(origins = "http://localhost:4200")//angular app
public interface PersonDAO extends JpaRepository<PersonEntity, Long> {
	
	//Exposes custom queries as RESTful endpoints under the {DAO_Path}/search/{path}?{param}={val}
	// http://localhost:8081/people/search/age?age=18
	@RestResource
	@Query(value = "SELECT * FROM public.person WHERE person.age <= :age", nativeQuery = true)
	Collection<PersonEntity> getPeopleByAge(@Param("age") Integer age);

}
