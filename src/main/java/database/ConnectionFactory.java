package database;

import config.GestoreConfigurazione;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**classe con responsabilità di:
 * 1-creazione connessione al DB
 * 2-mantenimento della connessione globale
 * -----
 * la classe è singleton: viene creata un'unica connessione
 * condivisa da tutte le classi del software.
 */
public class ConnectionFactory {

    //riferimento all'unica istanza di ConnectionFactory
    private static ConnectionFactory instance;

    private final String connectionURL;
    private final String dbUser;
    private final String dbPassword;


    // Costruttore privato: impedisce l'istanziazione esterna
    private ConnectionFactory() {

        GestoreConfigurazione gestoreConfigurazione = GestoreConfigurazione.getInstance();

        connectionURL = gestoreConfigurazione.getConnectionURL();
        dbUser = gestoreConfigurazione.getDBUser();
        dbPassword = gestoreConfigurazione.getDBPassword();
    }

    // Getter dell'unica istanza di ConnectionFactory
    public static ConnectionFactory getInstance() {

        if (instance == null) {
            instance = new ConnectionFactory();
        }

        return instance;
    }


    //metodo con responsabilità di creare e restituire una connessione JDBC
    public Connection getConnection() throws SQLException {

        return DriverManager.getConnection(
                connectionURL,
                dbUser,
                dbPassword
        );
    }
}
