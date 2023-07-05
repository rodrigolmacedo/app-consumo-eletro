package com.fiap.grupo9.appconsumoeletro.dominio.service;

import com.fiap.grupo9.appconsumoeletro.dominio.Pessoa;
import com.fiap.grupo9.appconsumoeletro.repositorio.IPessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class PessoaService {
    @Autowired
    private IPessoaRepository repository;
    public Collection<Pessoa> findAll(){
        var pessoas = repository.findAll();
        return pessoas;
    }

    public Optional<Pessoa> findById(UUID id){
        var pessoas = repository.findById(id);
        return pessoas;
    }

    public Pessoa save(Pessoa pessoa){
        var pessoaSaved = repository.save(pessoa);
        return pessoaSaved;
    }



}
