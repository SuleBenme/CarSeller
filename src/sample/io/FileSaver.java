package sample.io;

import java.io.FileWriter;
import java.io.IOException;

import static sample.gui.Komponent.*;

public class FileSaver {
    public void saveToFile() throws IOException {
        FileWriter writer = new FileWriter("output.txt");
        writer.write(BilKomponent.get(0).get(0) +  "," + BilKomponent.get(0).get(1));
        writer.write(System.lineSeparator());

        for(int i = 1; i < BilKomponent.size(); i++) {
            writer.write(BilKomponent.get(i).get(0) + "," + BilKomponent.get(i).get(1));
            writer.write(";");
            for (int l = 0; l < ListGui.get(i-1).length; l++) {
                writer.write(ListGui.get(i-1)[l]);
                if (ListGui.get(i-1).length != l+1){
                    writer.write(",");
                }
            }
            writer.write(";");
            for (int p = 0; p < Pris.get(i-1).size(); p++) {
                writer.write(Pris.get(i-1).get(p).toString());
                if (Pris.get(i-1).size() != p+1){
                    writer.write(",");
                } else{
                    writer.write(System.lineSeparator());
                }
            }
        }
        writer.close();
    }

}
