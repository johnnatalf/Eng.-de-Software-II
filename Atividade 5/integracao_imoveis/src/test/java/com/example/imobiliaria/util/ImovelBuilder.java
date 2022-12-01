package com.example.imobiliaria.util;

import com.example.imobiliaria.domain.model.Imoveis;
import org.springframework.test.util.ReflectionTestUtils;

public class ImovelBuilder {
    public static Imoveis getImovel(){
        Imoveis imovel = new Imoveis();

        ReflectionTestUtils.setField(imovel, "id", 1L);
        imovel.setBairro("vila nova");
        imovel.setCep("65000-000");
        imovel.setTipo_Imovel("casa");

        return imovel;
    }
}
