package com.example.imobiliaria.domain.service;

import com.example.imobiliaria.domain.exception.NegocioException;
import com.example.imobiliaria.domain.model.Alugueis;

import com.example.imobiliaria.domain.model.Imoveis;
import com.example.imobiliaria.domain.model.Locacao;
import com.example.imobiliaria.util.AluguelBuilder;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

public class AluguelServiceTest {
    AluguelService service;

    @BeforeEach
    public void init(){
        service = new AluguelService();
    }

    @Test
    @DisplayName("deve retornar todos os alugueis")
    public void buscarTodos(){
        List<Alugueis> alugueisDB = service.findAllAlugueis();

        assertNotNull(alugueisDB);
    }

    @Test
    @DisplayName("Deve retornar a lista com todos os alugueis ativos")
    public void buscarTodosAtivos(){

       List<Locacao> locacoesDB = service.findAll();

        assertNotNull(locacoesDB);
        assertTrue(
                locacoesDB
                        .stream()
                        .allMatch(locacao -> locacao.isAtivo())
        );
    }

    @Test
    @DisplayName("deve retornar o aluguel do id informado")
    public void buscarPorId(){
        Alugueis aluguelMock = AluguelBuilder.getAluguel();

        Alugueis aluguelResponse = service.findById(aluguelMock.getId());

        assertNotNull(aluguelResponse);
        assertEquals(aluguelMock, aluguelResponse);
    }

    @Test
    @DisplayName("deve lançar uma exception ao buscar aluguel por um id inexistente")
    public void buscarIdnexistente(){
        Long nonexistentId = 11110L;

        Assertions.assertThatExceptionOfType(NegocioException.class)
                .isThrownBy(() -> service.findById(nonexistentId))
                .withMessage("O aluguel com id invalido")
        ;
    }

    @Test
    @DisplayName("deve Recuperar uma lista com todos os alugueis pagos")
    public void buscarAluegueisPagos(){
        String nameMock = "Joaozinho";

       List<Alugueis> alugueisResponse = service.findAllByClienteNameAndPaid(nameMock);

       assertNotNull(alugueisResponse);
       Assertions.assertThat( alugueisResponse ).allMatch( al -> al.getValor_pago()!=null || ! al.getValor_pago().equals(0) );

    }

    @Test
    @DisplayName("deve Recuperar uma lista com todos os alugueis que foram pagos com atraso na data de vencimento")
    public void buscarAluegueisPagosComAtraso(){
        String nameMock = "Luizinho";

        List<Alugueis> alugueisResponse = service.findAllByClienteNameAndPaidLate(nameMock);

        assertNotNull(alugueisResponse, "Não há alugueis registrados no nome de: " + nameMock);
        assertTrue(alugueisResponse.size()>0, "Não há alugueis registrados no nome de:" + nameMock);
        Assertions.assertThat( alugueisResponse ).allMatch( al -> al.getDt_pagamento().after(al.getDt_vencimento()) );

    }

    @Test
    @DisplayName("deve realizar o pagamento do aluguel para o imovel desejado")
    public void pagamentodeAluguel(){

        Imoveis imovelMock = new Imoveis();
        ReflectionTestUtils.setField(imovelMock, "id", 1L);
        BigDecimal valorPago = new BigDecimal(1000);

        Alugueis aluguelResponse = service.registrarPagamento(valorPago, imovelMock);

        assertNotNull(aluguelResponse);
        assertEquals(valorPago, aluguelResponse.getValor_pago());
    }

    @Test
    @DisplayName("deve realizar lançar uma exception caso o valor pago seja inferior ao valor do aluguel")
    public void pagamentodeAluguelComValorInferiro(){

        Imoveis imovelMock = new Imoveis();
        ReflectionTestUtils.setField(imovelMock, "id", 1L);
        BigDecimal valorPago = new BigDecimal(850);

        Assertions.assertThatExceptionOfType(NegocioException.class)
                .isThrownBy(() -> service.registrarPagamento(valorPago, imovelMock))
                .withMessage("Pagamento com valor inferior ao preço do aluguel");
    }

}
