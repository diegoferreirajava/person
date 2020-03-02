package br.com.creditas.person.application.handling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCPFException extends RuntimeException {

    public InvalidCPFException() {
        super("Invalid CPF");
    }

}
