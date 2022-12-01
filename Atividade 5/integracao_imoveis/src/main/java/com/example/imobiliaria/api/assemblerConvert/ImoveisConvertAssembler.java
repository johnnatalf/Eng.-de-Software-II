package com.example.imobiliaria.api.assemblerConvert;

import com.example.imobiliaria.api.model.dto.ClienteDTO;
import com.example.imobiliaria.api.model.dto.ImoveisDTO;
import com.example.imobiliaria.domain.model.Cliente;
import com.example.imobiliaria.domain.model.Imoveis;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/*
* CLASSE UTILIZADA PARA CONVERTER Imovel em ImovelDTO para depois devolver ao controller
* */

@Component
public class ImoveisConvertAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public ImoveisDTO convert_para_DTO(Imoveis imovel){
        return modelMapper.map(imovel, ImoveisDTO.class);
    }

    public List<ImoveisDTO> convert_Lista_para_DTO( List<Imoveis> imoveis) {
        return imoveis.stream()
                .map(imovel -> convert_para_DTO(imovel))
                .collect(Collectors.toList());
    }

}
