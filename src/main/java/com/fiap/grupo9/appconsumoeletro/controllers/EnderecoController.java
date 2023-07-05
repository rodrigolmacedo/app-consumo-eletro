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
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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
    public ResponseEntity<?> cadastrarEndereco(@Valid @RequestBody EnderecoForm enderecoForm) {
        List<String> errors = validar(enderecoForm);

        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }

        Endereco endereco = modelMapper.getMapper().map(enderecoForm, Endereco.class);
        endereco = enderecoRepository.salvarEndereco(endereco);

        return ResponseEntity.ok().body(endereco);
    }

    @Operation(summary = "Encontrar endereços por CEP", description = "Encontrar endereços por CEP", tags = { "Endereço" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @GetMapping("/{cep}")
    public ResponseEntity<?> encontrarEnderecosPorCEP(@Valid @NotBlank @PathVariable("cep") String cep) {
        List<Endereco> enderecoPelosCEP = enderecoRepository.getEnderecosPeloCEP(cep);
        return ResponseEntity.ok().body(enderecoPelosCEP);
    }

    @Operation(summary = "Listar endereços", description = "Listar endereços", tags = { "Endereço" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso", content = @Content),
    })
    @GetMapping
    public ResponseEntity<List<Endereco>> enderecos() {
        List<Endereco> listaEnderecos = enderecoRepository.getEnderecoList();
        return ResponseEntity.ok().body(listaEnderecos);
    }

    @Operation(summary = "Atualizar endereço", description = "Atualizar endereço", tags = { "Endereço" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso", content = @Content),
    })
    @PatchMapping("/{uuid}")
    public ResponseEntity<?> atualizarEndereco(@Valid @NotBlank @PathVariable("uuid") UUID uuid,
                                                                 @NotBlank @RequestParam String complemento,
                                                                 @NotBlank @RequestParam String numero) {
        EnderecoForm enderecoForm = new EnderecoForm();
        enderecoForm.setComplemento(complemento);
        enderecoForm.setNumero(numero);
        Endereco endereco = modelMapper.getMapper().map(enderecoForm, Endereco.class);
        enderecoRepository.atualizarEndereco(uuid, endereco);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Endereço atualizado com sucesso");

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Remover endereço", description = "Remover endereço", tags = { "Endereço" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso", content = @Content),
    })
    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> removerEndereco(@Valid @NotBlank @PathVariable("uuid") UUID uuid) {
        enderecoRepository.removerEndereco(uuid);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Endereço removido com sucesso");

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Buscar endereço por UUID", description = "Buscar endereço por UUID", tags = { "Endereço" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado", content = @Content)
    })
    @GetMapping("/buscar/{uuid}")
    public ResponseEntity<?> buscarEnderecoPorUUID(@Valid @PathVariable("uuid") UUID uuid) {
        Optional<Endereco> enderecoOptional = enderecoRepository.buscarEnderecoPorUUID(uuid);

        if (enderecoOptional.isPresent()) {
            Endereco endereco = enderecoOptional.get();
            return ResponseEntity.ok().body(endereco);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private <T> List<String> validar(T form) {
        Set<ConstraintViolation<T>> violations = validator.validate(form);
        return violations.stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.toList());
    }
}
