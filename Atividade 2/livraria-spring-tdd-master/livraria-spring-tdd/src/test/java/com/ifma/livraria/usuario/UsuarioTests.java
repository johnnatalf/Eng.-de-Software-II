package com.ifma.livraria.usuario;

import com.ifma.livraria.dto.EmprestimoDTO;
import com.ifma.livraria.emprestimo.EmprestimoObjetosTest;
import com.ifma.livraria.entity.Emprestimo;
import com.ifma.livraria.entity.Usuario;
import com.ifma.livraria.repository.impl.EmprestimoRepositoryImpl;
import com.ifma.livraria.service.EmprestimoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UsuarioTests {

    @Mock
    private EmprestimoRepositoryImpl repositoryEmprestimo;

    @InjectMocks
    private EmprestimoService serviceEmprestimo;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void verificarUsuarioSemEmprestimo(){
        Emprestimo retornoRepository = null;
        when(repositoryEmprestimo.salvarNovoEmprestimo(new EmprestimoObjetosTest().getEmprestimoTest())).thenReturn(retornoRepository);
        Emprestimo retornoService = serviceEmprestimo.salvarEmprestimo(new EmprestimoObjetosTest().getEmprestimoDtoTest());

        assertEquals(retornoService, retornoRepository);
    }

    @Test
    public void realizarSequenciaDeEmprestimoMesmoUsuario(){
        Usuario user = new UsuarioObjetosTest().getUsuarioTeste();
        List<EmprestimoDTO> emprestimos = new EmprestimoObjetosTest().getListEmprestimoPorUserTest(user.getId());
        for(EmprestimoDTO emprestimoDTO : emprestimos){
            Emprestimo retornoRepository = null;
            when(repositoryEmprestimo.salvarNovoEmprestimo(emprestimoDTO.converterParaEmprestimo())).thenReturn(retornoRepository);
            Emprestimo retornoService = serviceEmprestimo.salvarEmprestimo(emprestimoDTO);

            assertEquals(retornoService, retornoRepository);
        }
    }

}
