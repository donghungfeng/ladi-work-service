package com.example.ladiworkservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LadiWorkServiceApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(LadiWorkServiceApplication.class, args);
    }



    @Override
    public void run(String... args) throws Exception {
    }
}
