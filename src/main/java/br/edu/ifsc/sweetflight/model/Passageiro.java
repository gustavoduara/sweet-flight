package br.edu.ifsc.sweetflight.model;

public class Passageiro {

    private final String codigo;
    private final String nome;

    public Passageiro(String codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }
}
