package com.ifma.livraria.exceptions;

import com.ifma.livraria.dto.LivrariaErro;

public class LivrariaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final transient LivrariaErro erro;

    public LivrariaException() {
        super();
        erro = new LivrariaErro(null, null);
    }

    public LivrariaException(final String mensagem, final Throwable causa) {
        super(mensagem, causa);
        erro = new LivrariaErro(mensagem, causa.getStackTrace());
    }

    public LivrariaException(final String mensagem) {
        this(mensagem, mensagem);
    }

    public LivrariaException(final String mensagem, final Object detalhe) {
        super(mensagem);
        erro = new LivrariaErro(mensagem, detalhe);
    }

    public LivrariaErro getErro() {
        return erro;
    }

    @Override
    public String toString() {
        return String.format("LivrariaException [erro=%s]", erro);
    }
}
