package com.example.imobiliaria.domain.exception;

public class LocacaoNaoEncontradaException extends EntidadeNaoEncontradaException {
    private static final long serialVersionUID = 1L;

    public LocacaoNaoEncontradaException(String mensagem){
        super(mensagem);
    }

    public LocacaoNaoEncontradaException(Long locacaoId){
        this(String.format("NÃO EXISTE UM CADASTRO DE IMOVEL COM CÓDIGO %d", locacaoId));
    }
}
