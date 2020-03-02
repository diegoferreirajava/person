package br.com.creditas.person.domain.dto;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SerproPersonDtoTest {

    @Test
    void isRegular_shouldReturnTrueWhenCodigoStatusIsRegular() {
        var situacao = Map.of(SerproPersonDto.SITUACAO_CODIGO, StatusCPF.REGULAR.getCode());
        var personDto = new SerproPersonDto("123456", "Teste", situacao);

        assertTrue(personDto.isRegular());
    }

    @Test
    void isRegular_shouldReturnFalseWhenCodigoStatusIsntRegular() {
        var situacao = Map.of(SerproPersonDto.SITUACAO_CODIGO, StatusCPF.CANCELED.getCode());
        var personDto = new SerproPersonDto("123456", "Teste", situacao);

        assertFalse(personDto.isRegular());
    }

}