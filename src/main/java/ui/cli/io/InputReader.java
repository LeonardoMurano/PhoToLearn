package ui.cli.io;

import java.util.Scanner;

//componente responsabile della ricezione dell'input dell'utente
public class InputReader {

    //riferimento all'oggetto attraverso cui vengono acquisiti i dati inseriti dall'utente
    private final Scanner scanner;
    //riferimento all'oggetto attraverso il quale vengono comunicati eventuali errori o messaggi all'utente
    private final OutputPrinter printer;

    //costruttore InputReadeer
    public InputReader(Scanner scanner, OutputPrinter printer) {
        this.scanner = scanner;
        this.printer = printer;
    }
}
