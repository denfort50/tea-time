package ru.dkalchenko.teatime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.dkalchenko.teatime.model.Person;
import ru.dkalchenko.teatime.service.PersonService;

@SpringBootApplication
@EnableScheduling
public class TeaTimeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeaTimeApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(PersonService personService) {
        return args -> {
            Person person1 = new Person("Денис", "Кальченко", "denfort50@yandex.ru");
            Person person2 = new Person("Олег", "Коробкин", "korobkin@yandex.ru");
            Person person3 = new Person("Роман", "Олейкин", "oleykin50@yandex.ru");
            if (personService.findByEmail(person1.getEmail()).isEmpty()) {
                personService.save(person1);
            }
            if (personService.findByEmail(person2.getEmail()).isEmpty()) {
                personService.save(person2);
            }
            if (personService.findByEmail(person3.getEmail()).isEmpty()) {
                personService.save(person3);
            }
        };
    }
}
