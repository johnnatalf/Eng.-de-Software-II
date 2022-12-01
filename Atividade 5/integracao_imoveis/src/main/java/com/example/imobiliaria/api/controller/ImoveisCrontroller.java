package com.example.imobiliaria.api.controller;

import com.example.imobiliaria.api.assemblerConvert.ImoveisConvertAssembler;
import com.example.imobiliaria.api.assemblerConvert.ImoveisConvertDISAssembler;
import com.example.imobiliaria.api.model.dto.ClienteDTO;
import com.example.imobiliaria.api.model.dto.ImoveisDTO;
import com.example.imobiliaria.api.model.input.ClienteInput;
import com.example.imobiliaria.api.model.input.ImovelInput;
import com.example.imobiliaria.domain.service.ImovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/imoveis")
public class ImoveisCrontroller {

    @Autowired
    private ImovelService imovelService;

    @Autowired
    private ImoveisConvertAssembler imoveisConvertAssembler;

    @Autowired
    private ImoveisConvertDISAssembler imoveisConvertDISAssembler;

    @GetMapping
    public List<ImoveisDTO> listar(){
        return imoveisConvertAssembler
                .convert_Lista_para_DTO(imovelService.findAll());
    }

    @GetMapping("/{imoveilId}")
    public ImoveisDTO buscar(@PathVariable Long imoveilId){
        return imoveisConvertAssembler
                .convert_para_DTO(imovelService.findById(imoveilId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ImoveisDTO adicionar(@RequestBody @Valid ImovelInput imovelInput) {
        return imoveisConvertAssembler
                .convert_para_DTO(imovelService.save(
                        imoveisConvertDISAssembler.convert_paraClienteDomain(imovelInput)
                ));
    }

    @PutMapping("/{imoveilId}")
    public ImoveisDTO atualizar(@PathVariable Long imoveilId, @RequestBody @Valid ImovelInput imovelInput) {
        return imoveisConvertAssembler
                .convert_para_DTO(imovelService.update(
                        imoveilId, imoveisConvertDISAssembler.convert_paraClienteDomain(imovelInput)
                ));
    }

    @DeleteMapping("/{imoveilId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long imoveilId){
        imovelService.delete(imoveilId);
    }
}
