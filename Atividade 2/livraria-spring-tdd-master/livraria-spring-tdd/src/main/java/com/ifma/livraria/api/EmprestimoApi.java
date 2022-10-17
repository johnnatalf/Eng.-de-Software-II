package com.ifma.livraria.api;

import com.ifma.livraria.dto.EmprestimoDTO;
import com.ifma.livraria.service.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emprestimo")
public class EmprestimoApi {

    @Autowired
    private EmprestimoService emprestimoService;

    @PostMapping("/novo")
    public boolean enviarEmprestimo(@RequestBody EmprestimoDTO emprestimoDTO){
        return emprestimoService.salvarEmprestimo(emprestimoDTO) != null;
    }

    @PostMapping("/devolver")
    public double realizaDevolucao(@RequestBody EmprestimoDTO emprestimoDTO){
        return emprestimoService.realizarDevolucao(emprestimoDTO);
    }

    @GetMapping("/valor")
    public double calcularValor(@RequestBody EmprestimoDTO emprestimoDTO){
        return emprestimoService.calculaValorEmprestimo(emprestimoDTO);
    }
}
