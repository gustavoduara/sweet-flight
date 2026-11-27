package br.edu.ifsc.sweetflight;

import br.edu.ifsc.sweetflight.service.CentralVendas;
import br.edu.ifsc.sweetflight.ui.MenuConsole;

public class Main {

    public static void main(String[] args) {
        new MenuConsole(new CentralVendas()).iniciar();
    }
}
