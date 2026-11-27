package br.edu.ifsc.sweetflight.model;

import java.math.BigDecimal;

public class Venda {

    private final String codigo;
    private final Voo voo;
    private final Passageiro passageiro;
    private final BigDecimal valor;
    private SituacaoVenda situacao;

    // Criar a venda é o que confirma o pagamento (simulado) e ocupa a vaga.
    public Venda(String codigo, Voo voo, Passageiro passageiro) {
        voo.ocuparAssento();
        this.codigo = codigo;
        this.voo = voo;
        this.passageiro = passageiro;
        this.valor = voo.getTarifa();
        this.situacao = SituacaoVenda.CONFIRMADA;
    }

    public String getCodigo() {
        return codigo;
    }

    public Voo getVoo() {
        return voo;
    }

    public Passageiro getPassageiro() {
        return passageiro;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public SituacaoVenda getSituacao() {
        return situacao;
    }

    public boolean isConfirmada() {
        return situacao == SituacaoVenda.CONFIRMADA;
    }

    // A checagem da situação impede que a mesma vaga seja liberada duas vezes.
    public void cancelar() {
        if (!isConfirmada()) {
            throw new IllegalStateException("A venda " + codigo + " já está cancelada.");
        }
        situacao = SituacaoVenda.CANCELADA;
        voo.liberarAssento();
    }
}
