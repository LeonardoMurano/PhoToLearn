package context;

import Controller.session.SessionController;

//classe con responsabilità di rappresentazione delle componenti condivise del sistema
//le componenti rappresentate da ApplicationContext hanno ciclo di vita uguale all'esecuzione dell'applicazione
public class ApplicationContext {

    // Riferimento al controller responsabile della gestione della sessione utente
    private final SessionController sessionController;


    // Costruttore ApplicationContext
    public ApplicationContext() {

        // Crea il controller per la gestione della sessione e ne assegna il riferimento a this.sessionController
        this.sessionController = new SessionController();
    }


    // getter del riferimento al SessionController
    public SessionController getSessionController() {
        return sessionController;
    }
}
