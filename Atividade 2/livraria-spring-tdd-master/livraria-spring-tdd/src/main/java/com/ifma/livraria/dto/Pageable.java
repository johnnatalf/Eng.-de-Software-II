package com.ifma.livraria.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Pageable {

    private Integer paginaAtual;
    private Integer tamanhoPagina;
    private Integer totalRegistros;
   
    public Pageable(Integer paginaAtual, Integer tamanhoPagina) {
        this.paginaAtual = paginaAtual;
        this.tamanhoPagina = tamanhoPagina;
    }

    public Integer getPaginaAtual() {
        return paginaAtual == null ? 0 : this.paginaAtual;
    }

    public Integer getTamanhoPagina() {
        return tamanhoPagina == null ? this.getTotalRegistros() : this.tamanhoPagina;
    }

    public Integer getTotalRegistros() {
        return totalRegistros == null ? 0 : this.totalRegistros;
    }

    /**
     * getOffSet.
     * @return
     */
    @JsonIgnore
    public Integer getOffset() {
        return this.getPaginaAtual() == null || this.getTamanhoPagina() == null ? 0 : 
            this.getPaginaAtual() * this.getTamanhoPagina();
    }
    
    /**
     * Usado para paginar em lista.
     * @return
     */
    @JsonIgnore
    public Integer getToIndex() {
        return this.getPaginaAtual() + this.getTamanhoPagina() < this.getTotalRegistros() - 1
                ? this.getTotalRegistros() - 1 : this.getPaginaAtual() + this.getTamanhoPagina();
    }
    
    /**
     * Usado para paginar em lista.
     * @return
     */
    @JsonIgnore
    public Integer getFromIndex() {
        return this.getPaginaAtual() * this.getTamanhoPagina() > this.getTotalRegistros() - 1
                ? this.getTotalRegistros() - 1 : this.getPaginaAtual() * this.getTamanhoPagina();
    }
}
