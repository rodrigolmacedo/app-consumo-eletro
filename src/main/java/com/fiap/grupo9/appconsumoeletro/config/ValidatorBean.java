package com.fiap.grupo9.appconsumoeletro.config;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidatorBean {
    Validator validator(){
        return Validation.buildDefaultValidatorFactory().getValidator();
    }
}
