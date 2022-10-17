package com.ifma.livraria.livro;

import com.ifma.livraria.entity.Livro;

import java.util.ArrayList;
import java.util.List;

public class LivroObjetosTest {

    public Livro getLivroTeste(){
        return new Livro(1L, "Mauricio de Souza", "Turma da monica", false, false);
    }

    public Livro getLivroReservadoTeste(){
        return new Livro(2L, "Joao do Pneu", "A borracha e o diamante", false, true);
    }

    public Livro getLivroEmprestadoTeste(){
        return new Livro(3L, "Luis José", "O amanhã vem de uma flor", true, false);
    }

    public List<Livro> getListaLivroDisponiveisTeste(){
        List<Livro> livros = new ArrayList<>();
        livros.add(new Livro(1L, "Monteiro Lobato", "As aventuras de pedrinho", false, false));
        livros.add(new Livro(2L, "Monteiro Lobato", "As aventuras de emilia", false, false));
        livros.add(new Livro(3L, "Monteiro Lobato", "As aventuras de narizinho", false, false));
        livros.add(new Livro(4L, "Monteiro Lobato", "As aventuras da cuca", false, false));
        livros.add(new Livro(5L, "Monteiro Lobato", "As aventuras do saci", false, false));
        return livros;
    }
}
