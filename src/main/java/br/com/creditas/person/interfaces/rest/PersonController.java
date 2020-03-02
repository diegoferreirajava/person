package br.com.creditas.person.interfaces.rest;

import br.com.creditas.person.application.service.PersonService;
import br.com.creditas.person.domain.dto.PersonDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/persons")
public class PersonController {

    private PersonService personService;

    @PostMapping
    public ResponseEntity<PersonDto> save(@RequestBody @Valid PersonDto personDto) {
        var person = personService.create(personDto);

        return new ResponseEntity<>(person, HttpStatus.CREATED);
    }

}
