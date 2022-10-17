package com.ifma.livraria.service;

import com.ifma.livraria.entity.Usuario;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    public boolean usuarioLiberadoParaEmprestimo(Long idUsuario){
        return true;
    }

    public boolean setBloqueado(Long idUsuario){
        return true;
    }
}
