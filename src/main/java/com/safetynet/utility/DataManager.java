package com.safetynet.utility;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.safetynet.model.Firestation;
import com.safetynet.model.MedRecord;
import com.safetynet.model.Person;
import com.safetynet.repository.FirestationRepository;
import com.safetynet.repository.MedRecordRepository;

public class DataManager {
	@Autowired
	private Person person;
	
	MedRecordRepository medRecRepo;
	FirestationRepository firestationRepo;
	
	public MedRecord getPersonMedicalrecord() {
		List <MedRecord> listeDossiersMedicaux = medRecRepo.findAll();
		for(MedRecord medicalRecord: listeDossiersMedicaux) {
			if(medicalRecord.getFirstName().equals(this.person.getFirstName())
					&& medicalRecord.getLastName().equals(this.person.getLastName()))
				
				return medicalRecord;
		}
		
		return null;
	}
	
	public String getPersonBirthDate() {
		return this.getPersonMedicalrecord().getBirthdate();
	}
	
	public int getPersonAge() {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
		LocalDate today = LocalDate.now();
		LocalDate birthDate = LocalDate.parse(this.getPersonBirthDate(), formatter);
		Period age = Period.between(birthDate, today);
		
		return age.getYears();
	}
	
	public Set<String> getAddressesByFirestationNumber(String station) {
		Set<String> addresses = new HashSet<>();
		for (Firestation firestation : firestationRepo.findAll()) {
			if (firestation.getStation().equals(station)) {
				addresses.add(firestation.getAddress());
			}
		}
		return addresses;
	}
}
