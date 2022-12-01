package com.example.imobiliaria.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
public class ImovelInput {
    @NotNull
    private String tipo_Imovel;
    private String endereco;
    private String bairro;

    @NotBlank
    private String cep;


    @PositiveOrZero
    private Integer dormitorios;
    private Integer banheiros;
    private Integer suites;
    private Integer metragem;
    private Double valor_aluguel_suge;
    private String obs;
}
