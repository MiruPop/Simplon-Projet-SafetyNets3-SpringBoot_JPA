package com.safetynet.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.model.Person;
import com.safetynet.repository.PersonRepository;

@Service
public class PersonService {

	PersonRepository personRepository;

	@Autowired
	public PersonService(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	/********************************************************************\
	*                METHODES POUR LES ENDPOINTS DU CRUD                 *
	\********************************************************************/
	

//	public Person updatePerson(Person personne) {
//		Person p = new Person();
//		p.setId(personne.getId());
//		p.setFirstName(personne.getFirstName());
//		p.setLastName(personne.getLastName());
//		p.setAddress(personne.getAddress());
//		p.setZip(personne.getZip());
//		p.setCity(personne.getCity());
//		p.setPhone(personne.getPhone());
//		p.setEmail(personne.getEmail());
//		return p;
//	}



			/********************************************************************\
			*             METHODES POUR LES URLs DEMANDES DANS LE TP             *
			\********************************************************************/
	
	

	//	http://localhost:8080/communityEmail?city=<city>    

	public List<String> emailListByCity(String city) {
		List<String> emails = new ArrayList<>();

		for (Person p : personRepository.findAll()) {
			if (p.getCity().equals(city))
				emails.add(p.getEmail());
		}
		return emails;
	}
//	
//
//	public Persons getByFullName(String firstName, String lastName) {
//		Persons unePersonne = new Persons();
//
//		for (Persons p : DataJson.persons) {
//			if (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)) {
//				unePersonne.setFirstName(p.getFirstName());
//				unePersonne.setLastName(p.getLastName());
//				unePersonne.setAddress(p.getCity());
//				unePersonne.setZip(p.getZip());
//				unePersonne.setCity(p.getCity());
//				unePersonne.setPhone(p.getPhone());
//				unePersonne.setEmail(p.getEmail());
//			}
//		}
//		return unePersonne;
//	}

}
