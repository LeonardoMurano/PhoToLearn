package ui.cli;

import ui.cli.io.InputReader;
import ui.cli.io.OutputPrinter;

public abstract class AbstractCliView implements CliView {

    protected CliEngine engine;
    protected OutputPrinter printer;
    protected InputReader reader;


    //metodo che esegue la view e restituisce quella successiva
    @Override
    public final CliView execute(CliEngine engine) {

        //memorizza il riferimento al CliEngine
        this.engine = engine;

        //recupera gli strumenti di input e output
        this.printer = engine.getPrinter();
        this.reader = engine.getInput();

        //esegue la logica specifica della view
        return render();
    }


    //operazione astratta che definisce il comportamento specifico della view; restituisce la prossima view (o null per uscire)
    protected abstract CliView render();


    //metodo che termina l'esecuzione della view
    @Override
    public void stop() {
        //implementazione default: nessuna risorsa da rilasciare
    }
}
