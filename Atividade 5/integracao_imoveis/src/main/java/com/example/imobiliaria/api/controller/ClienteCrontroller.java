package com.example.imobiliaria.api.controller;

import com.example.imobiliaria.api.assemblerConvert.ClienteConvertAssembler;
import com.example.imobiliaria.api.assemblerConvert.ClienteConvertDISAssembler;
import com.example.imobiliaria.api.model.dto.ClienteDTO;
import com.example.imobiliaria.api.model.input.ClienteInput;
import com.example.imobiliaria.domain.repository.ClienteRepository;
import com.example.imobiliaria.domain.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteCrontroller {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteConvertAssembler clienteConvertAssembler;

    @Autowired
    private ClienteConvertDISAssembler clienteConvertDISAssembler;

    @GetMapping
    public List<ClienteDTO> listar(){
        return clienteConvertAssembler
                .convert_Lista_para_DTO(clienteService.findAll());
    }

    @GetMapping("/{clienteId}")
    public ClienteDTO buscar(@PathVariable Long clienteId){
        return clienteConvertAssembler
                .convert_para_DTO(clienteService.findById(clienteId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDTO adicionar(@RequestBody @Valid ClienteInput clienteInput) {
        return clienteConvertAssembler
                .convert_para_DTO(clienteService.save(
                        clienteConvertDISAssembler.convert_paraClienteDomain(clienteInput)
                ));
    }

    @PutMapping("/{clienteId}")
    public ClienteDTO atualizar(@PathVariable Long clienteId, @RequestBody @Valid ClienteInput clienteInput) {
        return clienteConvertAssembler
                .convert_para_DTO(clienteService.update(
                        clienteId, clienteConvertDISAssembler.convert_paraClienteDomain(clienteInput)
                ));
    }

    @DeleteMapping("/{clienteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long clienteId){
        clienteService.delete(clienteId);
    }
}
