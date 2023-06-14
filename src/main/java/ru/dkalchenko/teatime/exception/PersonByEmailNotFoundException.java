package ru.dkalchenko.teatime.exception;


public class PersonByEmailNotFoundException extends RuntimeException {

    public PersonByEmailNotFoundException(String email) {
        super("Could not find person with email: " + email);
    }
}
