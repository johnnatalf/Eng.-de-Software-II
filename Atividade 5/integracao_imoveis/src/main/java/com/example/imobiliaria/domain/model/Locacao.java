package com.example.imobiliaria.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tab_locacao")
public class Locacao {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean ativo;
    private int dia_vencimento;
    private Double perc_multa;
    private BigDecimal valor_alugue;
    private String obs;

    @Temporal(TemporalType.DATE)
    private Date data_fim;

    @Temporal(TemporalType.DATE)
    private Date data_inicio;

    @ManyToOne
    @JoinColumn(name = "id_inquilino")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_imovel")
    private Imoveis imovel;

    @OneToMany(mappedBy = "locacao", cascade = CascadeType.ALL)
    private Set<Alugueis> alugueis;

}
