package com.fiap.grupo9.appconsumoeletro.controllers;

import com.fiap.grupo9.appconsumoeletro.controllers.form.EnderecoForm;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {

    private Validator validator;
    @PostMapping
    public ResponseEntity<?> cadastrarEndereco(@Valid @RequestBody EnderecoForm enderecoForm){
        return ResponseEntity.ok().body(enderecoForm);
    }
}
