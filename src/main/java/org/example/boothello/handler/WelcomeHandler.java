package org.example.boothello.handler;

import org.example.boothello.bean.Welcome;
import org.example.boothello.service.WelcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class WelcomeHandler {

	@Autowired
	WelcomeService welcomeService;

	public Mono<ServerResponse> welcome(ServerRequest request) {
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
			.body(BodyInserters.fromValue(new Welcome("Welcome")));
	}

	public Mono<ServerResponse> getAll(ServerRequest request) {

		Flux<Welcome> welcomes = welcomeService.findAll();
		Mono<ServerResponse> sRM = ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(welcomes, Welcome.class);
		return sRM;
	}

	public Mono<ServerResponse> welcomeFromTo(ServerRequest request) {
		String from = request.pathVariable("from");
		Optional<String> to = request.queryParam("to");
		if (to.isPresent()) {
			return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
					.body(BodyInserters.fromValue(new Welcome("Welcome", from,
							to.get())));
		} else {
			return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
					.body(BodyInserters.fromValue(new Welcome("Welcome", from)));
		}

	}

	public Mono<ServerResponse> create(ServerRequest request) {
		Mono<Welcome> welcomeMono = request.bodyToMono(Welcome.class);

		return welcomeMono
				.flatMap(w -> ServerResponse
						.status(HttpStatus.CREATED)
						.contentType(MediaType.APPLICATION_JSON)
						.body(welcomeService.create(w), Welcome.class)
				);
	}

	public Mono<ServerResponse> delete(ServerRequest request) {
		String from = request.pathVariable("from");
		System.out.println("[delete] from: " + from);

		return Mono.just(welcomeService.delete(from))
				.flatMap(w -> {
					return ServerResponse.noContent().build();
				});
	}

	public Mono<ServerResponse> saveUpdate(ServerRequest request) {
		Mono<Welcome> welcomeMono = request.bodyToMono(Welcome.class);

		return welcomeMono
				.flatMap(w -> ServerResponse
						.status(HttpStatus.CREATED)
						.contentType(MediaType.APPLICATION_JSON)
						.body(welcomeService.saveUpdate(w), Welcome.class)
				);
	}

	public Mono<ServerResponse> update(ServerRequest request) {
		String from = request.pathVariable("from");
		System.out.println("[update] from: " + from);

		Mono<Welcome> welcomeMono = request.bodyToMono(Welcome.class);

		return welcomeMono
				.flatMap(w -> ServerResponse
						.status(HttpStatus.CREATED)
						.contentType(MediaType.APPLICATION_JSON)
						.body(welcomeService.update(from, w), Welcome.class)
				);
	}



}
