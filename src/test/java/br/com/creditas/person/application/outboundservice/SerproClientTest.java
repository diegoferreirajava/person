package br.com.creditas.person.application.outboundservice;

import br.com.creditas.person.domain.dto.PersonDto;
import br.com.creditas.person.domain.dto.SerproPersonDto;
import br.com.creditas.person.domain.dto.StatusCPF;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static java.util.Collections.singletonMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SerproClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private SerproClient serproClient;

    @Test
    public void searchCpf_shouldReturnThePerson() {
        var situacao = singletonMap(SerproPersonDto.SITUACAO_CODIGO, StatusCPF.REGULAR.getCode());
        var personDto = new PersonDto(UUID.randomUUID(), "12345678901", "Nome");
        var serproDtoResponse = ResponseEntity.ok(new SerproPersonDto(personDto.getCpf(), personDto.getNome(), situacao));

        when(serproClient.searchCpf(personDto.getCpf())).thenReturn(serproDtoResponse);

        var serproPersonDto = serproClient.searchCpf(personDto);

        assertNotNull(serproPersonDto);
        assertTrue(serproPersonDto.isPresent());
        assertEquals(personDto.getCpf(), serproPersonDto.get().getNi());
    }

}