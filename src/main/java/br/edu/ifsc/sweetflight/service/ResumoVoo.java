package br.edu.ifsc.sweetflight.service;

import br.edu.ifsc.sweetflight.model.Voo;
import java.math.BigDecimal;

/** Dados do resumo de um voo, para o menu apenas exibir. */
public record ResumoVoo(Voo voo, int assentosOcupados, int assentosDisponiveis,
        BigDecimal totalConfirmado) {
}
