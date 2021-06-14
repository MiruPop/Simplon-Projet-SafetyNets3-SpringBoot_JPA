package com.safetynet.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PersonTest {

	//A ETUDIER
	
//	private static Validator validator;
//
//    @BeforeAll
//    public static void setUpValidator() {
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        validator = factory.getValidator();
//    }
//    
//	@Test
//	public void whenNotBlankFirstname_thenNoConstraintViolations() {
//	    Person person = new Person();
//	    person.setFirstName("John");
//	    Set<ConstraintViolation<Person>> violations = validator.validate(person);
//	 
//	    assertThat(violations.size()).isEqualTo(0);
//	}
//	    
//	@Test
//	public void whenBlankFirstname_thenOneConstraintViolation() {
//		Person person = new Person();
//		person.setFirstName(" ");
//	    Set<ConstraintViolation<Person>> violations = validator.validate(person);
//	 
//	    assertThat(violations.size()).isEqualTo(1);
//	}
//	    
//	@Test
//	public void whenEmptyName_thenOneConstraintViolation() {
//		Person person = new Person();
//		person.setFirstName("");
//	    Set<ConstraintViolation<Person>> violations = validator.validate(person);
//	 
//	    assertThat(violations.size()).isEqualTo(1);
//	}
//	    
//	@Test
//	public void whenNullName_thenOneConstraintViolation() {
//		Person person = new Person();
//		person.setFirstName(null);
//	    Set<ConstraintViolation<Person>> violations = validator.validate(person);
//	 
//	    assertThat(violations.size()).isEqualTo(1);
//	}

}
