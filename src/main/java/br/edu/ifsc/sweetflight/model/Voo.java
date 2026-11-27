package br.edu.ifsc.sweetflight.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Voo {

    private final String codigo;
    private final String origem;
    private final String destino;
    private final LocalDateTime partida;
    private final int capacidade;
    private final BigDecimal tarifa;
    private int assentosOcupados;

    public Voo(String codigo, String origem, String destino, LocalDateTime partida,
            int capacidade, BigDecimal tarifa) {
        this.codigo = codigo;
        this.origem = origem;
        this.destino = destino;
        this.partida = partida;
        this.capacidade = capacidade;
        this.tarifa = tarifa;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getOrigem() {
        return origem;
    }

    public String getDestino() {
        return destino;
    }

    public LocalDateTime getPartida() {
        return partida;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public BigDecimal getTarifa() {
        return tarifa;
    }

    public int getAssentosOcupados() {
        return assentosOcupados;
    }

    public int getAssentosDisponiveis() {
        return capacidade - assentosOcupados;
    }

    public boolean temVaga() {
        return getAssentosDisponiveis() > 0;
    }

    // Não existe setAssentosOcupados: a contagem só muda de um em um
    // e nunca passa da capacidade nem fica negativa.
    public void ocuparAssento() {
        if (!temVaga()) {
            throw new IllegalStateException("O voo " + codigo + " está lotado.");
        }
        assentosOcupados++;
    }

    public void liberarAssento() {
        if (assentosOcupados == 0) {
            throw new IllegalStateException("O voo " + codigo + " não tem assentos ocupados.");
        }
        assentosOcupados--;
    }
}
