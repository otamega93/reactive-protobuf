package com.example.reactiveprotobuf.services;

import com.example.reactiveprotobuf.models.Person;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ProtoPersonService {

    private final AtomicInteger atomicInteger = new AtomicInteger(1);

    private ConcurrentMap<Integer, Person> personMap = new ConcurrentHashMap<>();

    public Mono<Void> addPerson(Mono<Person> person) {

        return person.map( p -> {

            this.personMap.put(atomicInteger.getAndIncrement(), p);
            return p;
        }).then();
    }

    public Mono<Person> getPerson(int id) {

        return Mono.just(this.personMap.get(id));
    }
}
