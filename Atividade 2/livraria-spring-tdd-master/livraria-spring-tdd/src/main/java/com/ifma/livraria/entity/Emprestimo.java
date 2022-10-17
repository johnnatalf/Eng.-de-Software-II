package com.ifma.livraria.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Emprestimo {

    private Long idEmprestimo;
    private Long idUser;
    private List<Livro> livros;
    private LocalDateTime dataInicioEmprestimo;
    private LocalDateTime dataPrevistaDevolucaoEmprestimo;
    private LocalDateTime dataDevolucaoEmprestimo;

}
