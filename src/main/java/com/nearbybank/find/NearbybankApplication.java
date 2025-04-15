package com.nearbybank.find;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.nearbybank")
public class NearbybankApplication {

    public static void main(String[] args) {
        SpringApplication.run(NearbybankApplication.class, args);
    }
}
