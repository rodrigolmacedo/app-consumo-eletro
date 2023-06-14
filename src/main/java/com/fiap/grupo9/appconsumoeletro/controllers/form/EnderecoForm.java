package com.fiap.grupo9.appconsumoeletro.controllers.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoForm {
    @JsonProperty
    @NotBlank
    @Pattern(regexp = "\\d{8}", message = "CEP inválido")
    private String cep;
    @JsonProperty
    @NotBlank
    private String end;
    @JsonProperty
    private String numero;
    @JsonProperty
    @NotBlank
    private String bairro;
    @JsonProperty
    @NotBlank
    private String cidade;
    @JsonProperty
    @NotBlank
    private String uf;
    @JsonProperty
    private String complemento;
}
