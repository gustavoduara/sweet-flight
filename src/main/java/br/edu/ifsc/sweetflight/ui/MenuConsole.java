package br.edu.ifsc.sweetflight.ui;

import br.edu.ifsc.sweetflight.model.Venda;
import br.edu.ifsc.sweetflight.model.Voo;
import br.edu.ifsc.sweetflight.service.CentralVendas;
import br.edu.ifsc.sweetflight.service.RegraNegocioException;
import br.edu.ifsc.sweetflight.service.ResumoVoo;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
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
        System.out.println("3 - Registrar venda");
        System.out.println("4 - Consultar vendas por voo");
        System.out.println("5 - Consultar vendas por passageiro");
        System.out.println("6 - Cancelar venda");
        System.out.println("7 - Resumo por voo");
        System.out.println("0 - Encerrar");
    }

    private void executar(String opcao) {
        switch (opcao) {
            case "1" -> cadastrarVoo();
            case "2" -> cadastrarPassageiro();
            case "3" -> registrarVenda();
            case "4" -> exibirVendas(central.consultarPorVoo(leitor.lerTexto("Código do voo")));
            case "5" -> exibirVendas(central.consultarPorPassageiro(
                    leitor.lerTexto("Código do passageiro")));
            case "6" -> cancelarVenda();
            case "7" -> exibirResumo();
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

    private void registrarVenda() {
        Venda venda = central.registrarVenda(
                leitor.lerTexto("Código do voo"),
                leitor.lerTexto("Código do passageiro"));
        System.out.println("Pagamento confirmado. Venda registrada:");
        exibirVenda(venda);
    }

    private void exibirVenda(Venda venda) {
        System.out.println("Venda " + venda.getCodigo()
                + " | Voo " + venda.getVoo().getCodigo()
                + " | Passageiro " + venda.getPassageiro().getCodigo()
                + " - " + venda.getPassageiro().getNome()
                + " | " + formatarValor(venda.getValor())
                + " | " + venda.getSituacao());
    }

    private String formatarValor(BigDecimal valor) {
        return String.format(Locale.forLanguageTag("pt-BR"), "R$ %,.2f", valor);
    }

    private void exibirVendas(List<Venda> vendas) {
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda encontrada.");
        }
        for (Venda venda : vendas) {
            exibirVenda(venda);
        }
    }

    private void cancelarVenda() {
        Venda venda = central.cancelarVenda(leitor.lerTexto("Código da venda"));
        System.out.println("Venda cancelada e vaga liberada:");
        exibirVenda(venda);
    }

    private void exibirResumo() {
        ResumoVoo resumo = central.resumirVoo(leitor.lerTexto("Código do voo"));
        Voo voo = resumo.voo();
        System.out.println("Voo " + voo.getCodigo() + " | " + voo.getOrigem() + " -> "
                + voo.getDestino() + " | " + voo.getPartida().format(LeitorEntrada.FORMATO_DATA));
        System.out.println("Assentos ocupados: " + resumo.assentosOcupados());
        System.out.println("Assentos disponíveis: " + resumo.assentosDisponiveis());
        System.out.println("Total de vendas confirmadas: "
                + formatarValor(resumo.totalConfirmado()));
    }
}
