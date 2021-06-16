package com.safetynet.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.safetynet.model.Firestation;
import com.safetynet.repository.FirestationRepository;

@Controller
public class FirestationController {

	@Autowired
	private FirestationRepository firestationRepository;
	

//	READ
	   
	@GetMapping ("/firestation")
	public String listeCasernes(Model model) {
		model.addAttribute("listeCasernes", firestationRepository.findAll());
		return "firestations";
	}
	
//	CREATE
    @GetMapping("/newFirestation")
    public String showSignUpForm(Firestation f) {
        return "registerFirestation";
    }
    @PostMapping("/addFirestation")
    public String ajouterCaserne(@Valid Firestation firestation, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "registerFirestation";
        }
        firestationRepository.save(firestation);
        return "redirect:/firestation";
    }
	
//	UPDATE
    @GetMapping("/editFirestation/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
    	Firestation firestation = firestationRepository.findById(id)
          .orElseThrow(() -> new IllegalArgumentException("L'id recherché n'existe pas: " + id));
        
        model.addAttribute("firestation", firestation);
        return "ficheCaserne";
    }
    @PostMapping("/updateFirestation/{id}")
    public String modifierCaserne(@PathVariable("id") long id, @Valid Firestation f, 
      BindingResult result, Model model) {
        if (result.hasErrors()) {
            f.setId(id);
            return "ficheCaserne";
        }
        firestationRepository.save(f);
        return "redirect:/firestation";
    }
	
//	DELETE
	@GetMapping("/deleteFirestation/{id}")
	public String deleteStation(@PathVariable("id") Long id, Model model) {
		Firestation firestation = firestationRepository.findById(id)
	      .orElseThrow(() -> new IllegalArgumentException("L'id recherché n'existe pas : " + id));
		firestationRepository.delete(firestation);
	    return "redirect:/firestation";
	}
}