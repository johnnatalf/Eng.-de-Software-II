package com.example.imobiliaria.domain.service;

import com.example.imobiliaria.domain.exception.EntidadeEmUsoException;
import com.example.imobiliaria.domain.exception.ImovelNaoEncontradoException;
import com.example.imobiliaria.domain.model.Imoveis;
import com.example.imobiliaria.domain.model.Locacao;
import com.example.imobiliaria.domain.repository.ImoveisRepository;
import com.example.imobiliaria.domain.repository.LocacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImovelService {
    private static final String MSG_IMOVEL_EM_USO
            = "Imovel de código %d não pode ser removido, pois está associada a outro item do banco";
    private static final String APARTAMENTO = "Apartamento";
    private static final String CASA = "Casa";

    @Autowired
    private ImoveisRepository imovelRepository;

    @Autowired
    LocacaoRepository locacaoRepository;

    @Transactional
    public Imoveis save(Imoveis imovel) {
        return imovelRepository.save(imovel);
    }

    @Transactional
    public void delete(Long idImovel) {
        try {
            imovelRepository.deleteById(idImovel);
        }catch (EmptyResultDataAccessException e) {
            throw new ImovelNaoEncontradoException(idImovel);
        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format(MSG_IMOVEL_EM_USO, idImovel));
        }
    }

    public List<Imoveis> findAll(){
        List<Imoveis> imoveis = imovelRepository.findAll();
        return imoveis;
    }

    public Imoveis findById(Long idImovel){
        return imovelRepository.findById(idImovel)
                .orElseThrow( () -> new ImovelNaoEncontradoException(idImovel)
                );
    }

    @Transactional
    public Imoveis update(Long idImovel, Imoveis imovel){
        Imoveis imovelParaAtualizar = findById(idImovel);
        imovel.setId(imovelParaAtualizar.getId());

        return save(imovel);
    }

    /** BUSCAR IMOVEIS DO TIPO APARTAMENTO DISPONIVEIS - UTILIZANDO EXPRESSÃO LAMBDA*/
    public List<Imoveis> findActiveApartment(String bairro, String tipoImovel) {
        List<Locacao> locacoes= locacaoRepository.findAll();
        List<Imoveis> imoveisDisponiveis = new ArrayList<>();

        List<Locacao> locacaoFilter= locacoes.stream()
                .filter(locacao -> !locacao.isAtivo()
                        && locacao.getImovel().getTipo_Imovel().equals(APARTAMENTO)
                        && locacao.getImovel().getBairro().equals(bairro))
                .collect(Collectors.toList());


        locacaoFilter.forEach( l -> imoveisDisponiveis.add( l.getImovel() ) );
       return imoveisDisponiveis;
    }

    /**BUSCAR IMOVEIS DO TIPO APARTAMENTO DISPONIVEIS - Utilizando filtro na Query de busca no banco de dados */
    List<Imoveis> findActiveApartmentQueryMethod(String bairro, String tipoImovel) {
        List<Locacao> locacoes = locacaoRepository.alugueAtivosBairro(bairro, tipoImovel);
        List<Imoveis> imoveisDisponiveis = new ArrayList<>();

        locacoes.forEach( l -> imoveisDisponiveis.add(l.getImovel()));
        return imoveisDisponiveis;

    }

    public List<Imoveis> findByPriceAndAvailable(BigDecimal limitedPrice){
        List<Imoveis> imoveisFilter = new ArrayList<>();

        List<Locacao> todasLocaoes = locacaoRepository.findAll();

        List<Locacao> locacoesFilter = todasLocaoes.stream()
                .filter( l -> l.getImovel().getValor_aluguel_suge().compareTo(limitedPrice) <= 0 )
                .filter(l-> !l.isAtivo())
                .collect(Collectors.toList());

        if(locacoesFilter != null || !locacoesFilter.isEmpty() ){
            locacoesFilter.forEach( l -> imoveisFilter.add(l.getImovel()) );
        }

        return imoveisFilter;
    }
}
