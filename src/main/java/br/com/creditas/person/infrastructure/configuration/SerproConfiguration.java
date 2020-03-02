package br.com.creditas.person.infrastructure.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("client.serpro")
public class SerproConfiguration {

    private String url;
    private String userToken;

}
