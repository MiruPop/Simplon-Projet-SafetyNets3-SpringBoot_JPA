package com.safetynet.utility;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynet.model.Firestation;
import com.safetynet.model.MedRecord;
import com.safetynet.model.Person;
import com.safetynet.repository.FirestationRepository;
import com.safetynet.repository.MedRecordRepository;
import com.safetynet.repository.PersonRepository;

@Component
public class DataArriereCuisine {
	
	@Autowired
	PersonRepository personRepo;
	@Autowired
	MedRecordRepository medRecRepo;
	@Autowired
	FirestationRepository firestationRepo;
	
	
	/***************************************************************************\
	*    Cette classe contient des méthodes intermédiaires, qui reproduisent    *
	*    des requêtes SQL simples et qui seront utilisées par les webservices   *
	\***************************************************************************/
	
	public MedRecord getPersonMedicalrecord(Person p) {
		List <MedRecord> listeDossiersMedicaux = medRecRepo.findAll();
		for(MedRecord medicalRecord: listeDossiersMedicaux) {
			if(medicalRecord.getFirstName().equals(p.getFirstName())
					&& medicalRecord.getLastName().equals(p.getLastName()))
				
				return medicalRecord;
		}
		
		return null;
	}
	

	public String getPersonBirthDate(Person p) {
		List <MedRecord> listeDossiersMedicaux = medRecRepo.findAll();
		for(MedRecord medicalRecord: listeDossiersMedicaux) {
			if(medicalRecord.getFirstName().equals(p.getFirstName())
					&& medicalRecord.getLastName().equals(p.getLastName()))
				return medicalRecord.getBirthdate();
		}				
		return null;
	}

// la méthode getAge utilise la méthode getPersonBirthDate antérieurement définie
	public int getPersonAge(Person p) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
		LocalDate today = LocalDate.now();
		LocalDate birthDate = LocalDate.parse(this.getPersonBirthDate(p), formatter);
		Period age = Period.between(birthDate, today);
		
		return age.getYears();
	}
	
	public List<String> getAddressesByFirestationNumber(String station) {
		List<String> addresses = new ArrayList<>();
		for (Firestation firestation : firestationRepo.findAll()) {
			if (firestation.getStation().equals(station)) {
				addresses.add(firestation.getAddress());
			}
		}
		return addresses;
	}
	
	public List<Person> getPersonsServedByFirestation(String station) {
//		je peuple une liste d'adresses par caserne, en utilisant la méthode antérieurement définie 
		List<String> addresses = this.getAddressesByFirestationNumber(station);
		List<Person> listePersonnes = new ArrayList<>();
		for (Person person : personRepo.findAll()) {
			for (String address : addresses) {
				if (person.getAddress().equals(address)) {
					listePersonnes.add(person);
				}
			}
		}
		return listePersonnes;
	}

//	public List<Person> getAllPersonsByAddress(String address) {
//		List<Person> persons = new ArrayList<>();
//		for (Person person : personRepo.findAll()) {
//			if(person.getAddress().equals(address)) {
//				persons.add(person);
//			}
//		}
//		return persons;
//	}
//	
//	public List<Person> getChildrenByAddress(String address) {
//		List<Person> childrenInHousehold = new ArrayList<>();
//		for (Person person : personRepo.findAll()) {
//			if(person.getAddress().equals(address)) {
//				if(this.getPersonAge(person)<=18) {
//					childrenInHousehold.add(person);
//				}
//			}
//		}
//		return childrenInHousehold;
//	}
}
