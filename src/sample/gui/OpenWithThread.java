package sample.gui;

import javafx.concurrent.Task;
import sample.io.FileWriter;

public class OpenWithThread extends Task<FileWriter>{
    private final Gui gui;

    public OpenWithThread(Gui gui) {
        this.gui = gui;
    }

    @Override
    protected FileWriter call() throws Exception {
        try {
            //Venter et sekund
            Thread.sleep(1000);
        }  catch (InterruptedException e) {
            // gjør ikke noe her, men hvis hvis du er i en løkke
            // burde løkken stoppes med break hvis isCancelled() er true...
        }
        return new FileWriter(gui);
    }
}