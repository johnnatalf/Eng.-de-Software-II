package com.example.imobiliaria.domain.service;


import com.example.imobiliaria.domain.exception.NegocioException;
import com.example.imobiliaria.domain.model.Alugueis;
import com.example.imobiliaria.domain.model.Imoveis;
import com.example.imobiliaria.domain.model.Locacao;
import com.example.imobiliaria.domain.repository.AlugueisRepository;
import com.example.imobiliaria.domain.repository.LocacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AluguelService {

    @Autowired
    private AlugueisRepository repository;

    @Autowired
    private LocacaoService locacaoService;

    @Autowired
    private LocacaoRepository locacaoRepository;

    public List<Locacao> findAll() {
        List<Locacao> alugueisList = locacaoRepository.alugueisAtivos();
        return alugueisList;
    }

    public List<Alugueis> findAllAlugueis(){
        return repository.findAll();
    }

    public Alugueis findById(Long idLocacao){

        return repository.findById(idLocacao)
                .orElseThrow( () -> new NegocioException("O aluguel com id invalido"));
    }

    public void ativarLocacao(Long idLocacao){
        /**metodo ativa uma locação*/

        Locacao locacao = locacaoRepository.findByAtivoAndId(false, idLocacao)
                .orElseThrow( () -> new NegocioException("O aluguel informado já está ativo ou id invalido")
        );

        locacao.setAtivo(true);
        locacaoService.save(locacao);
    }

    public void desativarLocacao(Long idLocacao){
        /**metodo desativa uma locação */

        Locacao locacao = locacaoRepository.findByAtivoAndId(true, idLocacao)
                .orElseThrow( () -> new NegocioException("O aluguel informado nao está ativo ou id invalido")
                );

        locacao.setAtivo(false);
        locacaoService.save(locacao);
    }

    public List<Alugueis> findAllByClienteName(String clienteName){
        return repository.findAllByLocacaoCliente_Nome(clienteName);
    }

    /** @buscar-alugueis-pagos*/
    public List<Alugueis> findAllByClienteNameAndPaid(String clienteName){
        List<Alugueis> all = repository.findAllByLocacaoCliente_Nome(clienteName);

        List<Alugueis> delayedPayments = all.stream()
                .filter(
                        al -> {
                            return al.getValor_pago() != null || ! al.getValor_pago().equals(0);
                        }
                )
                .collect(Collectors.toList());

        return delayedPayments;
    }

    /** @buscar-pagamentos-com-atraso*/
    public List<Alugueis> findAllByClienteNameAndPaidLate(String clienteName){
        List<Alugueis> all = repository.findAllByLocacaoCliente_Nome(clienteName);

        List<Alugueis> delayedPayments = all.stream()
                .filter(
                        al -> al.getDt_pagamento().after(al.getDt_vencimento())
                )
                .collect(Collectors.toList());

        return delayedPayments;
    }

    public Alugueis registrarPagamento(BigDecimal valorPago, Imoveis imovelAlugado){

        Alugueis aluguelPago = repository.findByLocacao_Imovel_Id(imovelAlugado.getId());

        if( valorPago.compareTo(aluguelPago.getLocacao().getValor_alugue()) >= 0 ){
            aluguelPago.setValor_pago(valorPago);
            aluguelPago.setDt_pagamento( new Date(System.currentTimeMillis()) );
            aluguelPago.setObs("Proximo vencimento em: " +  LocalDate.now( ).plusDays(30) );

            repository.save(aluguelPago);
            return aluguelPago;
        }else{
            throw new NegocioException("Pagamento com valor inferior ao preço do aluguel");
        }

    }

}
