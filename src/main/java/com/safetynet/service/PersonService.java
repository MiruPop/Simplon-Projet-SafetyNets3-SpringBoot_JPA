package com.safetynet.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.repository.PersonRepository;

@Service
public class PersonService {
	@Autowired
	PersonRepository personRepository;

}
