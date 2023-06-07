package ru.dkalchenko.teatime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TeaTimeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeaTimeApplication.class, args);
    }
}
