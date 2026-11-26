package Controller.session;

import context.UserSessionContext;

//controller applicativo degli eventi di sessione: login, logout, registrazione
public class SessionController {

    //riferimento a oggetto istanza di UserSessionContext
    private UserSessionContext session;


    //metodo che verifica se un utente è autenticato
    public boolean isAuthenticated() {
        return session != null;
    }


    //metodo che crea una nuova istanza di UserSessionContext
    public void startSession(/*param che rappresentano la sessione*/) {            //SARA' INVOCATA AL TERMINE DEL LOGIN
        //se esiste una sessione, la termina
        if (session != null) {
            session.terminate();
        }
        //assegna a session il riferimento al nuovo oggetto UserSessionContext
        session = new UserSessionContext(/*param che rappresentano la sessione*/);
    }

    //metodo che restituisce il riferimento all'istanza di UserSessionContext
    public UserSessionContext getSession() {
        //verifica se esiste una sessione utente
        if (session == null) {
            throw new IllegalStateException("Nessun utente autenticato");
        }

        //restituisce il riferimento alla sessione
        return session;
    }


    //metodo che gestisce la disconnessione dell'utente
    public void logout() {                  //SARA' INVOCATA SU RICHIESTA DI LOGOUT

        //se esiste una sessione, la termina e assegna alla variabile session il riferimento a null
        if (session != null) {
            session.terminate();
            session = null;
        }
    }
}
