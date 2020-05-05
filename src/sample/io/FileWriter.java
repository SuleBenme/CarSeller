package sample.io;

import javafx.scene.control.ChoiceBox;
import sample.gui.Dialogs;
import sample.gui.Gui;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import static sample.controllers.Controller.KomponenterListe;

public class FileWriter {
    private Gui fileWriter;

    private final String feilmelding = "Noe gikk galt med henting av data";

    public FileWriter(Gui fileWriter){
        this.fileWriter = fileWriter;
    }

    public void readFromFile(ChoiceBox<String> komponentChoicebox) throws IOException {
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
                    }
                    if (index2.contains(",")){
                        String[] varianter = index2.split(",");
                        fileWriter.getListGui().add(varianter);
                    }
                    if(index3.contains(",")){
                        String [] priser_string = index3.split(",");
                        ArrayList<Double> priser = new ArrayList<>();
                        for(int i = 0; i < priser_string.length; i++){
                            priser.add(Double.parseDouble(priser_string[i]));
                        }
                        fileWriter.getPris().add(priser);
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
            komponentChoicebox.getSelectionModel().selectFirst();
            lagerGuiElementer();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    public void lagerGuiElementer(){
        for (String[] strings : fileWriter.getListGui()) {
            if (strings.length <= 2 && strings.length > 0) {
                fileWriter.lagerRadioKnapp(strings);
            } else if (strings.length > 2) {
                fileWriter.lagerChoiceBox(strings);
            }
        }
    }

    public void slettKomponent (){
        String m = JOptionPane.showInputDialog("Hvilken komponente du vil slette?");
        int index = 0;

        for(int i = 0; i < KomponenterListe.size(); i++){
            if(KomponenterListe.get(i).toLowerCase().equals(m.toLowerCase())){
                index=i;
            }

            if(i == KomponenterListe.size()-1 && index != 0){
                int svar = JOptionPane.showConfirmDialog(null, "Er du sikker? Komponenten skal slettes", "Bekreft",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (svar == JOptionPane.YES_OPTION) {
                    KomponenterListe.remove(index);
                    fileWriter.getBilKomponent().remove(index);
                    fileWriter.getListGui().remove(index-1);
                    fileWriter.getPris().remove(index-1);
                    Dialogs.showSuccessDialog("Komponenten ble slettet");
                }
            } else if(i == KomponenterListe.size()-1 && index == 0){
                System.out.println(index);
                Dialogs.showErrorDialog("Vi har ikke funnet komponenten");
            }
        }
    }

//Jeg ikke ferdig med denne metoden
    public void redigereKomponent(){
        /*
        String m = JOptionPane.showInputDialog("Hvilken komponente du vil redigere?");

        int index = 0;

        for(int i = 0; i < liste.size(); i++){
            if(liste.get(i).toLowerCase().equals(m.toLowerCase())){
                index=i;
            }
            if (i == liste.size()-1 && index != 0){
                Object[] fields = new Object[(ListGui.get(index-1).length) * 2];
                for(int p = 1; p < fields.length; p=p+2){
                    TextField komponent = new TextField("Variant");
                    fields[p-1] = "Endre navn";
                    fields[p] = komponent;
                }
                int number = JOptionPane.showConfirmDialog(null,  fields, "Redigerer komponent", JOptionPane.OK_CANCEL_OPTION);
                TextField[] textFieldListe = new TextField[(ListGui.get(index-1).length)*2];
                System.out.println(ListGui.get(index-1).length);
                if (number == JOptionPane.OK_OPTION){
                    for(int l = 1; l < fields.length; l=l+2){
                        textFieldListe[l] = (TextField) fields[l];
                    }
                    for (int o = 1; o < textFieldListe.length; o=o+2){
                        System.out.print(textFieldListe[o].getText());
                    }
                    int prueba  = 1;
                    for(int p = 0; p < ListGui.get(index-1).length; p++){
                        if (p == 1){
                            prueba = 2;
                        }
                        ListGui.get(index-1)[p] = textFieldListe[p+prueba].getText();
                    }
                }
            } else if(i == liste.size()-1 && index == 0){
                System.out.println(index);
                Dialogs.showErrorDialog("Vi har ikke funnet komponenten");
            }
        }

         */
    }

}

