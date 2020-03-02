package br.com.creditas.person.application.outboundservice;

import br.com.creditas.person.domain.dto.PersonDto;
import br.com.creditas.person.domain.dto.SerproPersonDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SerproClient {

    private RestTemplate serproRestTemplate;

    public ResponseEntity<SerproPersonDto> searchCpf(String cpf) {
        return serproRestTemplate.getForEntity("/cpf/{cpf}", SerproPersonDto.class, cpf);
    }

    public Optional<SerproPersonDto> searchCpf(PersonDto personDto) {
        var responseEntity = searchCpf(personDto.getCpf());

        return Optional.ofNullable(responseEntity.getBody());
    }

}
