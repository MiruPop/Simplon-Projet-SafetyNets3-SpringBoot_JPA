package com.safetynet.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
//@JsonIgnoreProperties(ignoreUnknown = true)		//permet d'ignorer les champs de data.json qui ne correspondent pas aux attributs du bean
public class MedRecord {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
	private Long id;

//	@Column(name="person_id", FK.. bdd Person

			
//	@OneToOne(mappedBy="firstName")
//	@JoinColumn(name="firstName")
	@Column(name="firstName")
	@NotBlank (message = "Merci de saisir un prénom")
	private String firstName;
	
//	@OneToOne(mappedBy="lastName")
//	@JoinColumn(name="lastName")
	@Column(name="lastName")
	@NotBlank (message = "Merci de saisir un nom")
	private String lastName;
	
	
	@Column(name="birthDate")
	@NotBlank
	private String birthdate;
	
	//alternative: @OneToMany + @Embedded; surtout intéressant si c'était une List d'objets, avec plusieurs attributs
	@ElementCollection
	private List<String> medications;
	
	@ElementCollection
	private List<String> allergies;

	public MedRecord() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public List<String> getMedications() {
		return medications;
	}

	public void setMedications(List<String> medications) {
		this.medications = medications;
	}

	public List<String> getAllergies() {
		return allergies;
	}

	public void setAllergies(List<String> allergies) {
		this.allergies = allergies;
	}

	@Override
	public String toString() {
		return "MedRecord [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", birthdate="
				+ birthdate + ", medications=" + medications + ", allergies=" + allergies + "]";
	}
}