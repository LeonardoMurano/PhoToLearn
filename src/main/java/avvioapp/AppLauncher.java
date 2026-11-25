package avvioapp;

import config.GestoreConfigurazione;
import config.TipologiaGUI;
import context.ApplicazioneContext;

//classe con responsabilità di avvio del software
public class AppLauncher {

    static void main(String[] args) {

        GestoreConfigurazione gestoreConfigurazione = new GestoreConfigurazione();

        //recupero della proprietà TipologiaGUI definita
        TipologiaGUI tipologiaGUI =
                gestoreConfigurazione.getTipologiaGUI();

        switch (tipologiaGUI) {

            case CLI: {
                ApplicazioneContext applicazioneContext = new ApplicazioneContext();
                CLIApp.avvia();
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
