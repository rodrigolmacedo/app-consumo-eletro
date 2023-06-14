package com.fiap.grupo9.appconsumoeletro.controllers.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoForm {
    @JsonProperty
    @NotBlank
    private String cep;
    private String end;
    private String numero;
    private String bairro;
    private String cidade;
    private String uf;
    private String complemento;
}
