package com.example.reactiveprotobuf.controllers;

import com.example.reactiveprotobuf.models.Person;
import com.example.reactiveprotobuf.models.PersonNonProto;
import com.example.reactiveprotobuf.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private PersonService personService;

    @Autowired
    public PersonController(final PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<PersonNonProto> getPerson(@PathVariable int id){
        return this.personService.getPerson(id);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Void> createPerson(@RequestBody Mono<PersonNonProto> person) {

        return this.personService.addPerson(person);
    }
}
