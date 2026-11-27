package br.edu.ifsc.sweetflight.service;

import java.util.List;
import java.util.ArrayList;
import br.edu.ifsc.sweetflight.model.Venda;
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
    private final Map<String, Venda> vendas = new LinkedHashMap<>();
    private int proximaVenda = 1;

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

    public Venda registrarVenda(String codigoVoo, String codigoPassageiro) {
        Voo voo = buscarVoo(codigoVoo);
        Passageiro passageiro = buscarPassageiro(codigoPassageiro);
        if (possuiVendaConfirmada(voo, passageiro)) {
            throw new RegraNegocioException(passageiro.getNome()
                    + " já tem uma venda confirmada no voo " + voo.getCodigo() + ".");
        }
        if (!voo.temVaga()) {
            throw new RegraNegocioException("Não há vagas no voo " + voo.getCodigo() + ".");
        }
        String codigo = String.format("V%03d", proximaVenda++);
        Venda venda = new Venda(codigo, voo, passageiro);
        vendas.put(codigo, venda);
        return venda;
    }

    public List<Venda> consultarPorVoo(String codigoVoo) {
        Voo voo = buscarVoo(codigoVoo);
        List<Venda> resultado = new ArrayList<>();
        for (Venda venda : vendas.values()) {
            if (venda.getVoo() == voo) {
                resultado.add(venda);
            }
        }
        return resultado;
    }

    public List<Venda> consultarPorPassageiro(String codigoPassageiro) {
        Passageiro passageiro = buscarPassageiro(codigoPassageiro);
        List<Venda> resultado = new ArrayList<>();
        for (Venda venda : vendas.values()) {
            if (venda.getPassageiro() == passageiro) {
                resultado.add(venda);
            }
        }
        return resultado;
    }

    public Voo buscarVoo(String codigo) {
        String chave = normalizarCodigo(codigo);
        Voo voo = voos.get(chave);
        if (voo == null) {
            throw new RegraNegocioException("Voo " + chave + " não cadastrado.");
        }
        return voo;
    }

    public Passageiro buscarPassageiro(String codigo) {
        String chave = normalizarCodigo(codigo);
        Passageiro passageiro = passageiros.get(chave);
        if (passageiro == null) {
            throw new RegraNegocioException("Passageiro " + chave + " não cadastrado.");
        }
        return passageiro;
    }

    private boolean possuiVendaConfirmada(Voo voo, Passageiro passageiro) {
        for (Venda venda : vendas.values()) {
            if (venda.isConfirmada() && venda.getVoo() == voo
                    && venda.getPassageiro() == passageiro) {
                return true;
            }
        }
        return false;
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
