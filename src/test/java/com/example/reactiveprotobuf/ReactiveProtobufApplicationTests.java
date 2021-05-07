package com.example.reactiveprotobuf;

import com.example.reactiveprotobuf.models.Person;
import com.example.reactiveprotobuf.models.PersonNonProto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReactiveProtobufApplicationTests {

	@Value("${local.server.port}")
	private int port;

	@Test
	void saveAndGetPersonTest() {

		String url = "http://localhost:" + port + "/proto-persons";

		WebClient webClient = WebClient.builder()
				.baseUrl(url)
				.build();

		// create person
		Person sam = Person.newBuilder()
				.setName("sam")
				.setAge(10)
				.build();

		Mono<Void> monoResult = webClient.post()
				.contentType(MediaType.parseMediaType("application/x-protobuf"))
				.bodyValue(sam)
				.retrieve()
				.bodyToMono(Void.class);

		// confirm if it saves
		StepVerifier.create(monoResult)
				.verifyComplete();

		//  retrieve and verify
		StepVerifier.create(this.getPerson(webClient,1))
				.expectNextMatches(p ->
						p.getAge() == sam.getAge() && p.getName().equals(sam.getName()))
				.verifyComplete();

	}

	private Mono<Person> getPerson(WebClient webClient, int id) {

		return webClient.get()
				.uri("/{id}", id)
				.retrieve()
				.bodyToMono(Person.class)
				.doOnNext(p -> System.out.println(p.toString()));
	}

}
