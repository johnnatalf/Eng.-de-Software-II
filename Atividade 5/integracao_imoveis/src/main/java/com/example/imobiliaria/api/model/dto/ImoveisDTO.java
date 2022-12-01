package com.example.imobiliaria.api.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImoveisDTO {

    private Long id;
    private String tipo_Imovel;
    private String endereco;
    private String bairro;
    private String cep;
    private Integer dormitorios;
    private Integer banheiros;
    private Integer suites;
    private Integer metragem;
    private Double valor_aluguel_suge;
    private String obs;
}
