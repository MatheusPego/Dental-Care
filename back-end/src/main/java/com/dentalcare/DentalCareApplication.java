package com.dentalcare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DentalCareApplication {

    public static void main(String[] args) {
        SpringApplication.run(DentalCareApplication.class, args);

        var encoder = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
        System.out.println("HASH PARA 123456: " + encoder.encode("123456"));
    }
}