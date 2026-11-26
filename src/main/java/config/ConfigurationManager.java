package config;

import exception.ConfigurationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**classe con responsabilità di:
 * 1-caricamento in memoria principale delle proprietà di configurazione del sistema
 * 2-mantenimento delle proprietà di configurazione del sistema
 * -----
 * la classe è singleton: le proprietà contenute in config.properties
 * sono condivise da tutte le classi del software.
 */
public class ConfigurationManager {

    //riferimento all'unica istanza di GestoreConfigurazione
    private static ConfigurationManager instance;

    private final Properties properties;


    // Costruttore privato: impedisce l'istanziazione esterna
    private ConfigurationManager() {
        properties = new Properties();
        caricaProperties();
    }

    // Getter dell'unica istanza di GestoreConfigurazione
    public static ConfigurationManager getInstance() {

        if (instance == null) {
            instance = new ConfigurationManager();
        }

        return instance;
    }


    //metodo con responsabilità di caricare tutte le proprietà definite in 'config.properties' all'interno di properties
    private void caricaProperties() {

        //blocco try-with-resources:
        // invoca close(.) dell'istanza di InputStream al termine del blocco try
        try(InputStream input = getClass().getResourceAsStream("/config.properties")) {

            //gestione caso in cui la risorsa non viene trovata
            if (input == null) {
                throw new ConfigurationException(
                        "Impossibile trovare config.properties nel classpath"
                );
            }

            //carica tutte le proprietà contenute in input, all'interno di properties
            properties.load(input);

        } catch (IOException ex) {
            throw new ConfigurationException(
                    "Errore durante la lettura di config.properties",
                    ex
            );
        }
    }

    //metodo con responsabilità di recuperare e validare una proprietà
    private String getProprieta(String nomeProprieta) {

        //recupera valore associato alla proprietà nomeProprieta
        String valore = properties.getProperty(nomeProprieta);

        //verifica se la proprietà nomeProprieta esiste e possiede valore significativo
        if (valore == null || valore.isBlank()) {
            throw new ConfigurationException(
                    "Proprietà '" + nomeProprieta + "' mancante o vuota"
            );
        }

        return valore.trim();
    }

    //getter della tipologia di GUI da utilizzare
    public TipologiaGUI getTipologiaGUI() {

        String valoreTipologiaGUI = getProprieta("TIPOLOGIA_GUI");

        //verifica se 'TIPOLOGIA_GUI' possiede un valore dell'enum TipologiaGUI
        try {
            return TipologiaGUI.valueOf(
                    valoreTipologiaGUI.toUpperCase()
            );

        } catch (IllegalArgumentException ex) {
            throw new ConfigurationException(
                    "Tipologia di GUI non riconosciuta: " + valoreTipologiaGUI,
                    ex
            );
        }
    }

    //getter dell'URL di connessione al database
    public String getConnectionURL() {
        return getProprieta("CONNECTION_URL");
    }

    //getter dello username dell'utente MySQL
    public String getDBUser() {
        return getProprieta("DB_USER");
    }

    //getter della password dell'utente MySQL
    public String getDBPassword() {
        return getProprieta("DB_PASS");
    }
}
