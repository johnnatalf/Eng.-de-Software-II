package com.example.imobiliaria.api.assemblerConvert;

/*
 * CLASSE UTILIZADA PARA CONVERTER ImovelInput em Imoveis para depois enviar ao Service
 * */

import com.example.imobiliaria.api.model.input.ClienteInput;
import com.example.imobiliaria.api.model.input.ImovelInput;
import com.example.imobiliaria.domain.model.Cliente;
import com.example.imobiliaria.domain.model.Imoveis;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImoveisConvertDISAssembler {

    @Autowired
    private ModelMapper modelMapper;

    //recebe um objeto input e convert para um objeto do modelo de domínio
    public Imoveis convert_paraClienteDomain(ImovelInput imovelInput){
        return modelMapper.map(imovelInput, Imoveis.class);
    }

    public void copy_paraClienteDomain(ImovelInput imovelInput, Imoveis imovel){
        modelMapper.map(imovelInput, imovel);
    }
}
