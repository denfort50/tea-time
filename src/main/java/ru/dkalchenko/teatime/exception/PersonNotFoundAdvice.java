package ru.dkalchenko.teatime.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PersonNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(PersonByIdNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String personByIdNotFoundHandler(PersonByIdNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(PersonByEmailNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String personByEmailNotFoundHandler(PersonByEmailNotFoundException ex) {
        return ex.getMessage();
    }
}
