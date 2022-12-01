package com.example.imobiliaria.api.assemblerConvert;

/*
 * CLASSE UTILIZADA PARA CONVERTER ImovelInput em Imoveis para depois enviar ao Service
 * */

import com.example.imobiliaria.api.model.input.ImovelInput;
import com.example.imobiliaria.api.model.input.LocacaoInput;
import com.example.imobiliaria.domain.model.Cliente;
import com.example.imobiliaria.domain.model.Imoveis;
import com.example.imobiliaria.domain.model.Locacao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocacaoConvertDISAssembler {

    @Autowired
    private ModelMapper modelMapper;

    //recebe um objeto input e convert para um objeto do modelo de domínio
    public Locacao convert_paraClienteDomain(LocacaoInput locacaoInput){
        return modelMapper.map(locacaoInput, Locacao.class);
    }

    public void copy_paraLocacaoDomain(LocacaoInput locacaoInput, Locacao locacao){
        locacao.setCliente(new Cliente());
        locacao.setImovel(new Imoveis());

        modelMapper.map(locacaoInput, locacao);
    }
}
