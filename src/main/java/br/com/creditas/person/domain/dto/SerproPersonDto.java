package br.com.creditas.person.domain.dto;

import lombok.Value;

import java.util.Map;

@Value
public class SerproPersonDto {

    public static final String SITUACAO_CODIGO = "codigo";

    private String ni;
    private String nome;
    private Map<String, String> situacao;

    public boolean isRegular() {
        return StatusCPF.REGULAR.getCode().equals(situacao.get(SITUACAO_CODIGO));
    }

}
