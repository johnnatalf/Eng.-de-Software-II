package com.ifma.livraria.dto;

import com.ifma.livraria.entity.Emprestimo;
import com.ifma.livraria.entity.Livro;
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
public class EmprestimoDTO {

    private Long idEmprestimo;
    private Long idUser;
    private List<Livro> livros;

    public Emprestimo converterParaEmprestimo(){
        return new Emprestimo(this.idEmprestimo, this.idUser, this.livros, LocalDateTime.now(), LocalDateTime.now().plusDays(7), null);
    }
}
