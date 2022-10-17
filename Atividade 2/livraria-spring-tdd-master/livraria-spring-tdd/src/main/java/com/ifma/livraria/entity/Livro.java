package com.ifma.livraria.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Livro {

    private Long id;
    private String autor;
    private String titulo;
    private boolean emprestado;
    private boolean reservado;

}
