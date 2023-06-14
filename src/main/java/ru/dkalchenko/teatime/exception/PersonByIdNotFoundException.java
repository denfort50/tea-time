package ru.dkalchenko.teatime.exception;


public class PersonByIdNotFoundException extends RuntimeException {

    public PersonByIdNotFoundException(String id) {
        super("Could not find person with id: " + id);
    }
}
