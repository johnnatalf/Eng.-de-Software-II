package com.ifma.livraria.repository;

import com.ifma.livraria.entity.Emprestimo;

public interface EmprestimoRepository{

    public Emprestimo salvarNovoEmprestimo(Emprestimo emprestimo);

    public boolean devolucaoDeEmprestimo(Emprestimo emprestimo);
}
