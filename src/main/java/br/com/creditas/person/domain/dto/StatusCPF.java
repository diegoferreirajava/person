package br.com.creditas.person.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusCPF {
    
    REGULAR("0", "Regular"),
    CANCELED("9", "Cancelada de Oficio");

    private String code;
    private String description;

}
