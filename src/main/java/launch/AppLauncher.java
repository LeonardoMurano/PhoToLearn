package launch;

import config.ConfigurationManager;
import config.TipologiaGUI;
import context.ApplicationContext;
import ui.cli.CliEngine;

//classe con responsabilità di avvio del software
public class AppLauncher {

    static void main() {

        ConfigurationManager gestoreConfigurazione = ConfigurationManager.getInstance();

        //recupero della proprietà TipologiaGUI definita
        TipologiaGUI tipologiaGUI =
                gestoreConfigurazione.getTipologiaGUI();

        switch (tipologiaGUI) {

            case CLI: {
                ApplicationContext applicationContext = new ApplicationContext();
                CliEngine cliEngine = new CliEngine(applicationContext.getSessionController());
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
