package com.ifma.livraria.exceptions;

import static com.ifma.livraria.utils.Constants.RESPONSE_204;

public class NaoEncontradoException extends LivrariaException {

    private static final long serialVersionUID = 1L;

    public NaoEncontradoException() {
        getErro().setMensagem(RESPONSE_204);
        getErro().setDetalhe(null);
    }

    public NaoEncontradoException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NaoEncontradoException(final String message) {
        super(message);
    }

    public NaoEncontradoException(final Object detalhe) {
        super(RESPONSE_204, detalhe);
    }

    public NaoEncontradoException(final Throwable cause) {
        super(RESPONSE_204, cause);
    }

    public NaoEncontradoException(final String mensagem, final Object detalhe) {
        super(mensagem, detalhe);
    }
}
