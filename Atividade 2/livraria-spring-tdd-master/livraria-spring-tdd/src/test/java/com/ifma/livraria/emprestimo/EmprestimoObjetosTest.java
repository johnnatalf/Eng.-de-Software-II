package com.ifma.livraria.emprestimo;

import com.ifma.livraria.dto.EmprestimoDTO;
import com.ifma.livraria.entity.Emprestimo;
import com.ifma.livraria.entity.Livro;
import com.ifma.livraria.livro.LivroObjetosTest;
import com.ifma.livraria.usuario.UsuarioObjetosTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoObjetosTest {

    public EmprestimoDTO getEmprestimoDtoTest(){
        EmprestimoDTO emprestimo = new EmprestimoDTO();
        List<Livro> livros = new ArrayList<>();
        livros.add(new LivroObjetosTest().getLivroTeste());
        emprestimo.setIdUser(new UsuarioObjetosTest().getUsuarioTeste().getId());
        emprestimo.setLivros(livros);
        return emprestimo;
    }

    public Emprestimo getEmprestimoTest(){
        Emprestimo emprestimo = new Emprestimo();
        List<Livro> livros = new ArrayList<>();
        livros.add(new LivroObjetosTest().getLivroTeste());
        emprestimo.setIdUser(new UsuarioObjetosTest().getUsuarioTeste().getId());
        emprestimo.setLivros(livros);
        emprestimo.setDataInicioEmprestimo(LocalDateTime.now());
        emprestimo.setDataPrevistaDevolucaoEmprestimo(emprestimo.getDataInicioEmprestimo().plusDays(7));
        emprestimo.setDataDevolucaoEmprestimo(emprestimo.getDataInicioEmprestimo().plusDays(5));
        return emprestimo;
    }

    public Emprestimo getEmprestimoAtrasadoTest(){
        Emprestimo emprestimo = new Emprestimo();
        List<Livro> livros = new ArrayList<>();
        livros.add(new LivroObjetosTest().getLivroTeste());
        emprestimo.setIdUser(new UsuarioObjetosTest().getUsuarioTeste().getId());
        emprestimo.setLivros(livros);
        emprestimo.setDataInicioEmprestimo(LocalDateTime.now());
        emprestimo.setDataPrevistaDevolucaoEmprestimo(emprestimo.getDataInicioEmprestimo().plusDays(7));
        emprestimo.setDataDevolucaoEmprestimo(emprestimo.getDataInicioEmprestimo().plusDays(8));
        return emprestimo;
    }

    public Emprestimo getEmprestimoDataPrevistaInvalidaTest(){
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setIdEmprestimo(1L);
        List<Livro> livros = new ArrayList<>();
        livros.add(new LivroObjetosTest().getLivroTeste());
        emprestimo.setIdUser(new UsuarioObjetosTest().getUsuarioTeste().getId());
        emprestimo.setLivros(livros);
        emprestimo.setDataPrevistaDevolucaoEmprestimo(LocalDateTime.now());
        emprestimo.setDataInicioEmprestimo(emprestimo.getDataPrevistaDevolucaoEmprestimo().plusDays(7));
        emprestimo.setDataDevolucaoEmprestimo(null);
        return emprestimo;
    }

    public Emprestimo getEmprestimoDataDevolucaoInvalidaTest(){
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setIdEmprestimo(1L);
        List<Livro> livros = new ArrayList<>();
        livros.add(new LivroObjetosTest().getLivroTeste());
        emprestimo.setIdUser(new UsuarioObjetosTest().getUsuarioTeste().getId());
        emprestimo.setLivros(livros);
        emprestimo.setDataInicioEmprestimo(LocalDateTime.now());
        emprestimo.setDataPrevistaDevolucaoEmprestimo(LocalDateTime.now().plusDays(7));
        emprestimo.setDataDevolucaoEmprestimo(LocalDateTime.now().minusDays(2));
        return emprestimo;
    }

    public EmprestimoDTO getEmprestimoDtoComLivroReservadoTest(){
        EmprestimoDTO emprestimo = new EmprestimoDTO();
        emprestimo.setIdEmprestimo(1L);
        List<Livro> livros = new ArrayList<>();
        livros.add(new LivroObjetosTest().getLivroTeste());
        livros.add(new LivroObjetosTest().getLivroReservadoTeste());
        emprestimo.setIdUser(new UsuarioObjetosTest().getUsuarioTeste().getId());
        emprestimo.setLivros(livros);
        return emprestimo;
    }

    public List<EmprestimoDTO> getListEmprestimoPorUserTest(Long idUsuario){
        List<EmprestimoDTO> emprestimos = new ArrayList<>();
        int id = 1;
        for(Livro livro : new LivroObjetosTest().getListaLivroDisponiveisTeste()){
            EmprestimoDTO emprestimo = new EmprestimoDTO();
            emprestimo.setIdEmprestimo(Long.parseLong(""+id++));
            emprestimo.setIdUser(idUsuario);
            List<Livro> livros = new ArrayList<>();
            livros.add(livro);
            emprestimo.setLivros(livros);
            emprestimos.add(emprestimo);
        }

        return emprestimos;
    }

    public List<EmprestimoDTO> getListEmprestimoMultiUserTest(Long idUsuario1, Long idUsuario2){
        List<EmprestimoDTO> emprestimos = new ArrayList<>();
        int id = 1;
        List<Livro> listaLivros = new LivroObjetosTest().getListaLivroDisponiveisTeste();
        for(Livro livro : listaLivros){
            EmprestimoDTO emprestimo = new EmprestimoDTO();
            emprestimo.setIdEmprestimo(Long.parseLong(""+id++));
            emprestimo.setIdUser( id % 2 == 0 ? idUsuario2 : idUsuario1);
            List<Livro> livros = new ArrayList<>();
            livros.add(livro);
            emprestimo.setLivros(livros);
            emprestimos.add(emprestimo);
        }

        return emprestimos;
    }

}
