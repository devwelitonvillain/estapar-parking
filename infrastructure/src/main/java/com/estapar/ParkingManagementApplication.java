package com.estapar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.estapar")
public class ParkingManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkingManagementApplication.class, args);
    }

}
