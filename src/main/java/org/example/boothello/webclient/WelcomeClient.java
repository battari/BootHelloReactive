package org.example.boothello.webclient;

import org.example.boothello.bean.Welcome;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


// Usually WebClient handled from other Springboot apps
@Component
public class WelcomeClient {

	private final WebClient client;

	public WelcomeClient(WebClient.Builder builder) {
		this.client = builder.baseUrl("http://localhost:8080").build();
	}

	public Flux<Welcome> getMessage() {
		Welcome tmp2 = new Welcome("Welcome", "Jane NewDoe",
				"John ValJohn");

		client.post().uri("/welcome")
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(tmp2), Welcome.class)
				.retrieve()
				.bodyToMono(Welcome.class).block();
		return this.client.get().uri("/welcome").accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToFlux(Welcome.class);
	}

}
