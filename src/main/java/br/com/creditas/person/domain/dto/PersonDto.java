package br.com.creditas.person.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {

    private UUID id;

    @Pattern(regexp = "[\\d+]{11}")
    private String cpf;

    @NotBlank
    private String nome;

}
