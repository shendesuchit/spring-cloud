package com.saama.microservices.limitsservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class LimitsServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(LimitsServicesApplication.class, args);
	}

}
