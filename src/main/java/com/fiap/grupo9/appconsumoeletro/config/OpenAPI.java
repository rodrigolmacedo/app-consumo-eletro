package com.fiap.grupo9.appconsumoeletro.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API para gerenciar o consumo eletrônico",
                description = "Visualizar o consumo de energia de cada aparelho eletrônico e usuário"
        )
)
public class OpenAPI {
}
