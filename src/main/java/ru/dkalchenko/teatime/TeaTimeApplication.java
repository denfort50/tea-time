package ru.dkalchenko.teatime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.dkalchenko.teatime.model.Person;
import ru.dkalchenko.teatime.repository.PersonRepositoryMongo;

@SpringBootApplication
@EnableScheduling
public class TeaTimeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeaTimeApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(PersonRepositoryMongo repositoryMongo) {
        return args -> {
            Person person1 = new Person("Денис", "Кальченко", "denfort50@yandex.ru");
            Person person2 = new Person("Олег", "Коробкин", "korobkin@yandex.ru");
            Person person3 = new Person("Роман", "Олейкин", "oleykin50@yandex.ru");
            repositoryMongo.save(person1);
            repositoryMongo.save(person2);
            repositoryMongo.save(person3);
        };
    }
}
