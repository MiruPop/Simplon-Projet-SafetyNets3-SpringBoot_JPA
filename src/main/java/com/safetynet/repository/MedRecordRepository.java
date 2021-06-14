package com.safetynet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.safetynet.model.MedRecord;

public interface MedRecordRepository extends JpaRepository<MedRecord, Long> {

}