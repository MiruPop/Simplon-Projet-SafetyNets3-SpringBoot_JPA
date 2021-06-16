package com.safetynet.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.model.MedRecord;
import com.safetynet.model.Person;
import com.safetynet.repository.MedRecordRepository;
import com.safetynet.repository.PersonRepository;

@Service
public class PersonService {
	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	private MedRecordRepository mrr;

	/********************************************************************\
	*                METHODES INTERMEDIIRES                 *
	\********************************************************************/
	
	public MedRecord getMedicalRecord(String firstName, String lastName) {
		List<MedRecord> medicalRecords = mrr.findAll();
		
		for(MedRecord medicalRecord: medicalRecords) {
			if(medicalRecord.getFirstName().equals(firstName)
					&& medicalRecord.getLastName().equals(lastName))
				
				return medicalRecord;
		}
		
		return null;
	}
	
	public String getPersonBirthdate(String firstName, String lastName) {
		return this.getMedicalRecord(firstName, lastName).getBirthdate();
	}
	
	
//	public String getPersonBirthdate(MedRecord mr) {
//			for(Person p : personRepository.findAll()) {
//				if (p.getFirstName().equals(mr.getFirstName())
//						&& p.getLastName().equals(mr.getLastName())) {
//					return mr.getBirthdate();
//				}
//			}
//		return null;
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
//	public Map<Person,MedicalRecord> getMedRecord(String firstName, String lastName) {
//		Persons unePersonne = new Persons();
	//décla nouvelle map
//
//		for (Persons p : DataJson.persons) {
//			if (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)) {
//				unePersonne.setFirstName(p.getFirstName());
//				unePersonne.setLastName(p.getLastName());
		// if (medRecord.getFirstName().equals(unePersonne.getFirstName() && medRecord.getLastName().equals(unePersonne.getLastName()) {
		//	nouvelle map.put
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
