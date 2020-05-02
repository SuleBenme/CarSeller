package sample.io;

import sample.gui.Gui;
import java.io.FileWriter;
import java.io.IOException;

public class FileSaver {
    private Gui saveKomponent;

    public FileSaver(Gui saveKomponent){
        this.saveKomponent = saveKomponent;
    }
    public void saveToFile() throws IOException {
        FileWriter writer = new FileWriter("output.txt");
        writer.write(saveKomponent.getBilKomponent().get(0).get(0) +  "," + saveKomponent.getBilKomponent().get(0).get(1));
        writer.write(System.lineSeparator());

        for(int i = 1; i < saveKomponent.getBilKomponent().size(); i++) {
            writer.write(saveKomponent.getBilKomponent().get(i).get(0) + "," + saveKomponent.getBilKomponent().get(i).get(1));
            writer.write(";");
            for (int l = 0; l < saveKomponent.getListGui().get(i-1).length; l++) {
                writer.write(saveKomponent.getListGui().get(i-1)[l]);
                if (saveKomponent.getListGui().get(i-1).length != l+1){
                    writer.write(",");
                }
            }
            writer.write(";");
            for (int p = 0; p < saveKomponent.getPris().get(i-1).size(); p++) {
                writer.write(saveKomponent.getPris().get(i-1).get(p).toString());
                if (saveKomponent.getPris().get(i-1).size() != p+1){
                    writer.write(",");
                } else{
                    writer.write(System.lineSeparator());
                }
            }
        }
        writer.close();
    }

}
