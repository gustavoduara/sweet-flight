package br.edu.ifsc.sweetflight.service;

/** Operação recusada. A mensagem informa o motivo ao atendente. */
public class RegraNegocioException extends RuntimeException {

    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
