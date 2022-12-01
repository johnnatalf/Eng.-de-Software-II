package com.example.imobiliaria.domain.service;

import com.example.imobiliaria.domain.exception.ClienteNaoEncontradoException;
import com.example.imobiliaria.domain.exception.EntidadeEmUsoException;
import com.example.imobiliaria.domain.model.Cliente;
import com.example.imobiliaria.domain.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    private static final String MSG_CLIENTE_EM_USO
            = "Cliente de código %d não pode ser removido, pois está associada a outro item do banco";

    @Transactional
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Transactional
    public void delete(Long ClienteId){

        try{

            clienteRepository.deleteById(ClienteId);

        }catch(EmptyResultDataAccessException e){
            throw new ClienteNaoEncontradoException(ClienteId);
        }catch(DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format(MSG_CLIENTE_EM_USO, ClienteId));
        }
    }

    @Transactional
    public Cliente update(Long clienteId, Cliente cliente) {
        Cliente clienteAtual = findById(clienteId);
        cliente.setId(clienteAtual.getId());

        return save(cliente);
    }

    public Cliente findById(Long clienteId){
        return clienteRepository.findById(clienteId)
                .orElseThrow( () -> new ClienteNaoEncontradoException(clienteId)
                );
    }

    public List<Cliente> findAll(){
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes;
    }

}
