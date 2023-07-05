package com.fiap.grupo9.appconsumoeletro.controllers;

import com.fiap.grupo9.appconsumoeletro.controllers.form.EnderecoForm;
import com.fiap.grupo9.appconsumoeletro.controllers.mapper.ModelMapper;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Tag(name = "Endereço", description = "Gerenciar endereços")
@RestController
@RequestMapping("/endereco")
public class EnderecoController {

    private final Validator validator;
    private final EnderecoRepository enderecoRepository;
    private final ModelMapper modelMapper;

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

        var endereco = modelMapper.getMapper().map(enderecoForm, Endereco.class);

        endereco = enderecoRepository.salvarEndereco(endereco);

        return ResponseEntity.ok().body(endereco);
    }
    @Operation(summary = "Encontrar endereços por CEP", description = "Encontrar endereços por CEP", tags = { "Endereço" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @GetMapping("/{cep}")
    public ResponseEntity<?> encontrarEnderecosPorCEP(@Valid @NotBlank @PathVariable("cep") String cep){
        var enderecoPelosCEP = enderecoRepository.getEnderecosPeloCEP(cep);
        return ResponseEntity.ok().body(enderecoPelosCEP);
    }
    @Operation(summary = "Listar endereços", description = "Listar endereços", tags = { "Endereço" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso", content = @Content),
    })
    @GetMapping
    public ResponseEntity<?> enderecos(){
        var listaEnderecos = enderecoRepository.getEnderecoList();
        return ResponseEntity.ok().body(listaEnderecos);
    }

    @Operation(summary = "Atualizar endereço", description = "Atualizar endereço", tags = { "Endereço" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso", content = @Content),
    })
    @PatchMapping("/{uuid}")
    public ResponseEntity<?> atualizarEndereco(@Valid @NotBlank @PathVariable("uuid") String uuid,
                                               @Valid @NotBlank @RequestParam String complemento,
                                               @Valid @NotBlank @RequestParam String numero){
        EnderecoForm enderecoForm = new EnderecoForm();
        enderecoForm.setComplemento(complemento);
        enderecoForm.setNumero(numero);
        var endereco = modelMapper.getMapper().map(enderecoForm, Endereco.class);
        enderecoRepository.atualizarEndereco(UUID.fromString(uuid), endereco);
        return ResponseEntity.ok().build();
    }
    @Operation(summary = "Remover endereço", description = "Remover endereço", tags = { "Endereço" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso", content = @Content),
    })
    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> removerEndereco(@Valid @NotBlank @PathVariable("uuid") String uuid){
        enderecoRepository.removerEndereco(UUID.fromString(uuid));
        return ResponseEntity.ok().build();
    }

    private <T> Map<Path, String> validar(T form) {
        Set<ConstraintViolation<T>> violacoes = validator.validate(form);
        return violacoes.stream().collect(Collectors.toMap(
                ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage
        ));
    }
}
