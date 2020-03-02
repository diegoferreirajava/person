package br.com.creditas.person.interfaces.rest;

import br.com.creditas.person.domain.dto.PersonDto;
import br.com.creditas.person.infrastructure.configuration.SerproConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.WireMockRestServiceServer;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestTemplate serproRestTemplate;

    @Autowired
    private SerproConfiguration serproConfiguration;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void save() throws Exception {
        var personDto = new PersonDto(null, "53141363099","Nome");
        var json = objectMapper.writeValueAsString(personDto);

        WireMockRestServiceServer
                .with(this.serproRestTemplate)
                .baseUrl(serproConfiguration.getUrl())
                .stubs("classpath:/stubs/resource.json")
                .build();

        mockMvc.perform(post("/persons")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(Matchers.containsString(personDto.getCpf())));
    }

}