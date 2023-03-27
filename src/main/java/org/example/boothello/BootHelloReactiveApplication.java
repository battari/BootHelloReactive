package org.example.boothello;

import org.example.boothello.webclient.WelcomeClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.stream.Collectors;

@SpringBootApplication
public class BootHelloReactiveApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(BootHelloReactiveApplication.class, args);
		WelcomeClient welcomeClient = context.getBean(WelcomeClient.class);
		// We need to block for the content here or the JVM might exit before the message is logged
		System.out.println(">> message = " + welcomeClient.getMessage().collect(Collectors.toList()).block());
	}
}
