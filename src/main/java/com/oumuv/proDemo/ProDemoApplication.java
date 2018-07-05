package com.oumuv.proDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient

public class ProDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProDemoApplication.class, args);
	}
}
