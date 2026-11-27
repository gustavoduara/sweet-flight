package br.edu.ifsc.sweetflight.ui;

import br.edu.ifsc.sweetflight.service.CentralVendas;
import br.edu.ifsc.sweetflight.service.RegraNegocioException;
import java.util.NoSuchElementException;

/** Entrada e saída do console. As regras ficam na CentralVendas. */
public class MenuConsole {

    private final CentralVendas central;
    private final LeitorEntrada leitor = new LeitorEntrada();

    public MenuConsole(CentralVendas central) {
        this.central = central;
    }

    public void iniciar() {
        String opcao = "";
        while (!opcao.equals("0")) {
            exibirOpcoes();
            try {
                opcao = leitor.lerTexto("Opção");
                executar(opcao);
            } catch (RegraNegocioException e) {
                System.out.println("Operação recusada: " + e.getMessage());
            } catch (NoSuchElementException e) {
                opcao = "0"; // a entrada foi fechada
            }
        }
        System.out.println("Programa encerrado.");
    }

    private void exibirOpcoes() {
        System.out.println();
        System.out.println("=== SWEET FLIGHT - VENDA DE PASSAGENS ===");
        System.out.println("1 - Cadastrar voo");
        System.out.println("2 - Cadastrar passageiro");
        System.out.println("0 - Encerrar");
    }

    private void executar(String opcao) {
        switch (opcao) {
            case "1" -> cadastrarVoo();
            case "2" -> cadastrarPassageiro();
            case "0" -> { }
            default -> System.out.println("Opção inválida.");
        }
    }

    private void cadastrarVoo() {
        central.cadastrarVoo(
                leitor.lerTexto("Código do voo"),
                leitor.lerTexto("Origem"),
                leitor.lerTexto("Destino"),
                leitor.lerDataHora("Partida"),
                leitor.lerInteiro("Capacidade de assentos"),
                leitor.lerValor("Tarifa (R$)"));
        System.out.println("Voo cadastrado.");
    }

    private void cadastrarPassageiro() {
        central.cadastrarPassageiro(
                leitor.lerTexto("Código do passageiro"),
                leitor.lerTexto("Nome completo"));
        System.out.println("Passageiro cadastrado.");
    }
}
