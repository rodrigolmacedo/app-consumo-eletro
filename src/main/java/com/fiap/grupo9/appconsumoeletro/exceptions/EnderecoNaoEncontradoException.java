package com.fiap.grupo9.appconsumoeletro.exceptions;

public class EnderecoNaoEncontradoException extends IllegalArgumentException{
    public EnderecoNaoEncontradoException(String mensagem){
        super(mensagem);
    }
}
