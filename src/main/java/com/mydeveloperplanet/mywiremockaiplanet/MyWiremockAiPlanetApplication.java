package com.mydeveloperplanet.mywiremockaiplanet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("com.mydeveloperplanet.mywiremockaiplanet.config")
public class MyWiremockAiPlanetApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyWiremockAiPlanetApplication.class, args);
	}

}
