package com.example.imobiliaria.api.assemblerConvert;

import com.example.imobiliaria.api.model.dto.ImoveisDTO;
import com.example.imobiliaria.api.model.dto.LocacaoDTO;
import com.example.imobiliaria.domain.model.Imoveis;
import com.example.imobiliaria.domain.model.Locacao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/*
* CLASSE UTILIZADA PARA CONVERTER Locacao em LocacaoDTO para depois devolver ao controller
* */

@Component
public class LocacaoConvertAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public LocacaoDTO convert_para_DTO(Locacao locacao){
        return modelMapper.map(locacao, LocacaoDTO.class);
    }

    public List<LocacaoDTO> convert_Lista_para_DTO( List<Locacao> locacoes) {
        return locacoes.stream()
                .map(locacao -> convert_para_DTO(locacao))
                .collect(Collectors.toList());
    }

}
