package com.safetynet.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.model.Firestation;
import com.safetynet.model.MedRecord;
import com.safetynet.model.Person;
import com.safetynet.repository.FirestationRepository;
import com.safetynet.utility.DataManager;
//import com.safetynet.repository.MedRecordRepository;
import com.safetynet.repository.PersonRepository;

@Service
public class AlertsService {
//	private FirestationRepository firestationRepository;
//	private MedRecordRepository medrecordRepository;
	private PersonRepository personRepository;
	DataManager dataManager;


//	http://localhost:8080/firestation?stationNumber=<station_number>
	public TreeMap<String,String> getPersonsByFirestationNumber(String station) {
		TreeMap<String,String> personsAndCount = new TreeMap<>();
		Integer numberOfAdults = 0;
		Integer numberOfChildren = 0;
		//je peuple la liste d'adresses en fonction du numéro de station donné en paramètre
		Set<String> addresses = dataManager.getAddressesByFirestationNumber(station);
		
		//je peuple la liste de personnes avec les personnes qui ont des adresses contenues dans la liste d'adresses
		Set<Person> personnes = new HashSet<>();
		for (Person person : personRepository.findAll()) {
			for (String address : addresses) {
				if (person.getAddress().equals(address)) {
					personnes.add(person);
				}
			}
		}
		
		for (Person person : personnes) {
			int age = dataManager.getPersonAge();
			if (age >= 18)
				numberOfAdults ++;
			else
				numberOfChildren ++;
		}
		
		personsAndCount.put("Adresses couvertes", addresses.toString());
		personsAndCount.put("Nombre d'adultes", numberOfAdults.toString());
		personsAndCount.put("Nombre d'enfants", numberOfAdults.toString());
		
		
		return personsAndCount;
	}
	

}