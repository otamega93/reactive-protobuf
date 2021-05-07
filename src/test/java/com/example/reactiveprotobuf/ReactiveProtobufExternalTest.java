package com.example.reactiveprotobuf;

import com.example.reactiveprotobuf.models.Person;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class ReactiveProtobufExternalTest {

    // This is meant to be used to call a running server. It's more like
    // a end-to-end like test
    public static void main(String[] args) {

        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/proto-persons")
                .build();

        // create person
        Person sam = Person.newBuilder()
                .setName("sam")
                .setAge(10)
                .build();

        webClient.post()
                .contentType(MediaType.parseMediaType("application/x-protobuf"))
                .bodyValue(sam)
                .retrieve()
                .bodyToMono(Void.class).block();

        // This wont work because even though we are subscribing not blocking the thread,
        // since the execution continues the test ends before the subscription receives the result.
        // That's why we're using the blocking sample
//        webClient.get()
//                .uri("/{id}", 1)
//                .retrieve()
//                .bodyToMono(Person.class).subscribe(p -> System.out.println(p.toString()));

        Person person = webClient.get()
                .uri("/{id}", 1)
                .retrieve()
                .bodyToMono(Person.class).block();

        System.out.println(person.toString());

    }
}
