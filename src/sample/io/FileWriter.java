package sample.io;

import sample.gui.Dialogs;
import sample.gui.Gui;
import sample.gui.LagerGuiElemeter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import static sample.controllers.HandleController.KomponenterListe;

public class FileWriter {
    private Gui fileWriter;

    private final String feilmelding = "Noe gikk galt med henting av data";

    public FileWriter(Gui fileWriter) throws Exception {
        this.fileWriter = fileWriter;
        readFromFile();
        LagerGuiElemeter.lagerGuiElementer(fileWriter);
    }

    public void readFromFile() throws IOException {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader("output.txt"));
            String str;
            while ((str = in.readLine()) != null) {
                if(str.contains(";")) {
                    String[] parts = str.split(";", 3);
                    String index1 = parts[0];
                    String index2 = parts[1];
                    String index3 = parts[2];
                    if (index1.contains(",")) {
                        ArrayList<String> komponent_liste = new ArrayList<>();
                        String[] komponenter = index1.split(",");
                        String kom1 = komponenter[0];
                        String kom2 = komponenter[1];
                        komponent_liste.add(kom1);
                        komponent_liste.add(kom2);
                        fileWriter.getBilKomponent().add(komponent_liste);
                        KomponenterListe.add(kom2);
                    } else{
                        throw new IOException(feilmelding);
                    }
                    if (index2.contains(",")){
                        String[] varianter = index2.split(",");
                        fileWriter.getListGui().add(varianter);
                    }else{
                        throw new IOException(feilmelding);
                    }
                    if(index3.contains(",")){
                        String [] priser_string = index3.split(",");
                        ArrayList<Double> priser = new ArrayList<>();
                        for(int i = 0; i < priser_string.length; i++){
                            priser.add(Double.parseDouble(priser_string[i]));
                        }
                        fileWriter.getPris().add(priser);
                    }else{
                        throw new IOException(feilmelding);
                    }
                } else{
                    ArrayList<String> liste_velg = new ArrayList<>();
                    String[] velg = str.split(",");
                    String kom1 = velg[0];
                    String kom2 = velg[1];
                    liste_velg.add(kom1);
                    liste_velg.add(kom2);
                    fileWriter.getBilKomponent().add(liste_velg);
                    KomponenterListe.add(kom2);
                }
            }

        } catch (FileNotFoundException e) {
            Dialogs.showErrorDialog("Vi har ikke funnet filen");
        } catch (IOException e) {
            Dialogs.showErrorDialog(e.getMessage());
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

}

