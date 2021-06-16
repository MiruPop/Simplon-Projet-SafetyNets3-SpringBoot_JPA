package com.safetynet.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.model.Person;
import com.safetynet.utility.DataArriereCuisine;
//import com.safetynet.repository.MedRecordRepository;
import com.safetynet.repository.PersonRepository;

@Service
public class WebServices {

	@Autowired
	private PersonRepository persRepo;
	@Autowired
	DataArriereCuisine comisCuisine;

	/**********************************************************************************\
	*    Cette classe contient des méthodes qui permettent de répondre aux requêtes    *
	*    URL du cahier de charges.                                                     *
	*    Elle se sert parfois des méthodes utilitaires de la classe DataArriereCuisine *                       *
	\**********************************************************************************/

//	http://localhost:8080/communityEmail?city=<city>
	//	Cette url doit retourner les adresses mail de tous les habitants de la ville.

	public List<String> emailListByCity(String city) {
		List<String> emails = new ArrayList<>();

		for (Person p : persRepo.findAll()) {
			if (p.getCity().equals(city))
				emails.add(p.getEmail());
		}
		return emails;
	}

//	http://localhost:8080/firestation?stationNumber=<station_number>
	//	Cette url doit retourner une liste des personnes couvertes par la caserne de pompiers correspondante.
	//	Donc, si le numéro de station = 1, elle doit renvoyer les habitants couverts par la station numéro 1.
	//	La liste doit inclure les informations spécifiques suivantes : prénom, nom, adresse, numéro de téléphone.
	//	De plus, elle doit fournir un décompte du nombre d'adultes et du nombre d'enfants (tout individu âgé de 18 ans ou
	//	moins) dans la zone desservie.	
	
	public Map<String,Object> getPersonsByFirestationNumber(String station) {
		Map<String,Object> personsAndCount = new TreeMap<>();
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
				numberOfAdults ++;
			else
				numberOfChildren ++;
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
		personsAndCount.put("Station numéro: ", station);
		personsAndCount.put("Personnes couvertes", infosPersonnes);
		personsAndCount.put("Nombre d'adultes", numberOfAdults);
		personsAndCount.put("Nombre d'enfants", numberOfChildren);
		
		return personsAndCount;
	}
	
//	http://localhost:8080/childAlert?address=<address>
	//	Cette url doit retourner une liste d'enfants (tout individu âgé de 18 ans ou moins) habitant à cette adresse.
	//	La liste doit comprendre le prénom et le nom de famille de chaque enfant, son âge et une liste des autres
	//	membres du foyer. S'il n'y a pas d'enfant, cette url peut renvoyer une chaîne vide.
	
	public Map<String,Object> getChildrenAndFamilyMembersByAddress(String address) {
		Map<String,Object> childrenAndFamily = new TreeMap<>();
// je regroupe les personnes par adresse
// en utilisant la méthode de tri du package Utility
		List<Person> personnesParAdresse = comisCuisine.getAllPersonsByAddress(address);
		
		if (comisCuisine.getChildrenByAddress(address).isEmpty())
			childrenAndFamily.put("Alerte enfants: ", "pas d'enfants à cette adresse");
		else {
			for (Person enfant : comisCuisine.getChildrenByAddress(address)) {
				StringBuilder child = new StringBuilder();
				child.append(enfant.getFirstName());
				child.append(" ");
				child.append(enfant.getLastName());
				child.append(", ");
				child.append(comisCuisine.getPersonAge(enfant));
				child.append(" ans");
				childrenAndFamily.put("Alerte enfants: ", child.toString());
				personnesParAdresse.remove(enfant);
				childrenAndFamily.put("Autres membres de la famille: ", personnesParAdresse);
			}
		}
		
		return childrenAndFamily;
	}
	

		
//	http://localhost:8080/phoneAlert?firestation=<firestation_number>
//		Cette url doit retourner une liste des numéros de téléphone des résidents desservis par la caserne de
//		pompiers. Nous l'utiliserons pour envoyer des messages texte d'urgence à des foyers spécifiques.
//	http://localhost:8080/fire?address=<address>
//		Cette url doit retourner la liste des habitants vivant à l’adresse donnée ainsi que le numéro de la caserne
//		de pompiers la desservant. La liste doit inclure le nom, le numéro de téléphone, l'âge et les antécédents
//		médicaux (médicaments, posologie et allergies) de chaque personne.
//	http://localhost:8080/flood/stations?stations=<a list of station_numbers>
//		Cette url doit retourner une liste de tous les foyers desservis par la caserne. Cette liste doit regrouper les
//		personnes par adresse. Elle doit aussi inclure le nom, le numéro de téléphone et l'âge des habitants, et
//		faire figurer leurs antécédents médicaux (médicaments, posologie et allergies) à côté de chaque nom.
//	http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
//		Cette url doit retourner le nom, l'adresse, l'âge, l'adresse mail et les antécédents médicaux (médicaments,
//		posologie, allergies) de chaque habitant. Si plusieurs personnes portent le même nom, elles doivent
//		toutes apparaître.

}