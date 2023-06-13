package ru.dkalchenko.teatime.exception;

import java.math.BigInteger;

public class PersonNotFoundException extends RuntimeException {

    public PersonNotFoundException(BigInteger id) {
        super("Could not find person " + id);
    }
}
