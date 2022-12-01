package com.example.imobiliaria.api.model.dto;

/*
 CLASSE UTILIZADA PARA FORNECER O CONTEUDO DA REQUISIÇÃO
* */

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class LocacaoDTO {

    private Long id;
    private boolean ativo;
    private int dia_vencimento;
    private Double perc_multa;
    private BigDecimal valor_alugue;
    private String obs;

    private ClienteDTO cliente;
    private ImoveisDTO imovel;
}
