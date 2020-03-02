package br.com.creditas.person.application.handling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicatedPersonException extends RuntimeException {

    public DuplicatedPersonException() {
        super("Person Already Exists");
    }

}
