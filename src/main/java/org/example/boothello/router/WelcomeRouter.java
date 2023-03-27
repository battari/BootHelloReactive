package org.example.boothello.router;

import org.example.boothello.handler.WelcomeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration(proxyBeanMethods = false)
public class WelcomeRouter {

	@Bean
	public RouterFunction<ServerResponse> route(WelcomeHandler welcomeHandler) {

		return RouterFunctions
			.route(GET("/welcome").and(accept(MediaType.APPLICATION_JSON)), welcomeHandler::getAll)
			.andRoute(GET("/welcome/{from}").and(accept(MediaType.APPLICATION_JSON)), welcomeHandler::welcomeFromTo)
			.andRoute(POST("/welcome").and(accept(MediaType.APPLICATION_JSON)), welcomeHandler::create)
			.andRoute(DELETE("/welcome/{from}"), welcomeHandler::delete)
			.andRoute(PUT("/welcome"), welcomeHandler::saveUpdate)
			.andRoute(PATCH("/welcome/{from}"), welcomeHandler::update)
			;
	}
}
