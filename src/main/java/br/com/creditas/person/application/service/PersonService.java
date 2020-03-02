package br.com.creditas.person.application.service;

import br.com.creditas.person.application.handling.DuplicatedPersonException;
import br.com.creditas.person.application.handling.InvalidCPFException;
import br.com.creditas.person.application.outboundservice.SerproClient;
import br.com.creditas.person.domain.dto.PersonDto;
import br.com.creditas.person.domain.dto.SerproPersonDto;
import br.com.creditas.person.domain.entity.Person;
import br.com.creditas.person.infrastructure.repository.jpa.PersonRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class PersonService {

    private ModelMapper modelMapper;
    private PersonRepository personRepository;
    private SerproClient serproClient;

    @Transactional(propagation = Propagation.REQUIRED)
    public PersonDto create(PersonDto personDto) {
        personRepository.findByCpf(personDto.getCpf()).ifPresent(person -> { throw new DuplicatedPersonException(); });

        serproClient.searchCpf(personDto).filter(SerproPersonDto::isRegular).orElseThrow(InvalidCPFException::new);

        var savedPerson = personRepository.save(modelMapper.map(personDto, Person.class));

        return modelMapper.map(savedPerson, PersonDto.class);
    }

}
