package org.example.boothello;

import org.example.boothello.bean.Welcome;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
//  We create a `@SpringBootTest`, starting an actual server on a `RANDOM_PORT`
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WelcomeRouterTest {

	// Spring Boot will create a `WebTestClient` for you,
	// already configure and ready to issue requests against "localhost:RANDOM_PORT"
	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void test1GetWelcome() {
		webTestClient
			// Create a GET request to test an endpoint
			.get().uri("/welcome")
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			// and use the dedicated DSL to test assertions against the response
			.expectStatus().isOk();

	}

	@Test
	public void test2GetWelcomeWithPathVariable() {
		String from = "Jane Doe";
		webTestClient
				// Create a GET request to test an endpoint
				.get().uri(uriBuilder -> uriBuilder
						.path("/welcome/{from}").build(from))
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				// and use the dedicated DSL to test assertions against the response
				.expectStatus().isOk()
				.expectBody(Welcome.class).value(welcome -> {
					assertThat(welcome.getResponse()).isEqualTo("Welcome from Jane Doe");
				});

		webTestClient
				// Create a GET request to test an endpoint
				.get().uri("/welcome")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				// and use the dedicated DSL to test assertions against the response
				.expectStatus().isOk();
	}

	@Disabled
	public void test3GetWelcomeWithPathVariableAndQueryParam() {
		String from = "Jane Doe";
		String to = "John ValJohn";
		webTestClient
				// Create a GET request to test an endpoint
				.get().uri(uriBuilder -> uriBuilder
						.path("/welcome/{from}")
						.queryParam("to", to)
						.build(from)
				)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				// and use the dedicated DSL to test assertions against the response
				.expectStatus().isOk()
				.expectBody(Welcome.class).value(welcome -> {
					assertThat(welcome.getResponse()).isEqualTo("Welcome from Jane Doe to John ValJohn");
				});
	}

	@Disabled
	public void test4PostWelcome() {

		System.out.println("[testPostWelcome]");

		Welcome tmp = new Welcome("Welcome", "Jane Doe",
				"John ValJohn");


		webTestClient
				// Create a POST request to test an endpoint
				.post().uri("/welcome")
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(tmp), Welcome.class)
				.exchange()
				// and use the dedicated DSL to test assertions against the response
				.expectStatus().isCreated()
				.expectBody(Welcome.class).value(welcome -> {
					assertThat(welcome.getResponse()).isEqualTo("Welcome from Jane Doe to John ValJohn");
				});

		webTestClient.delete()
				.uri("/welcome/{from}", "Jane Doe")
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.NO_CONTENT);
	}

	@Disabled
	public void test5PutWelcome() {

		System.out.println("[testPutWelcome]");

		Welcome tmp = new Welcome("Welcome", "Jane NewDoe",
				"John NewJohn");


		webTestClient
				// Create a POST request to test an endpoint
				.post().uri("/welcome")
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(tmp), Welcome.class)
				.exchange()
				// and use the dedicated DSL to test assertions against the response
				.expectStatus().isCreated()
				.expectBody(Welcome.class).value(welcome -> {
					assertThat(welcome.getResponse()).isEqualTo("Welcome from Jane NewDoe to John NewJohn");
				});

		Welcome tmp2 = new Welcome("Welcome", "Jane NewDoe",
				"John ModJohn");

		webTestClient
				// Create a PUT request to test an endpoint
				.put().uri("/welcome")
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(tmp2), Welcome.class)
				.exchange()
				// and use the dedicated DSL to test assertions against the response
				.expectStatus().isCreated()
				.expectBody(Welcome.class).value(welcome -> {
					assertThat(welcome.getResponse()).isEqualTo("Welcome from Jane NewDoe to John ModJohn");
				});

		webTestClient.delete()
				.uri("/welcome/{from}", "Jane NewDoe")
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.NO_CONTENT);
	}

	@Disabled
	public void test6PatchWelcome() {

		System.out.println("[testPatchWelcome]");

		Welcome tmp = new Welcome("Welcome", "Jane",
				"John");


		webTestClient
				// Create a POST request to test an endpoint
				.post().uri("/welcome")
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(tmp), Welcome.class)
				.exchange()
				// and use the dedicated DSL to test assertions against the response
				.expectStatus().isCreated()
				.expectBody(Welcome.class).value(welcome -> {
					assertThat(welcome.getResponse()).isEqualTo("Welcome from Jane to John");
				});

		Welcome tmp2 = new Welcome("Welcome", "Jane",
				"John PatchJohn");

		webTestClient
				// Create a PATCH request to test an endpoint
				.patch().uri("/welcome/{from}", "Jane")
				.accept(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(tmp2), Welcome.class)
				.exchange()
				// and use the dedicated DSL to test assertions against the response
				.expectStatus().isCreated()
				.expectBody(Welcome.class).value(welcome -> {
					assertThat(welcome.getResponse()).isEqualTo("Welcome from Jane to John PatchJohn");
				});

		webTestClient.delete()
				.uri("/welcome/{from}", "Jane")
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.NO_CONTENT);
	}

}
