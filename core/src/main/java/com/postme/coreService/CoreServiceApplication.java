package com.postme.coreService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Configuration;

/**
 * Un nou microserviciu are @SpringBootApplication(scanBasePackages = { "com.postme" })
 * Un nou microserviciu are Clasa de WebSecurity de pe desktop
 * Un nou microserviciu importa microservicul de auth
 */
@Configuration
@EnableEurekaServer
@SpringBootApplication
public class CoreServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreServiceApplication.class, args);
    }

}
