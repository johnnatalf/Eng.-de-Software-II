package com.example.imobiliaria.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "RECURSO NÃO ENCONTRADO"),
    ENTIDADE_EM_USO("/entidade-em-uso", "ENTIDADE EM USO"),
    ERRO_NEGOCIO("/erro-negocio", "VIOLAÇÃO DA REGRA DE NEGOCIO"),
    MENSAGEM_INCOMPREENSIVEL("/mensagem-incopreensivel", "MENSAGEM INCONPREENSIVEL"),
    PARAMETRO_INVALIDO("/parametro-invalido", "PARAMETRO INFORMADO INVALIDO"),
    ERRO_DE_SISTEMA("/erro-sistema", "ERRO DE SISTEMA"),
    DADOS_INVALIDOS("/dados-invalidos", "DADOS INVALDOS");

    private String title;
    private String uri;

    ProblemType(String path, String title) {
        this.uri = "https://imobiliaria.com.br/" + path;
        this.title = title;
    }
}
