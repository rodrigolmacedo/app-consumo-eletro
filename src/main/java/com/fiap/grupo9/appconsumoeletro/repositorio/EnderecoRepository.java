package com.fiap.grupo9.appconsumoeletro.repositorio;

import com.fiap.grupo9.appconsumoeletro.dominio.Endereco;
import com.fiap.grupo9.appconsumoeletro.exceptions.EnderecoJaCadastradoException;
import com.fiap.grupo9.appconsumoeletro.exceptions.EnderecoNaoEncontradoException;
import com.fiap.grupo9.appconsumoeletro.exceptions.LimiteRepositorioException;
import org.springframework.stereotype.Component;

import javax.naming.LimitExceededException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EnderecoRepository {
    private List<Endereco> enderecoList = new ArrayList<>();

    public List<Endereco> getEnderecoList(){
        return this.enderecoList;
    }

    public List<Endereco> getEnderecosPeloCEP(String CEP){
        return this.enderecoList.stream().filter(endereco -> endereco.getCep().equalsIgnoreCase(CEP))
                .collect(Collectors.toList());
    }

    public Endereco salvarEndereco(Endereco endereco) throws EnderecoJaCadastradoException {

        if(enderecoList.contains(endereco)){
            throw new EnderecoJaCadastradoException(String.format("Endereco com CEP %s já cadastrado!", endereco.getCep()));
        }

        if(enderecoList.size() > 100){
            throw new LimiteRepositorioException(String.format("Limite de %s atingido!", enderecoList.size()));
        }

        enderecoList.add(endereco);

        return endereco;

    }
}
