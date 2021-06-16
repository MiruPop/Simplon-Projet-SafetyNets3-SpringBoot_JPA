package com.safetynet.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// cette classe doit etre une join table qui lie chaque adresse avec un numéro de caserne
@Entity
public class Firestation {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="address")
	private String address;
	@Column(name="station")
	private String station;
	
	
//	@OneToMany(mappedBy="firestation")
//    private Set<Person> personnes;


	public String getAddress() {
		//faudrait peut-être ajouter Person person dans attributs
		//puis définir le getter comme return person.getAddress();
		return address;
	}

	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getStation() {
		return station;
	}


	public void setStation(String station) {
		this.station = station;
	}


	@Override
	public String toString() {
		return "Firestations [address=" + address + ", station=" + station + "]";
	}
}