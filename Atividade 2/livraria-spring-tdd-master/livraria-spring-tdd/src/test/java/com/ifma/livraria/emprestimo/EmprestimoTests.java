package com.ifma.livraria.emprestimo;

import com.ifma.livraria.dto.EmprestimoDTO;
import com.ifma.livraria.entity.Emprestimo;
import com.ifma.livraria.entity.Livro;
import com.ifma.livraria.exceptions.LivrariaException;
import com.ifma.livraria.livro.LivroObjetosTest;
import com.ifma.livraria.repository.impl.EmprestimoRepositoryImpl;
import com.ifma.livraria.service.EmprestimoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
class EmprestimoTests {

    private static final LocalDate LOCAL_DATE = LocalDate.now();
    @Mock
    private EmprestimoRepositoryImpl repository;

    @InjectMocks
    private EmprestimoService service;

    @Mock
    private Clock clock;
    private Clock localClock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void realizarEmprestimoLivroNaoReservado(){
        Emprestimo realizadoRepositorio = null;
        when(repository.salvarNovoEmprestimo(new EmprestimoObjetosTest().getEmprestimoTest())).thenReturn(realizadoRepositorio);
        Emprestimo realizadoService = service.salvarEmprestimo(new EmprestimoObjetosTest().getEmprestimoDtoTest());

        assertEquals(realizadoRepositorio, realizadoService);
    }

    @Test
    public void realizarEmprestimoLivroReservado(){
        Throwable thrown = catchThrowable(() -> service.salvarEmprestimo(new EmprestimoObjetosTest().getEmprestimoDtoComLivroReservadoTest()));

        assertThat(thrown).isInstanceOf(LivrariaException.class)
                .hasMessageContaining("O emprestimo nao obdece todas as regras.");
    }

    @Test
    public void verificarDataPrevistaEmprestimo(){
        assertTrue(service.dataPrevistaEmprestimoEstaValida(new EmprestimoObjetosTest().getEmprestimoTest()));
        Throwable thrown = catchThrowable(() -> service.dataPrevistaEmprestimoEstaValida(new EmprestimoObjetosTest().getEmprestimoDataPrevistaInvalidaTest()));

        assertThat(thrown).isInstanceOf(LivrariaException.class)
                .hasMessageContaining("data prevista deve ser posterior a data de emprestimo");
    }

    @Test
    public void verificarDataDevolucaoEmprestimo(){
        assertTrue(service.dataDevolucaoEmprestimoEstaValida(new EmprestimoObjetosTest().getEmprestimoTest()));
        Throwable thrown = catchThrowable(() -> service.dataDevolucaoEmprestimoEstaValida(new EmprestimoObjetosTest().getEmprestimoDataDevolucaoInvalidaTest()));

        assertThat(thrown).isInstanceOf(LivrariaException.class)
                .hasMessageContaining("a data de devolucao deve ser posterior a data de emprestimo");
    }

    @Test
    public void verificarQuantidadeDeLivrosEmprestimo(){
        Emprestimo emprestimo = new EmprestimoObjetosTest().getEmprestimoTest();
        assertTrue(service.quantidadeDeLivrosEstaValida(emprestimo));

        List<Livro> listaLivrosAcimaDoPermitido = new LivroObjetosTest().getListaLivroDisponiveisTeste();
        emprestimo.setLivros(listaLivrosAcimaDoPermitido);
        Throwable thrown = catchThrowable(() -> service.quantidadeDeLivrosEstaValida(emprestimo));
        assertThat(thrown).isInstanceOf(LivrariaException.class)
                .hasMessageContaining("O emprestimo nao tem uma quantidade de livros valida.");

        emprestimo.setLivros(new ArrayList<>());
        thrown = catchThrowable(() -> service.quantidadeDeLivrosEstaValida(emprestimo));
        assertThat(thrown).isInstanceOf(LivrariaException.class)
                .hasMessageContaining("O emprestimo nao tem uma quantidade de livros valida.");
    }

    @Test
    public void devolucaoAntesDaDataPrevista(){
        localClock = Clock.fixed(LOCAL_DATE.plusDays(2).atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        doReturn(localClock.instant()).when(clock).instant();
        doReturn(localClock.getZone()).when(clock).getZone();

        boolean retornoRepository = false;
        when(repository.devolucaoDeEmprestimo(new EmprestimoObjetosTest().getEmprestimoTest())).thenReturn(retornoRepository);
        boolean retornoService = service.realizarDevolucao(new EmprestimoObjetosTest().getEmprestimoDtoTest()) > 0;

        assertEquals(retornoService, retornoRepository);
    }

    @Test
    public void devolucaoNaDataPrevista(){
        localClock = Clock.fixed(LOCAL_DATE.plusDays(7).atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        doReturn(localClock.instant()).when(clock).instant();
        doReturn(localClock.getZone()).when(clock).getZone();

        boolean retornoRepository = false;
        when(repository.devolucaoDeEmprestimo(new EmprestimoObjetosTest().getEmprestimoTest())).thenReturn(retornoRepository);
        boolean retornoService = service.realizarDevolucao(new EmprestimoObjetosTest().getEmprestimoDtoTest()) > 0;

        assertEquals(retornoService, retornoRepository);
    }

    @Test
    public void devolucaoUmDiaAposDataPrevista(){
        localClock = Clock.fixed(LOCAL_DATE.plusDays(8).atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        doReturn(localClock.instant()).when(clock).instant();
        doReturn(localClock.getZone()).when(clock).getZone();

        boolean retornoRepository = false;
        when(repository.devolucaoDeEmprestimo(new EmprestimoObjetosTest().getEmprestimoTest())).thenReturn(retornoRepository);
        double valorEmprestimo = service.realizarDevolucao(new EmprestimoObjetosTest().getEmprestimoDtoTest());
        boolean multaAplicadaCorretamente = valorEmprestimo == (new EmprestimoObjetosTest().getEmprestimoTest().getLivros().size() * 7) + 0.4;

        assertEquals(multaAplicadaCorretamente, retornoRepository);
    }

    @Test
    public void devolucaoTrintaDiaAposDataPrevista(){
        localClock = Clock.fixed(LOCAL_DATE.plusDays(37).atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        doReturn(localClock.instant()).when(clock).instant();
        doReturn(localClock.getZone()).when(clock).getZone();

        boolean retornoRepository = false;
        when(repository.devolucaoDeEmprestimo(new EmprestimoObjetosTest().getEmprestimoTest())).thenReturn(retornoRepository);
        double valorEmprestimo = service.realizarDevolucao(new EmprestimoObjetosTest().getEmprestimoDtoTest());
        boolean multaAplicadaCorretamente = valorEmprestimo == (new EmprestimoObjetosTest().getEmprestimoTest().getLivros().size() * 5) + 30*0.4;

        assertEquals(multaAplicadaCorretamente, retornoRepository);
    }

    @Test
    public void verificarFiltrarListaEmprestimoPorUser(){
        List<Emprestimo> listaDeEmprestimo = new EmprestimoObjetosTest().getListEmprestimoMultiUserTest(1L, 2L).stream().map(EmprestimoDTO::converterParaEmprestimo).collect(Collectors.toList());
        List<Emprestimo> listaComFiltroAplicado = service.consultarEmprestimosPorUsuario(1L, listaDeEmprestimo);

        assertTrue(listaDeEmprestimo.size() > listaComFiltroAplicado.size());
    }

    @Test
    public void verificarCalculoDeValorEmprestimo(){
        double valor = service.calculaValorEmprestimo( new EmprestimoObjetosTest().getEmprestimoTest());
        double valorManual = new EmprestimoObjetosTest().getEmprestimoTest().getLivros().size() * 5;

        Emprestimo emprestimoAtrasado = new EmprestimoObjetosTest().getEmprestimoAtrasadoTest();
        double valorComMultaAtraso = service.calculaValorEmprestimo(emprestimoAtrasado);
        double valorManualComMulta = emprestimoAtrasado.getLivros().size() * 5 + emprestimoAtrasado.getDataDevolucaoEmprestimo().compareTo(emprestimoAtrasado.getDataPrevistaDevolucaoEmprestimo()) * 0.4;

        assertEquals(valor==valorManual, valorComMultaAtraso==valorManualComMulta);
    }

}
