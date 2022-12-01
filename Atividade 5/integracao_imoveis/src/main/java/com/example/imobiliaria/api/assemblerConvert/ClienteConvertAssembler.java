package com.example.imobiliaria.api.assemblerConvert;

import com.example.imobiliaria.api.model.dto.ClienteDTO;
import com.example.imobiliaria.domain.model.Cliente;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/*
* CLASSE UTILIZADA PARA CONVERTER Cliente em ClienteDTO para depois devolver ao controller
* */

@Component
public class ClienteConvertAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public ClienteDTO convert_para_DTO(Cliente cliente){
        return modelMapper.map(cliente, ClienteDTO.class);
    }

    public List<ClienteDTO> convert_Lista_para_DTO( List<Cliente> clientes) {
        return clientes.stream()
                .map(cliente -> convert_para_DTO(cliente))
                .collect(Collectors.toList());
    }

}
