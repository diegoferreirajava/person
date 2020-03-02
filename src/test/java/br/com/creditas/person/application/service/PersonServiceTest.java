package br.com.creditas.person.application.service;

import br.com.creditas.person.application.handling.DuplicatedPersonException;
import br.com.creditas.person.application.handling.InvalidCPFException;
import br.com.creditas.person.application.outboundservice.SerproClient;
import br.com.creditas.person.domain.dto.PersonDto;
import br.com.creditas.person.domain.dto.SerproPersonDto;
import br.com.creditas.person.domain.dto.StatusCPF;
import br.com.creditas.person.domain.entity.Person;
import br.com.creditas.person.infrastructure.repository.jpa.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;
import java.util.UUID;

import static java.util.Collections.singletonMap;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private SerproClient serproClient;

    @InjectMocks
    private PersonService personService;

    @Test
    public void create_shouldCreateANewPersonWhenTheCPFIsRegular() {
        var situacao = singletonMap(SerproPersonDto.SITUACAO_CODIGO, StatusCPF.REGULAR.getCode());
        var serproPersonDto = new SerproPersonDto("12345678901", "Nome", situacao);
        var personDto = new PersonDto(UUID.randomUUID(), serproPersonDto.getNi(), serproPersonDto.getNome());

        var person = new Person();
        person.setCpf(personDto.getCpf());
        person.setNome(personDto.getNome());

        when(serproClient.searchCpf(personDto)).thenReturn(Optional.of(serproPersonDto));
        when(personRepository.save(person)).thenReturn(person);
        when(modelMapper.map(person, PersonDto.class)).thenReturn(personDto);
        when(modelMapper.map(personDto, Person.class)).thenReturn(person);

        PersonDto expectedPersonDto = personService.create(personDto);

        assertNotNull(expectedPersonDto);
        verify(personRepository).save(person);
        verify(serproClient).searchCpf(personDto);
        verify(modelMapper).map(person, PersonDto.class);
    }

    @Test
    public void create_shouldThrowsExceptionWhenCPFIsntRegular() {
        var situacao = singletonMap(SerproPersonDto.SITUACAO_CODIGO, StatusCPF.CANCELED.getCode());
        var serproPersonDto = new SerproPersonDto("12345678901", "Nome", situacao);
        var personDto = new PersonDto(UUID.randomUUID(), serproPersonDto.getNi(), serproPersonDto.getNome());

        when(serproClient.searchCpf(personDto)).thenReturn(Optional.of(serproPersonDto));

        assertThrows(InvalidCPFException.class, () -> personService.create(personDto));
    }

    @Test
    public void create_shouldThrowsExceptionWhenCPFAlreadyExists() {
        var situacao = singletonMap(SerproPersonDto.SITUACAO_CODIGO, StatusCPF.REGULAR.getCode());
        var serproPersonDto = new SerproPersonDto("12345678901", "Nome", situacao);
        var personDto = new PersonDto(UUID.randomUUID(), serproPersonDto.getNi(), serproPersonDto.getNome());

        var person = new Person();
        person.setCpf(personDto.getCpf());
        person.setNome(personDto.getNome());

        when(personRepository.findByCpf(person.getCpf())).thenReturn(Optional.of(person));

        assertThrows(DuplicatedPersonException.class, () -> personService.create(personDto));
    }

}