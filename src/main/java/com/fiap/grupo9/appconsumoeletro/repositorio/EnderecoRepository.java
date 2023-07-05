package com.fiap.grupo9.appconsumoeletro.repositorio;

import com.fiap.grupo9.appconsumoeletro.dominio.Endereco;
import com.fiap.grupo9.appconsumoeletro.exceptions.EnderecoJaCadastradoException;
import com.fiap.grupo9.appconsumoeletro.exceptions.EnderecoNaoEncontradoException;
import com.fiap.grupo9.appconsumoeletro.exceptions.LimiteRepositorioException;
import org.springframework.stereotype.Component;

import javax.naming.LimitExceededException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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

        endereco.setUuid(UUID.randomUUID());
        enderecoList.add(endereco);

        return endereco;

    }

    public void atualizarEndereco(UUID uuid, Endereco endereco) {
        Endereco enderecoEncontrado = enderecoList.stream().filter(enderecoItem -> enderecoItem.getUuid().equals(uuid))
                .findFirst()
                .orElseThrow(() -> new EnderecoNaoEncontradoException(String.format("Endereço com uuid:%s não encontrado.", uuid)));

        enderecoEncontrado.setComplemento(endereco.getComplemento());
        enderecoEncontrado.setNumero(endereco.getNumero());
        enderecoList.set(enderecoList.indexOf(enderecoEncontrado), enderecoEncontrado);
    }

    public void removerEndereco(UUID uuid){
        Endereco enderecoEncontrado = enderecoList.stream().filter(enderecoItem -> enderecoItem.getUuid().equals(uuid))
                .findFirst()
                .orElseThrow(() -> new EnderecoNaoEncontradoException(String.format("Endereço com uuid:%s não encontrado.", uuid)));

        enderecoList.remove(enderecoEncontrado);
    }
}
