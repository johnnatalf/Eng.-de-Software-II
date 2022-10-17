package com.ifma.livraria.usuario;

import com.ifma.livraria.entity.Usuario;

public class UsuarioObjetosTest {
    public Usuario getUsuarioTeste(){
        return new Usuario(1L, "Joao Carlos", "123123", false);
    }
}
