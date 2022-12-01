package com.example.imobiliaria.api.controller;

import com.example.imobiliaria.api.assemblerConvert.LocacaoConvertAssembler;
import com.example.imobiliaria.api.assemblerConvert.LocacaoConvertDISAssembler;
import com.example.imobiliaria.api.model.dto.LocacaoDTO;
import com.example.imobiliaria.api.model.input.LocacaoInput;
import com.example.imobiliaria.domain.model.Locacao;
import com.example.imobiliaria.domain.service.LocacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/locacoes")
public class LocacaoController {

    @Autowired
    private LocacaoService locacaoService;

    @Autowired
    private LocacaoConvertAssembler locacaoConvertAssembler;

    @Autowired
    private LocacaoConvertDISAssembler locacaoConvertDISAssembler;

    @GetMapping
    public List<LocacaoDTO> listar() {
        return locacaoConvertAssembler.convert_Lista_para_DTO(locacaoService.findAll());
    }

    @GetMapping("/{locacaoId}")
    public LocacaoDTO buscar(@PathVariable Long locacaoId) {
        return locacaoConvertAssembler
                .convert_para_DTO(locacaoService.findById(locacaoId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LocacaoDTO adicionar (@RequestBody @Valid LocacaoInput locacaoInput) {
        return locacaoConvertAssembler
                .convert_para_DTO(locacaoService.save(
                        locacaoConvertDISAssembler.convert_paraClienteDomain(locacaoInput)
                ));
    }

    @PutMapping("/{locacaoId}")
    public LocacaoDTO atualizar (@PathVariable Long locacaoId, @RequestBody @Valid LocacaoInput locacaoInput){
        //TODO se aqui estiver certo refazer assim em todos;
        Locacao locacaoAtual = locacaoService.findById(locacaoId);

        locacaoConvertDISAssembler.copy_paraLocacaoDomain(locacaoInput, locacaoAtual);

        return locacaoConvertAssembler
                .convert_para_DTO(locacaoService.save(locacaoAtual));
    }

    @DeleteMapping("/{locacaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover (@PathVariable Long locacaoId) {
        locacaoService.deleteById(locacaoId);
    }

}
