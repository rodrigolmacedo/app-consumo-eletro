package com.fiap.grupo9.appconsumoeletro.repositorio;

import com.fiap.grupo9.appconsumoeletro.dominio.Endereco;
import com.fiap.grupo9.appconsumoeletro.exceptions.EnderecoJaCadastradoException;
import com.fiap.grupo9.appconsumoeletro.exceptions.EnderecoNaoEncontradoException;
import com.fiap.grupo9.appconsumoeletro.exceptions.LimiteRepositorioException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Component
public class EnderecoRepository {
    private final ConcurrentMap<UUID, Endereco> enderecoMap = new ConcurrentHashMap<>();

    public List<Endereco> getEnderecoList() {
        return List.copyOf(enderecoMap.values());
    }

    public List<Endereco> getEnderecosPeloCEP(String CEP) {
        return enderecoMap.values().stream()
                .filter(endereco -> endereco.getCep().equalsIgnoreCase(CEP))
                .collect(Collectors.toList());
    }

    public Endereco salvarEndereco(Endereco endereco) throws EnderecoJaCadastradoException {
        if (enderecoMap.containsValue(endereco)) {
            throw new EnderecoJaCadastradoException(String.format("Endereco com CEP %s já cadastrado!", endereco.getCep()));
        }

        if (enderecoMap.size() > 100) {
            throw new LimiteRepositorioException(String.format("Limite de %s atingido!", enderecoMap.size()));
        }

        endereco.setUuid(UUID.randomUUID());
        enderecoMap.put(endereco.getUuid(), endereco);

        return endereco;
    }

    public void atualizarEndereco(UUID uuid, Endereco endereco) {
        enderecoMap.computeIfPresent(uuid, (key, enderecoEncontrado) -> {
            enderecoEncontrado.setComplemento(endereco.getComplemento());
            enderecoEncontrado.setNumero(endereco.getNumero());
            return enderecoEncontrado;
        });

        throw new EnderecoNaoEncontradoException(String.format("Endereço com uuid:%s não encontrado.", uuid));
    }

    public void removerEndereco(UUID uuid) {
        boolean isEnderecoCadastrado = enderecoMap.containsKey(uuid);

        if(!isEnderecoCadastrado){
            throw new EnderecoNaoEncontradoException(String.format("Endereço com uuid:%s não encontrado.", uuid));
        }

        enderecoMap.remove(uuid);
    }

    public Optional<Endereco> buscarEnderecoPorUUID(UUID uuid) {
        return Optional.ofNullable(enderecoMap.get(uuid));
    }
}
