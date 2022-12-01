package com.example.imobiliaria.domain.service;

import com.example.imobiliaria.domain.model.Imoveis;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ImovelServiceTest {

    ImovelService service;

    @BeforeEach
    public void init(){
        service = new ImovelService();
    }

    @Test
    @DisplayName("deve retornar todos os imoveis disponíveis do tipo Apartamento")
    public void buscarApartamentosDisponiveis(){
        String bairroMock = "Vila Nova";
        String tipoImovel = "Apartamento";

        /**@@Method-1: utilizando lambda para realizar o filtro */
        List<Imoveis> imoveisResponse = service.findActiveApartment(bairroMock, tipoImovel);

        /**@@Method-2: utilizando query para realizar o filtro*/
//        List<Imoveis> imoveisResponse = service.findActiveApartmentQueryMethod(bairroMock, tipoImovel);

        assertNotNull(imoveisResponse);

        assertTrue(
                imoveisResponse
                        .stream()
                        .allMatch(imovel -> imovel.getTipo_Imovel().equals("Apartamento"))
        );

        assertTrue(
                imoveisResponse
                        .stream()
                        .allMatch(imovel -> imovel.getBairro().equals(bairroMock))
        );
    }

    @Test
    @DisplayName("deve retornar todos os imoveis disponíveis com valor do aluguel igual ou inferior ao valor informado.")
    public void buscarPorPreco(){

        BigDecimal limite = new BigDecimal(650.50);

        List<Imoveis> imoveisResponse = service.findByPriceAndAvailable(limite);

        assertTrue(imoveisResponse.stream()
                .allMatch(imovel -> imovel.getValor_aluguel_suge().compareTo(limite) <= 0));

    }


}
