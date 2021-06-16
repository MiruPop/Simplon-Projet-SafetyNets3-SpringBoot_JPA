package com.safetynet.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.safetynet.model.Person;
import com.safetynet.repository.PersonRepository;


@Controller
public class PersonController {
	
	@Autowired
	private PersonRepository personRepository;



//	READ
	   
	@GetMapping ("/person")
	public String listePersonnes(Model model) {
		model.addAttribute("listePersonnes", personRepository.findAll());
		return "persons";		//"persons" c'est le nom du fichier html
	}
	
//	CREATE
    @GetMapping("/newPerson")
    public String showSignUpForm(Person p) {
        return "registerPerson";
    }
    @PostMapping("/addPerson")
    public String ajouterPersonne(@Valid Person person, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "registerPerson";
        }
        personRepository.save(person);
        return "redirect:/person";
    }
	
//	UPDATE
    @GetMapping("/editPerson/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Person person = personRepository.findById(id)
          .orElseThrow(() -> new IllegalArgumentException("L'id recherché n'existe pas: " + id));
        
        model.addAttribute("person", person);
        return "fichePersonne";
    }
    @PostMapping("/updatePerson/{id}")
    public String modifierPersonne(@PathVariable("id") Long id, @Valid Person p, 
      BindingResult result, Model model) {
        if (result.hasErrors()) {
            p.setId(id);
            return "fichePersonne";
        }
        personRepository.save(p);
        return "redirect:/person";
    }
	
//	DELETE
	@GetMapping("/deletePerson/{id}")
	public String deleteUser(@PathVariable("id") Long id, Model model) {
	    Person person = personRepository.findById(id)
	      .orElseThrow(() -> new IllegalArgumentException("L'id recherché n'existe pas : " + id));
	    personRepository.delete(person);
	    return "redirect:/person";
	}
}