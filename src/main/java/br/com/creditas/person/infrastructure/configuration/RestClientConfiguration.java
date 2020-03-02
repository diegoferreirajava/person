package br.com.creditas.person.infrastructure.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Configuration
public class RestClientConfiguration {

    @Bean
    public RestTemplate serproRestTemplate(SerproConfiguration serproConfiguration, RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .rootUri(serproConfiguration.getUrl())
                .setConnectTimeout(Duration.ofSeconds(90))
                .setReadTimeout(Duration.ofSeconds(90))
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON.toString())
                .defaultHeader(AUTHORIZATION, String.format("Bearer %s", serproConfiguration.getUserToken()))
                .build();
    }

}
