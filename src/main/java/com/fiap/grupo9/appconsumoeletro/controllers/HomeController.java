package com.fiap.grupo9.appconsumoeletro.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/teste")
    public ResponseEntity<String> teste(){
        return ResponseEntity.ok("Teste");
    }
}
