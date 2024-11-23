package com.scalableservices.deliveryagentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class DeliveryAgentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DeliveryAgentServiceApplication.class, args);
    }
}