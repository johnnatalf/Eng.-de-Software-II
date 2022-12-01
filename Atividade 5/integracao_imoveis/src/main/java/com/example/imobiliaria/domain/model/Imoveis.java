package com.example.imobiliaria.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tab_imoveis")
public class Imoveis {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo_Imovel;
    private String endereco;
    private String bairro;
    private String cep;
    private Integer dormitorios;
    private Integer banheiros;
    private Integer suites;
    private Integer metragem;
    private BigDecimal valor_aluguel_suge;
    private String obs;

    @JsonIgnore
    @OneToMany(mappedBy = "imovel", cascade = CascadeType.ALL)
    private Set<Locacao> locacao = new LinkedHashSet<>();
}
