package Controller.session;

import context.UserSessionContext;

import java.util.Scanner;

//DA CONTROLLARE E TERMINARE
public class SessionController {

    private final SessionManager sessionManager;

    private final InputReader inputReader;
    private final OutputPrinter outputPrinter;

    public CliEngine(SessionManager sessionManager) {
        this.sessionManager = sessionManager;

        Scanner scanner = new Scanner(System.in);
        this.outputPrinter = new OutputPrinter();
        this.inputReader = new InputReader(scanner, outputPrinter);
    }

    public UserSessionContext getSessionContext() {
        return sessionManager.getSession();
    }

    public InputReader getInput() {
        return inputReader;
    }

    public OutputPrinter getPrinter() {
        return outputPrinter;
    }

    public void start() {

        CliView currentCliView = initialView();

        while (currentCliView != null) {

            CliView nextView = currentCliView.execute(this);

            if (nextView != null &&
                    !(nextView instanceof AuthenticationCli) &&
                    !sessionManager.isAuthenticated()) {

                currentCliView.stop();
                currentCliView = new AuthenticationCli();
                continue;
            }

            if (currentCliView != nextView) {
                currentCliView.stop();
            }

            currentCliView = nextView;
        }

        outputPrinter.printInfo("Chiusura dell'applicazione.");
    }

    private CliView initialView() {

        if (sessionManager.hasPersistedTokens()) {
            try {
                sessionManager.resumeSessionAsync().join();
                return new DashboardCli();

            } catch (CompletionException e) {
                outputPrinter.printInfo(
                        "Sessione scaduta, effettua nuovamente il login."
                );
                sessionManager.logout();
            }
        }

        return new AuthenticationCli();
    }
}
