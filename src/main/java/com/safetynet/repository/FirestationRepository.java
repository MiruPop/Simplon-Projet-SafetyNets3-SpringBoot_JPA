package com.safetynet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.safetynet.model.Firestation;

public interface FirestationRepository extends JpaRepository<Firestation, Integer> {
	Firestation getById(Long id);
}