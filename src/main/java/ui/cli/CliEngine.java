package ui.cli;

import Controller.session.SessionController;
import context.UserSessionContext;
import ui.cli.io.InputReader;
import ui.cli.io.OutputPrinter;

import java.util.Scanner;

//classe con responsabilità di coordinamento della CLI
public class CliEngine {

    //componente responsabile della gestione della sessione
    private final SessionController sessionController;
    //componente responsabile della ricezione dell'input dell'utente
    private final InputReader inputReader;
    //componente responsabile della stampa sul terminale
    private final OutputPrinter outputPrinter;


    //costruttore CliEngine
    public CliEngine(SessionController sessionController) {
        //assegna riferimento di sessionController a this.sessionController
        this.sessionController = sessionController;

        //crea oggetto di OutputPrinter e ne assegna il riferimento a this.outputPrinter
        this.outputPrinter = new OutputPrinter();

        //crea uno scanner che legge dallo standard input (System.in)
        Scanner scanner = new Scanner(System.in);
        //crea un oggetto di InputReader e ne assegna il riferimento a this.inputReader
        this.inputReader = new InputReader(scanner, outputPrinter);
    }


    //metodo che restituisce la sessione utente corrente
    public UserSessionContext getSessionContext() {
        return sessionController.getSession();
    }

    //metodo che restituisce il riferimento all'oggetto InputReader
    public InputReader getInput() {
        return inputReader;
    }

    //metodo che restituisce il riferimento all'oggetto OutputPrinter
    public OutputPrinter getPrinter() {
        return outputPrinter;
    }


    //metodo di avvio della CLI
    public void start() {
        //mostra la schermata iniziale
        CliView currentView = initialView();

        //ciclo while che prosegue finchè c'è una view successiva da mostrare
        while (currentView != null) {

            //esegui la view corrente da mostrare
            //il metodo restituisce la view successiva da mostrare
            CliView nextView = currentView.execute(this);

            // Se la sessione è scaduta, torna all'autenticazione:
            if (nextView != null                                    //se esiste view successiva da mostrare
                    && !(nextView instanceof AuthenticationCli)     //&& nextView non è la view di autenticazione
                    && !sessionController.isAuthenticated()) {      //&& la sessione è scaduta

                //termina l'esecuzione della view corrente
                currentView.stop();
                //assegna a currentView il riferimento a una nuova istanza di AuthenticationCli
                currentView = new AuthenticationCli();
                //passa alla successiva iterazione del while(.)
                continue;
            }

            //altrimenti, se la sessione non è scaduta:
            //se currentView!=nextView termina l'esecuzione della view corrente
            if (currentView != nextView) {
                currentView.stop();
            }

            //assegna a currentView il riferimento a nextView
            currentView = nextView;
        }

        //se non esiste view successiva da mostrare, termina esecuzione applicazione
        outputPrinter.printInfo("Chiusura dell'applicazione.");
    }


    //metodo che determina la view iniziale
    private CliView initialView() {
        return new AuthenticationCli();
    }
}
