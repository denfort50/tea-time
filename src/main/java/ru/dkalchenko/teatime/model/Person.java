package ru.dkalchenko.teatime.model;

import lombok.Data;

@Data
public class Person {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
