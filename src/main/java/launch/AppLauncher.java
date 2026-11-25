package launch;

import config.GestoreConfigurazione;
import config.TipologiaGUI;
import ui.cli.CliEngine;

//classe con responsabilità di avvio del software
public class AppLauncher {

    static void main() {

        GestoreConfigurazione gestoreConfigurazione = GestoreConfigurazione.getInstance();

        //recupero della proprietà TipologiaGUI definita
        TipologiaGUI tipologiaGUI =
                gestoreConfigurazione.getTipologiaGUI();

        switch (tipologiaGUI) {

            case CLI: {
                CliEngine cliEngine = new CliEngine();
                cliEngine.start();
                break;
            }

            case JAVAFX: {

                //Application.launch(JavaFXApp.class, args);
                //break;

                throw new UnsupportedOperationException(
                        "Interfaccia JavaFX non ancora implementata"
                );
            }
        }
    }
}
