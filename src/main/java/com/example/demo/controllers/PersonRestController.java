//package com.example.demo.controllers;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.demo.entities.Person;
//import com.example.demo.respository.PersonRepository;
//
//@RestController // Fichier qui mappe sur des chemins et qui va nous renvoyer
////des reponses au format JSon, XML
//
//public class PersonRestController {
//
//	@Autowired // utiliser de dépendance (une methode de la classe b utilise une methode de la
//				// classe a)
//	private PersonRepository personRepository;
//
//	// Recuperer une liste de personnes
//	@GetMapping("/persons")
//	public List<Person> getAllList() {
//		return personRepository.findAll();
//	}
//
//	// Enregister une persenne ; create est une methode ; l'annotation request body
//	// permet de recupérer le core de la methode
//	@PostMapping("/persons")
//	public Person create(@RequestBody() Person person) {
//		// Person personTosave = personRepository.save(person);
//		return personRepository.save(person);
//	}
//
//	// Variable de chemin - Utilisation d'optional-Path de recuperer la variable
//	// déclarer en argument ; chaque path variable pour un argument
//	@GetMapping("/persons/{id}")
//	public Person getById(@PathVariable long id) {
//		return personRepository.findById(id).get();
//	}
//	// Supprimer une personne
//
//// 1ere etape -> declarer signature de la methode delete ; 2eme etape -> declarer mapping
//
//	@DeleteMapping("/persons/{id}") // correct
//	public boolean delete(@PathVariable long id) {
//		personRepository.deleteById(id);
//		return true;
//	}
//
////Essai personnel
//	@PutMapping("/persons/{id}") // Mettre à jour une donnee pour l'Id
//	public Person update(@PathVariable long id) {
//		// return personRepository.getById(id);
//		return personRepository.findById(id).get();
//		// return personRepository.save(person);
//	}
//
//	// @PatchMapping // Mettre a jour une donne partiellement
//	// @PostMapping // Enregistre une donnee
//	// @DeleteMapping // Supprimer une donnee
//
//	// Une api utilise les methodes du protocole HTTP(GET, POST, PUT, PATCH, DELETE)
//
//	// hello
//	@GetMapping("/hello")
//	public String sayHello() {
//		return "hello";
//	}
//
//	@PostMapping("/hello")
//	public String sayHello(String msg) {
//		return msg;
//	}
//}

//CORRECTION

package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.entities.Person;
import com.example.demo.respository.PersonRepository;

// Fichier qui mappe sur des chemins et qui va nous renvoyer
// des reponses au format JSon, XML
@RestController
//permet la communication entre applications (Backend <=> Frontend)
@CrossOrigin("*")
public class PersonRestController {


	@Autowired // utilisee pr l'injection de dependances
	private PersonRepository personRepository;

	@GetMapping("/persons")
	public ResponseEntity<List<Person>> getAll() {
		return new ResponseEntity<List<Person>>(personRepository.findAll(), HttpStatus.OK);
	}

	@PostMapping("/persons")
	public ResponseEntity<Person> create(@RequestBody Person person) {
		return new ResponseEntity<Person>(personRepository.save(person), HttpStatus.CREATED);
	}

	@GetMapping("/persons/{id}")
	public ResponseEntity<Person> getById(@PathVariable long id) {
		return personRepository.findById(id).map((p) -> {
			return new ResponseEntity<Person>(p, HttpStatus.OK);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
				"La personne avec l'id " + id + "n'existe pas"));
	}

	@PutMapping("/persons/{id}")
	public ResponseEntity<Person> editById(@PathVariable long id, @RequestBody Person person) {
		return personRepository.findById(id).map((p) -> {
			p.setFirstName(person.getFirstName());
			p.setLastName(person.getLastName());
			p.setAge(person.getAge());
			personRepository.save(p);
			return new ResponseEntity<Person>(p, HttpStatus.OK);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
				"La personne avec l'id " + id + "n'existe pas"));
	}

	@DeleteMapping("/persons/{id}")
	public ResponseEntity<Boolean> delete(@PathVariable long id) {
		return personRepository.findById(id).map((p) -> {
			personRepository.delete(p);
			return new ResponseEntity<>(true, HttpStatus.OK);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
				"La personne avec l'id " + id + "n'existe pas"));
	}

	// @PostMapping // Enregistre une donnee
//	@DeleteMapping // Supprimer une donnee
//	@PutMapping // Mettre a jour une donnee totalement
//	@PatchMapping // Mettre a jour une donne partiellement

	// Une api utilise les methodes du protocole HTTP (GET, POST, PUT, PATCH,
	// DELETE)

	// /hello
	@GetMapping("/hello")
	public String sayHello() {
		return "hello";
	}

	@PostMapping("/hello")
	public String sayHello(String msg) {
		return msg;
	}

}
