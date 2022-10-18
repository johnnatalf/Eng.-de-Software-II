package com.ifma.livraria.service;

import com.ifma.livraria.entity.HistoricoEmprestimoLivro;
import com.ifma.livraria.entity.Livro;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LivroService {

    public boolean livroEstaDisponivel(Livro livro){
        return !livro.isEmprestado() && !livro.isReservado();
    }

    public boolean livroEstaReservado(Livro livro){
        return livro.isReservado();
    }

    public boolean LivroEstaEmprestado(Livro livro){
        return livro.isEmprestado();
    }

    @Transactional
    public boolean setLivroEmprestado(Livro livro){
        livro.setEmprestado(true);
        return true;
    }

    @Transactional
    public boolean setLivroReservado(Livro livro){
        livro.setReservado(true);
        return true;
    }

    @Transactional
    public boolean setLivroDisponivel(Livro livro){
        livro.setReservado(false);
        livro.setEmprestado(false);
        return true;
    }

    public List<HistoricoEmprestimoLivro> getEmprestimosPorLivro(Livro livro){
        return null;
    }


}
