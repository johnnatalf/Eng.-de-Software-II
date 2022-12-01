package com.example.imobiliaria.domain.exception;

public class ImovelNaoEncontradoException extends EntidadeNaoEncontradaException {
    private static final long serialVersionUID = 1L;

    public ImovelNaoEncontradoException(String mensagem){
        super(mensagem);
    }

    public ImovelNaoEncontradoException(Long imovelId){
        this(String.format("NÃO EXISTE UM CADASTRO DE IMOVEL COM CÓDIGO %d", imovelId));
    }
}
