package com.safetynet.utility;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.model.Firestation;
import com.safetynet.model.MedRecord;
import com.safetynet.model.Person;
import com.safetynet.repository.FirestationRepository;
import com.safetynet.repository.MedRecordRepository;
import com.safetynet.repository.PersonRepository;

@Component
public class JsonDataParser {
	@Value("${json.file.location}") // le chemin vers le fichier de données a été déclaré dans
	// applicatyion.properties

// l'objet File va nous donner le chemin du fichier à lire (source)
	private File file;
	private static final Logger LOGGER = LogManager.getLogger(JsonDataParser.class);

	@Autowired
	PersonRepository pr;
	@Autowired
	MedRecordRepository mrr;
	@Autowired
	FirestationRepository fsr;

// j'ai changé l'utilisation initiale de "List" en "HashSet" en m'apercevant qu'il y avait des doublons dans le data.json
	// il y a également des "erreurs de saisies": ex: l'adresse "112 Steppes Pl" est référencée par 2 casernes différentes
	public static Set<Person> listePersonnes = new HashSet<>();
	public static Set<MedRecord> listeMedRecords = new HashSet<>();
	public static Set<Firestation> listeFirestations = new HashSet<>();

	ObjectMapper mapper = new ObjectMapper();

// @EventListener permet la création des tables et leur remplissage au démarrage de l'application

	@EventListener
	public void readPersons(ApplicationReadyEvent event) {
		try {
			JsonNode jsonNode = mapper.readTree(file); // A FAIRE: trouver un moyen pour sortir en dehors des methodes
														// le mappage des données (DRY)
			String arrayString = jsonNode.get("persons").toString();

			listePersonnes = mapper.readValue(arrayString, new TypeReference<Set<Person>>() {
			});
//listePersonnes = mapper.readValue(file, DataJson.class).getPersons();
			if (pr.findAll().isEmpty()) {
				pr.saveAll(listePersonnes);
				LOGGER.info("Table personnes peuplée!");
			} else
				LOGGER.info("La table \"personnes\" était déjà peuplée!");
		} catch (IOException e) {
			LOGGER.info("FAIL TO READ personnes", e);
		}

	}

	@EventListener
	public void readMedRecords(ApplicationReadyEvent event) {
		try {
			JsonNode jsonNode = mapper.readTree(file);
			String arrayString = jsonNode.get("medicalrecords").toString();

			listeMedRecords = mapper.readValue(arrayString, new TypeReference<Set<MedRecord>>() {
			});
			System.out.println(listeMedRecords);

			if (mrr.findAll().isEmpty()) {
			mrr.saveAll(listeMedRecords);
			LOGGER.info("Liste dossiers médicaux peuplée!");
			} else
				LOGGER.info("La table \"dossiers médicaux\" était déjà peuplée!");
		} catch (IOException e) {
			LOGGER.info("FAIL TO READ dossiers médicaux", e);
		}
	}

	@EventListener
	public void readFirestations(ApplicationReadyEvent event) {
		try {
			JsonNode jsonNode = mapper.readTree(file);
			String arrayString = jsonNode.get("firestations").toString();

			listeFirestations = mapper.readValue(arrayString, new TypeReference<Set<Firestation>>() {
			});
			if (fsr.findAll().isEmpty()) {
				fsr.saveAll(listeFirestations);
				LOGGER.info("Table casernes peuplée!");
			} else
				LOGGER.info("La table \"firestation\" était déjà peuplée!");

		} catch (IOException e) {
			LOGGER.info("FAIL TO READ Liste  casernes", e);
		}
	}
}