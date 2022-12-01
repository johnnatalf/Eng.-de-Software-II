package com.example.imobiliaria.util;

import com.example.imobiliaria.domain.model.Cliente;
import org.springframework.test.util.ReflectionTestUtils;

public class ClienteBuilder {

    public static Cliente getCliente(){
        Cliente cliente = new Cliente();

        ReflectionTestUtils.setField(cliente, "id", 1L);
        cliente.setCpf("871.572.440-98");
        cliente.setEmail("mariano-oliveira@hotmail.com");
        cliente.setTelefone("98 9999-8888");

        return cliente;
    }
}
