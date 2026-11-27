package br.edu.ifsc.sweetflight.service;

import br.edu.ifsc.sweetflight.model.Passageiro;
import java.util.LinkedHashMap;
import java.util.Map;

/** Guarda os dados em memória e aplica as regras de negócio. */
public class CentralVendas {

    private final Map<String, Passageiro> passageiros = new LinkedHashMap<>();

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
