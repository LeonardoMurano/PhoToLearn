package ui.cli;

//interfaccia che definisce il comportamento comune delle view CLI
public interface CliView {

    //operazione che esegue la view e restituisce quella successiva
    CliView execute(CliEngine engine);

    //operazione che termina l'esecuzione della view
    void stop();
}
