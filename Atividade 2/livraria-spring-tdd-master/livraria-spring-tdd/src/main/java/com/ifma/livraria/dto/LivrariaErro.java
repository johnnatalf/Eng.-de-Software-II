package com.ifma.livraria.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(value = NON_NULL)
public class LivrariaErro {

    private String mensagem;
    private Object detalhe;

    public LivrariaErro() {
        this(null, null);
    }

    public void setMensagem(final String mensagem) {
        this.mensagem = mensagem;
    }

    public void setDetalhe(final Object detalhe) {
        this.detalhe = detalhe;
    }

    @Override
    public String toString() {
        return String.format("LivrariaErro [mensagem=%s, detalhe=%s]", mensagem, detalhe);
    }
}
