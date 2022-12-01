package com.example.imobiliaria.domain.exception;

public class ClienteNaoEncontradoException extends EntidadeNaoEncontradaException {
    private static final long serialVersionUID = 1L;

    public ClienteNaoEncontradoException(String mensagem){
        super(mensagem);
    }

    public ClienteNaoEncontradoException(Long clienteId){
        this(String.format("NÃO EXISTE UM CADASTRO DE CLIENTE COM CÓDIGO %d", clienteId));
    }
}
