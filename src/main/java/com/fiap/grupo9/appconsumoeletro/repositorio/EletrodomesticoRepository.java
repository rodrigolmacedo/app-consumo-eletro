package com.fiap.grupo9.appconsumoeletro.repositorio;

import com.fiap.grupo9.appconsumoeletro.dominio.Eletrodomestico;
import com.fiap.grupo9.appconsumoeletro.dominio.Endereco;
import com.fiap.grupo9.appconsumoeletro.exceptions.EnderecoJaCadastradoException;
import com.fiap.grupo9.appconsumoeletro.exceptions.LimiteRepositorioException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EletrodomesticoRepository {
    private List<Eletrodomestico> eletrodomesticos = new ArrayList<>();

    public List<Eletrodomestico> getEletrodomesticos(){
        return this.eletrodomesticos;
    }

    //register new eletro

}
