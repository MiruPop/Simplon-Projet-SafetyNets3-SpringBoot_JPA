package com.safetynet.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.safetynet.model.MedRecord;
import com.safetynet.repository.MedRecordRepository;

@Controller
public class MedrecordController {

	@Autowired
	private MedRecordRepository medRecordRepository;

	
//	READ
	   
	@GetMapping ("/medicalrecord")
	public String listeDossiers(Model model) {
		model.addAttribute("listeDossiers", medRecordRepository.findAll());
		return "medicalrecords";
	}
	
//	CREATE
    @GetMapping("/newMedrecord")
    public String showSignUpForm(MedRecord mr) {
        return "registerMedrecord";
    }
    @PostMapping("/addMedrecord")
    public String ajouterDossier(@Valid MedRecord mr, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "registerMedrecord";
        }
        medRecordRepository.save(mr);
        return "redirect:/medicalrecord";
    }
	
//	UPDATE
    @GetMapping("/editMedrecord/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
    	MedRecord mr = medRecordRepository.findById(id)
          .orElseThrow(() -> new IllegalArgumentException("L'id recherché n'existe pas: " + id));
        
        model.addAttribute("medRecord", mr);
        return "ficheDossier";
    }
    @PostMapping("/updateMedrecord/{id}")
    public String modifierDossier(@PathVariable("id") Long id, @Valid MedRecord mr, 
      BindingResult result, Model model) {
        if (result.hasErrors()) {
            mr.setId(id);
            return "ficheDossier";
        }
        medRecordRepository.save(mr);
        return "redirect:/medicalrecord";
    }
	
//	DELETE
	@GetMapping("/deleteMedrecord/{id}")
	public String deleteFile(@PathVariable("id") Long id, Model model) {
		MedRecord mr = medRecordRepository.findById(id)
	      .orElseThrow(() -> new IllegalArgumentException("L'id recherché n'existe pas : " + id));
		medRecordRepository.delete(mr);
	    return "redirect:/medicalrecord";
	}
}