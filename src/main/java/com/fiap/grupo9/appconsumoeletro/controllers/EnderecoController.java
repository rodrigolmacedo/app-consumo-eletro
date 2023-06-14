package com.fiap.grupo9.appconsumoeletro.controllers;

import com.fiap.grupo9.appconsumoeletro.controllers.form.EnderecoForm;
import com.fiap.grupo9.appconsumoeletro.controllers.mapper.EnderecoMapper;
import com.fiap.grupo9.appconsumoeletro.dominio.Endereco;
import com.fiap.grupo9.appconsumoeletro.repositorio.EnderecoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.*;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Tag(name = "Endereço", description = "Gerenciar endereços")
@RestController
@RequestMapping("/endereco")
public class EnderecoController {

    private final Validator validator;
    private final EnderecoRepository enderecoRepository;
    private final EnderecoMapper enderecoMapper;

    @Operation(summary = "Cadastrar novo endereço", description = "Cadastrar novo endereço", tags = { "Endereço" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> cadastrarEndereco(@Valid @RequestBody EnderecoForm enderecoForm){
        Map<Path, String> violacoes = validar(enderecoForm);

        if(!violacoes.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(violacoes);

        Endereco endereco = enderecoMapper.getMapper().map(enderecoForm, Endereco.class);

        endereco = enderecoRepository.salvarEndereco(endereco);

        return ResponseEntity.ok().body(endereco);
    }
    @Operation(summary = "Encontrar endereço por CEP", description = "Encontrar endereço por CEP", tags = { "Endereço" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @GetMapping("/{cep}")
    public ResponseEntity<?> encontrarEnderecoPorCEP(@Valid @NotBlank @PathVariable("cep") String cep){

        List<Endereco> enderecoPeloCEP = enderecoRepository.getEnderecosPeloCEP(cep);

        return ResponseEntity.ok().body(enderecoPeloCEP);
    }

    private <T> Map<Path, String> validar(T form) {
        Set<ConstraintViolation<T>> violacoes = Validation.buildDefaultValidatorFactory().getValidator().validate(form);
        return violacoes.stream().collect(Collectors.toMap(
                ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage
        ));
    }
}
