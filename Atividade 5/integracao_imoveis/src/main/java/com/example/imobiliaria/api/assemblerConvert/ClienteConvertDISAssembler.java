package com.example.imobiliaria.api.assemblerConvert;

/*
 * CLASSE UTILIZADA PARA CONVERTER ClienteInput em Cliente para depois enviar ao Service
 * */

import com.example.imobiliaria.api.model.input.ClienteInput;
import com.example.imobiliaria.domain.model.Cliente;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClienteConvertDISAssembler {

    @Autowired
    private ModelMapper modelMapper;

    //recebe um objeto input e convert para um objeto do modelo de domínio
    public Cliente convert_paraClienteDomain(ClienteInput clienteInput){
        return modelMapper.map(clienteInput, Cliente.class);
    }

    public void copy_paraClienteDomain(ClienteInput clienteInput, Cliente cliente){
        modelMapper.map(clienteInput, cliente);
    }
}
