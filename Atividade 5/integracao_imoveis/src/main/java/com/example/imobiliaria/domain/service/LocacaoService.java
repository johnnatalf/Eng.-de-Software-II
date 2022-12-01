package com.example.imobiliaria.domain.service;

import com.example.imobiliaria.domain.exception.*;
import com.example.imobiliaria.domain.model.Cliente;
import com.example.imobiliaria.domain.model.Imoveis;
import com.example.imobiliaria.domain.model.Locacao;
import com.example.imobiliaria.domain.repository.LocacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LocacaoService {

    private static final String MSG_LOCACAO_EM_USO
            = "Imovel de código %d não pode ser removido, pois está associada a outro item do banco";

    @Autowired
    private LocacaoRepository locacaoRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ImovelService imovelService;

    public List<Locacao> findAll() {
        return locacaoRepository.findAll();
    }

    public Locacao findById(Long idLocacao) {
        return locacaoRepository.findById(idLocacao)
                .orElseThrow( () -> new LocacaoNaoEncontradaException(idLocacao)
                );
    }

    @Transactional
    public Locacao save (Locacao locacao) {
        try{
            Long imovelId = locacao.getImovel().getId();
            Long clienteId = locacao.getCliente().getId();

            Cliente cliente = clienteService.findById(clienteId);
            Imoveis imovel = imovelService.findById(imovelId);

            locacao.setImovel(imovel);
            locacao.setCliente(cliente);

            return locacaoRepository.save(locacao);
        }catch(ClienteNaoEncontradoException e){
            throw new NegocioException(e.getMessage(), e);
        }catch(ImovelNaoEncontradoException e){
            throw new NegocioException(e.getMessage(), e);
        }

    }

    @Transactional
    public Locacao update (Locacao locacao) {

        try{
            Long imovelId = locacao.getImovel().getId();
            Long clienteId = locacao.getCliente().getId();

            Cliente cliente = clienteService.findById(clienteId);
            Imoveis imovel = imovelService.findById(imovelId);
            Locacao locacaoAtualizar = findById(locacao.getId());

            locacao.setImovel(imovel);
            locacao.setCliente(cliente);
            locacao.setId(locacaoAtualizar.getId());

            return locacaoRepository.save(locacao);

        }catch(ClienteNaoEncontradoException e){
            throw new NegocioException(e.getMessage(), e);
        }catch(ImovelNaoEncontradoException e){
            throw new NegocioException(e.getMessage(), e);
        }

    }

    @Transactional
    public void deleteById(Long idLocacao) {
        try{
            locacaoRepository.deleteById(idLocacao);
        }catch(EmptyResultDataAccessException e) {
            throw new LocacaoNaoEncontradaException(idLocacao);
        }catch(DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_LOCACAO_EM_USO, idLocacao));
        }

    }

}
