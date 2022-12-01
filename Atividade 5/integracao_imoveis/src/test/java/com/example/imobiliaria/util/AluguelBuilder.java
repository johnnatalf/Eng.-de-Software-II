package com.example.imobiliaria.util;

import com.example.imobiliaria.domain.model.Alugueis;
import com.example.imobiliaria.domain.model.Cliente;
import com.example.imobiliaria.domain.model.Imoveis;
import com.example.imobiliaria.domain.model.Locacao;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;
import java.util.List;

public class AluguelBuilder {

    public static List<Locacao> alugueisAtivos(){
        return null;
    }

    public static Alugueis getAluguel(){
        Alugueis aluguel = new Alugueis();

        ReflectionTestUtils.setField(aluguel, "id", 1L);

        return aluguel;
    }

//    public static Locacao aluguelAtivo(){
//        Locacao locacao = new Locacao();
//
//        ReflectionTestUtils.setField(locacao, "id", 1L);
//        locacao.setAtivo(true);
//
//        return locacao;
//    }
//
//    public static Locacao aluguelInativo(){
//        Locacao locacao = new Locacao();
//        locacao.setAtivo(false);
//
//        return locacao;
//    }




}
