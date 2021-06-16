package com.safetynet.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity
//@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)		//AUTO laisse Hibernate chosir la meilleure stratégie de génération de l'id unique
    @Column(name="id")
    private Long id;
	
//	@OneToOne
//	@JoinColumn(name="firstName",referencedColumnName = "MedRecord_firstName")
    @Column(name="firstName")
//	@NotBlank (message = "Merci de saisir un prénom")
    private String firstName;
	
//	@OneToOne
//	@JoinColumn(name="lastName",referencedColumnName = "MedRecord_lastName")
    @Column(name= "lastName")
//    @NotBlank (message = "Merci de saisir un nom")
    private String lastName;
    
    @Column(name="address")
//    @NotBlank (message = "Merci de saisir une adresse")
    private String address;
    
    @Column(name="city")
    private String city;
    @Column(name="zip")
    private String zip;
    @Column(name="phone")
    private String phone;
    @Column(name="email")
    private String email;
    
//    @ManyToOne
//    private Firestation firestation;
//    @OneToOne
//    private MedRecord medRecord;
    
// la norme JPA requiert un constructeur vide pour les entités
	public Person() {
	}

//	public Firestation getFirestation() {
//		return firestation;
//	}
//
//	public void setFirestation(Firestation firestation) {
//		this.firestation = firestation;
//	}


	/*
	 * avec Hibernate, l'existence de setId n'est en fait pas nécessaire, mais la norme JPA réclame
	 * des accésseurs pour chaque attribut, alors on met la méthode en "private" pour un id auto-généré,
	 * pour éviter les conflits
	 * Ici on le met en "public", comme dans l'exemple
	*/
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", address=" + address
				+ ", city=" + city + ", zip=" + zip + ", phone=" + phone + ", email=" + email + "]";
	}
	

}