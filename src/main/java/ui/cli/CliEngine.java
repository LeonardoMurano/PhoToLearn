package ui.cli;

import Controller.session.SessionController;

import java.util.Scanner;


//DA CONTROLLARE E TERMINARE
public class CliEngine {
    private final SessionController sessionController;
    private final InputReader inputReader;
    private final OutputPrinter outputPrinter;

    public CliEngine(SessionController sessionController) {
        this.sessionController = sessionController;

        this.outputPrinter = new OutputPrinter();

        Scanner scanner = new Scanner(System.in);
        this.inputReader = new InputReader(scanner, outputPrinter);
    }

    public UserSessionContext getSessionContext() {
        return sessionController.getSession();
    }

    public InputReader getInput() {
        return inputReader;
    }

    public OutputPrinter getPrinter() {
        return outputPrinter;
    }

    public void start() {
        CliView currentView = initialView();

        while (currentView != null) {

            CliView nextView = currentView.execute(this);

            // Se la sessione è scaduta, torna all'autenticazione.
            if (nextView != null
                    && !(nextView instanceof AuthenticationCli)
                    && !sessionController.isAuthenticated()) {

                currentView.stop();
                currentView = new AuthenticationCli();
                continue;
            }

            if (currentView != nextView) {
                currentView.stop();
            }

            currentView = nextView;
        }

        outputPrinter.printInfo("Chiusura dell'applicazione.");
    }

    private CliView initialView() {

        if (sessionController.hasPersistedTokens()) {
            try {
                sessionController.resumeSessionAsync().join();
                return new DashboardCli();

            } catch (CompletionException e) {
                outputPrinter.printInfo(
                        "Impossibile ripristinare la sessione. Effettua nuovamente il login."
                );

                sessionController.logout();
            }
        }

        return new AuthenticationCli();
    }
}
