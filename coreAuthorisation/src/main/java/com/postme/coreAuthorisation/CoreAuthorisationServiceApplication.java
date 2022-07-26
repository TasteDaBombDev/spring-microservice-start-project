package com.postme.coreAuthorisation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = { "com.postme" })
@EnableEurekaClient
public class CoreAuthorisationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreAuthorisationServiceApplication.class, args);
    }
}
