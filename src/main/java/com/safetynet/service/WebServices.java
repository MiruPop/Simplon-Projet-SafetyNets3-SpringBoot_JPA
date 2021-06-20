package com.safetynet.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.model.Firestation;
import com.safetynet.model.Person;
import com.safetynet.utility.DataArriereCuisine;
import com.safetynet.repository.FirestationRepository;
import com.safetynet.repository.PersonRepository;

@Service
public class WebServices {

	@Autowired
	private PersonRepository persRepo;
	@Autowired
	private FirestationRepository fireRepo;
	@Autowired
	DataArriereCuisine comisCuisine;

	/**********************************************************************************
	 * \ Cette classe contient des méthodes qui permettent de répondre aux requêtes
	 * * URL du cahier de charges. * Elle se sert parfois des méthodes utilitaires
	 * de la classe DataArriereCuisine * * \
	 **********************************************************************************/

//	http://localhost:8080/communityEmail?city=<city>
	// Cette url doit retourner les adresses mail de tous les habitants de la ville.

	public List<String> emailListByCity(String city) {
		List<String> emails = new ArrayList<>();

		for (Person p : persRepo.findAll()) {
			if (p.getCity().equals(city))
				emails.add(p.getEmail());
		}
		return emails;
	}

//	http://localhost:8080/firestation?stationNumber=<station_number>
	// Cette url doit retourner une liste des personnes couvertes par la caserne de
	// pompiers correspondante.
	// Donc, si le numéro de station = 1, elle doit renvoyer les habitants couverts
	// par la station numéro 1.
	// La liste doit inclure les informations spécifiques suivantes : prénom, nom,
	// adresse, numéro de téléphone.
	// De plus, elle doit fournir un décompte du nombre d'adultes et du nombre
	// d'enfants (tout individu âgé de 18 ans ou
	// moins) dans la zone desservie.

	public Map<String, Object> getPersonsAndCountByFirestation(String station) {
		Map<String, Object> personsAndCount = new HashMap<>();
		List<String> infosPersonnes = new ArrayList<>();

		Integer numberOfAdults = 0;
		Integer numberOfChildren = 0;

// je regroupe les personnes en fonction de la caserne (en paramètre) qui dessert leur adresse
// en utilisant la méthode de tri du package Utility
		List<Person> personnesParCaserne = comisCuisine.getPersonsServedByFirestation(station);

// je vérifie l'age des personnes de la liste des personnes
		for (Person person : personnesParCaserne) {
			Integer age = comisCuisine.getPersonAge(person);
			if (age > 18)
				numberOfAdults++;
			else
				numberOfChildren++;
		}
// pour chaque personne, je sélectionne uniquement les champs demandés
		for (Person person : personnesParCaserne) {
			StringBuilder personInfo = new StringBuilder();
			personInfo.append(person.getFirstName());
			personInfo.append(person.getLastName());
			personInfo.append(person.getAddress());
			personInfo.append(person.getPhone());
			infosPersonnes.add(personInfo.toString());
		}
//	et je remplis ma Map avec les infos demandés
//		personsAndCount.put("Station numéro: ", station);
		personsAndCount.put("Personnes couvertes", infosPersonnes);
		personsAndCount.put("Nombre d'adultes", numberOfAdults);
		personsAndCount.put("Nombre d'enfants", numberOfChildren);

		return personsAndCount;
	}

//	http://localhost:8080/fire?address=<address>
//	Cette url doit retourner la liste des habitants vivant à l’adresse donnée ainsi que le numéro de la caserne
//	de pompiers la desservant. La liste doit inclure le nom, le numéro de téléphone, l'âge et les antécédents
//	médicaux (médicaments, posologie et allergies) de chaque personne.

	public Map<String, Object> getPersonsInfoAndStationByAddress(String adresse) {
		Map<String, Object> result = new HashMap<>();
		List<Object> personnes = new ArrayList<>();

		for (Firestation f : fireRepo.findAll()) {

			if (f.getAddress().equals(adresse)) {
				result.put("Caserne désservant l'adresse: ", f.getStation());
			}
		}

		for (Person p : persRepo.findAll()) {
			if (p.getAddress().equals(adresse)) {
				StringBuilder personInfo = new StringBuilder();
				personInfo.append(p.getFirstName());
				personInfo.append(" ");
				personInfo.append(p.getLastName());
				personInfo.append(", phone: ");
				personInfo.append(p.getPhone());
				personInfo.append(", ");
				personInfo.append(comisCuisine.getPersonAge(p));
				personInfo.append("ans, allergies: ");
				personInfo.append(comisCuisine.getPersonMedicalrecord(p).getAllergies());
				personInfo.append(", médication: ");
				personInfo.append(comisCuisine.getPersonMedicalrecord(p).getMedications());

				personnes.add(personInfo);

			}
			result.put("Personnes à l'adresse: ", personnes);

		}
		return result;
	}

//	http://localhost:8080/childAlert?address=<address>
	// Cette url doit retourner une liste d'enfants (tout individu âgé de 18 ans ou
	// moins) habitant à cette adresse.
	// La liste doit comprendre le prénom et le nom de famille de chaque enfant, son
	// âge et une liste des autres
	// membres du foyer. S'il n'y a pas d'enfant, cette url peut renvoyer une chaîne
	// vide.

	public Map<String, Object> getChildrenAndFamilyMembersByAddress(String address) {
		Map<String, Object> childrenAndFamily = new HashMap<>();

// je regroupe les personnes par adresse
		List<Person> personsByAddress = new ArrayList<>();
		for (Person person : persRepo.findAll()) {
			if (person.getAddress().equals(address)) {
				personsByAddress.add(person);
				System.out.println(personsByAddress);
			}
		}

// je regroupe les enfants de l'adresse	
		List<Person> childrenInHousehold = new ArrayList<>();
		for (Person person : personsByAddress) {
			if (comisCuisine.getPersonAge(person) <= 18) {
				childrenInHousehold.add(person);
			}
		}
// je crée un affichage personnalisé des infos des enfants
		for (Person p : childrenInHousehold) {
			if (comisCuisine.getPersonAge(p) <= 18) {
				StringBuilder child = new StringBuilder();
				child.append(p.getFirstName());
				child.append(" ");
				child.append(p.getLastName());
				child.append(", ");
				child.append(comisCuisine.getPersonAge(p));
				child.append(" ans");
				childrenAndFamily.put("Alerte enfants: ", child);
			} else {
				childrenAndFamily.put("Alerte enfants: ", "pas d'enfants à cette adresse");
			}
			childrenAndFamily.put("Autres personnes à l'adresse: ", personsByAddress);
			System.out.println(childrenAndFamily);
		}

		System.out.println(childrenAndFamily);
		return childrenAndFamily;
	}

//	http://localhost:8080/phoneAlert?firestation=<firestation_number>
//		Cette url doit retourner une liste des numéros de téléphone des résidents desservis par la caserne de
//		pompiers. Nous l'utiliserons pour envoyer des messages texte d'urgence à des foyers spécifiques.

	public List<String> getPersonsPhonesByFirestationNumber(String station) {
		List<String> phones = new ArrayList<>();

		for (Firestation f : fireRepo.findAll()) {
			if (f.getStation().equals(station)) {
				for (Person p : persRepo.findAll()) {
					if (f.getAddress().equals(p.getAddress())) {
						phones.add(p.getPhone());
					}
				}
			}
		}
		return phones;
	}

//	http://localhost:8080/flood/stations?stations=<a list of station_numbers>
//		Cette url doit retourner une liste de tous les foyers desservis par la caserne. Cette liste doit regrouper les
//		personnes par adresse. Elle doit aussi inclure le nom, le numéro de téléphone et l'âge des habitants, et
//		faire figurer leurs antécédents médicaux (médicaments, posologie et allergies) à côté de chaque nom.

//	http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
//		Cette url doit retourner le nom, l'adresse, l'âge, l'adresse mail et les antécédents médicaux (médicaments,
//		posologie, allergies) de chaque habitant. Si plusieurs personnes portent le même nom, elles doivent
//		toutes apparaître.

}