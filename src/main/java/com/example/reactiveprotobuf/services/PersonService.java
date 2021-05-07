package com.example.reactiveprotobuf.services;

import com.example.reactiveprotobuf.models.Person;
import com.example.reactiveprotobuf.models.PersonNonProto;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PersonService {

    private final AtomicInteger atomicInteger = new AtomicInteger(1);

    private ConcurrentMap<Integer, PersonNonProto> personMap = new ConcurrentHashMap<>();

    public Mono<Void> addPerson(Mono<PersonNonProto> person) {

        return person.map( p -> {

            this.personMap.put(atomicInteger.getAndIncrement(), p);
            return p;
        }).then();

    }

    public Mono<PersonNonProto> getPerson(int id) {

        return Mono.just(this.personMap.get(id));
    }
}
