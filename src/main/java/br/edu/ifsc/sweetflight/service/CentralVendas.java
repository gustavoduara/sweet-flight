package br.edu.ifsc.sweetflight.service;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import br.edu.ifsc.sweetflight.model.Voo;
import br.edu.ifsc.sweetflight.model.Passageiro;
import java.util.LinkedHashMap;
import java.util.Map;

/** Guarda os dados em memória e aplica as regras de negócio. */
public class CentralVendas {

    private final Map<String, Voo> voos = new LinkedHashMap<>();
    private final Map<String, Passageiro> passageiros = new LinkedHashMap<>();

    public Voo cadastrarVoo(String codigo, String origem, String destino, LocalDateTime partida,
            int capacidade, BigDecimal tarifa) {
        String chave = normalizarCodigo(codigo);
        if (voos.containsKey(chave)) {
            throw new RegraNegocioException("Já existe um voo com o código " + chave + ".");
        }
        if (partida == null) {
            throw new RegraNegocioException("A data e hora de partida devem ser informadas.");
        }
        if (capacidade <= 0) {
            throw new RegraNegocioException("A capacidade deve ser um número inteiro positivo.");
        }
        if (tarifa == null || tarifa.signum() <= 0) {
            throw new RegraNegocioException("A tarifa deve ser positiva.");
        }
        if (tarifa.stripTrailingZeros().scale() > 2) {
            throw new RegraNegocioException("A tarifa aceita no máximo duas casas decimais.");
        }
        Voo voo = new Voo(chave, exigirTexto(origem, "origem"), exigirTexto(destino, "destino"),
                partida, capacidade, tarifa.setScale(2));
        voos.put(chave, voo);
        return voo;
    }

    public Passageiro cadastrarPassageiro(String codigo, String nome) {
        String chave = normalizarCodigo(codigo);
        if (passageiros.containsKey(chave)) {
            throw new RegraNegocioException("Já existe um passageiro com o código " + chave + ".");
        }
        Passageiro passageiro = new Passageiro(chave, exigirTexto(nome, "nome"));
        passageiros.put(chave, passageiro);
        return passageiro;
    }

    private String normalizarCodigo(String codigo) {
        return exigirTexto(codigo, "código").toUpperCase();
    }

    private String exigirTexto(String texto, String campo) {
        if (texto == null || texto.isBlank()) {
            throw new RegraNegocioException("O campo " + campo + " deve ser preenchido.");
        }
        return texto.trim();
    }
}
