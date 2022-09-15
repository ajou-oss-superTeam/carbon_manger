package com.oss.carbonadministrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CarbonAdministratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarbonAdministratorApplication.class, args);
    }
}
