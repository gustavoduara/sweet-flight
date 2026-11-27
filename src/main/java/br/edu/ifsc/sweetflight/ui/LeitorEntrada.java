package br.edu.ifsc.sweetflight.ui;

import br.edu.ifsc.sweetflight.service.RegraNegocioException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Scanner;

/** Lê o que o atendente digita e converte para os tipos usados pelo sistema. */
public class LeitorEntrada {

    static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter
            .ofPattern("dd/MM/uuuu HH:mm")
            .withResolverStyle(ResolverStyle.STRICT);

    private final Scanner scanner = new Scanner(System.in, charsetDoConsole());

    private static Charset charsetDoConsole() {
        String nome = System.getProperty("stdin.encoding", System.getProperty("native.encoding"));
        return nome == null ? Charset.defaultCharset() : Charset.forName(nome);
    }

    public String lerTexto(String rotulo) {
        System.out.print(rotulo + ": ");
        return scanner.nextLine().trim();
    }

    public int lerInteiro(String rotulo) {
        String texto = lerTexto(rotulo);
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            throw new RegraNegocioException("\"" + texto + "\" não é um número inteiro válido.");
        }
    }

    /** Aceita 250, 250.00, 250,00 e 1.250,00. */
    public BigDecimal lerValor(String rotulo) {
        String texto = lerTexto(rotulo);
        String numero = texto.contains(",") ? texto.replace(".", "").replace(',', '.') : texto;
        if (!numero.matches("-?\\d+(\\.\\d{1,2})?")) {
            throw new RegraNegocioException("\"" + texto
                    + "\" não é um valor válido. Use o formato 250,00.");
        }
        return new BigDecimal(numero);
    }

    public LocalDateTime lerDataHora(String rotulo) {
        String texto = lerTexto(rotulo + " (dd/mm/aaaa hh:mm)");
        try {
            return LocalDateTime.parse(texto, FORMATO_DATA);
        } catch (DateTimeParseException e) {
            throw new RegraNegocioException("Data e hora inválidas: \"" + texto + "\".");
        }
    }
}
