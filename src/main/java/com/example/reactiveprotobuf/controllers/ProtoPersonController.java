package com.example.reactiveprotobuf.controllers;

import com.example.reactiveprotobuf.models.Person;
import com.example.reactiveprotobuf.services.ProtoPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/proto-persons")
public class ProtoPersonController {

    private ProtoPersonService personService;

    @Autowired
    public ProtoPersonController(final ProtoPersonService personService) {
        this.personService = personService;
    }

    @GetMapping(value = "/{id}", produces = "application/x-protobuf")
    public Mono<Person> getPerson(@PathVariable int id){
        return this.personService.getPerson(id);
    }

    @PostMapping(value = "")
    public Mono<Void> createPerson(@RequestBody Mono<Person> person) {

        Mono<Void> personResult = this.personService.addPerson(person);
        return personResult;
    }
}
