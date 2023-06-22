package com.fiap.grupo9.appconsumoeletro.controllers;

import com.fiap.grupo9.appconsumoeletro.controllers.form.PessoaForm;
import com.fiap.grupo9.appconsumoeletro.controllers.mapper.ModelMapper;
import com.fiap.grupo9.appconsumoeletro.dominio.Pessoa;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Tag(name = "Pessoa", description = "Gerenciar pessoas")
@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    private final Validator validator;
    private final ModelMapper modelMapper;

    @Operation(summary = "Cadastrar nova pessoa", description = "Cadastrar nova pessoa", tags = { "Pessoa" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> cadastrarPessoa(@Valid @RequestBody PessoaForm pessoaForm){
        Map<Path, String> violacoes = validar(pessoaForm);

        if(!violacoes.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(violacoes);

        var pessoa = modelMapper.getMapper().map(pessoaForm, Pessoa.class);

        return ResponseEntity.ok().body(pessoa);
    }

    private <T> Map<Path, String> validar(T form) {
        Set<ConstraintViolation<T>> violacoes = validator.validate(form);
        return violacoes.stream().collect(Collectors.toMap(
                ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage
        ));
    }
}
