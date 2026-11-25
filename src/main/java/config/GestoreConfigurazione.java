package config;

import exception.ConfigurazioneException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//classe con responsabilità di gestione della configurazione del sistema
public class GestoreConfigurazione {

    private final Properties properties;

    //costruttore senza parametri di GestoreConfigurazione
    public GestoreConfigurazione() {
        properties = new Properties();
        caricaProperties();
    }

    //metodo con responsabilità di caricare tutte le proprietà definite in 'config.properties' all'interno di properties
    private void caricaProperties() {

        //blocco try-with-resources:
        // invoca close(.) dell'istanza di InputStream al termine del blocco try
        try(InputStream input = getClass().getResourceAsStream("/config.properties")) {

            //gestione caso in cui la risorsa non viene trovata
            if (input == null) {
                throw new ConfigurazioneException(
                        "Impossibile trovare config.properties nel classpath"
                );
            }

            //carica tutte le proprietà contenute in input, all'interno di properties
            properties.load(input);

        } catch (IOException ex) {
            throw new ConfigurazioneException(
                    "Errore durante la lettura di config.properties",
                    ex
            );
        }
    }

    //metodo con responsabilità di recupero della proprietà 'tipologiaGUI'
    public TipologiaGUI getTipologiaGUI() {

        //recupera valore associato alla proprietà 'interfaccia'
        String stringaTipologiaGUI = properties.getProperty("tipologiaGUI");

        //verifica se la proprietà 'interfaccia' esiste e possiede valore significativo
        if (stringaTipologiaGUI == null || stringaTipologiaGUI.isBlank()) {
            throw new ConfigurazioneException(
                    "Proprietà 'tipologiaGUI' mancante o vuota"
            );
        }

        try {
            //restituisci il valore della proprietà 'tipologiaGUI', dopo averla formattata in un valore dell'enum TipologiaGUI
            return TipologiaGUI.valueOf(
                    stringaTipologiaGUI.trim().toUpperCase()
            );

        } catch (IllegalArgumentException ex) {
            throw new ConfigurazioneException(
                    "Tipologia di GUI non riconosciuta: " + stringaTipologiaGUI,
                    ex
            );
        }
    }
}
