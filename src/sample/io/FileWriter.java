package sample.io;

import javafx.scene.control.ChoiceBox;
import sample.gui.Dialogs;
import sample.gui.Gui;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import static sample.gui.Controller.liste;
import static sample.gui.Komponent.*;



public class FileWriter {
    public void readFromFile(ChoiceBox<String> komponentChoicebox, Gui nyElement) throws IOException {
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
                        ArrayList<String> myList = new ArrayList<>();
                        String[] komponenter = index1.split(",");
                        String kom1 = komponenter[0];
                        String kom2 = komponenter[1];
                        myList.add(kom1);
                        myList.add(kom2);
                        BilKomponent.add(myList);
                        liste.add(kom2);
                    }
                    if (index2.contains(",")){
                        String[] varianter = index2.split(",");
                        ListGui.add(varianter);
                    }
                    if(index3.contains(",")){
                        String [] priser_string = index3.split(",");
                        ArrayList<Double> priser = new ArrayList<>();
                        for(int i = 0; i < priser_string.length; i++){
                            priser.add(Double.parseDouble(priser_string[i]));
                        }
                        Pris.add(priser);
                    }
                } else{
                    ArrayList<String> liste_velg = new ArrayList<>();
                    String[] prueba = str.split(",");
                    String kom1 = prueba[0];
                    String kom2 = prueba[1];
                    liste_velg.add(kom1);
                    liste_velg.add(kom2);
                    BilKomponent.add(liste_velg);
                    liste.add(kom2);
                }
            }
            komponentChoicebox.getSelectionModel().selectFirst();
            actualiza(nyElement);
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

    public void actualiza(Gui nyElement){
        for (String[] strings : ListGui) {
            if (strings.length <= 2 && strings.length > 0) {
                nyElement.lagerRadioKnapp(strings);
            } else if (strings.length > 2) {
                nyElement.lagerChoiceBox(strings);
            }
        }
    }

    public void slettKomponent (){
        String m = JOptionPane.showInputDialog("Hvilken komponente du vil slette?");
        int index = 0;

        for(int i = 0; i < liste.size(); i++){
            if(liste.get(i).toLowerCase().equals(m.toLowerCase())){
                index=i;
            }

            if(i == liste.size()-1 && index != 0){
                int svar = JOptionPane.showConfirmDialog(null, "Er du sikker? Komponenten skal slettes?", "Bekreft",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (svar == JOptionPane.YES_OPTION) {
                    liste.remove(index);
                    BilKomponent.remove(index);
                    ListGui.remove(index-1);
                    Pris.remove(index-1);
                    Dialogs.showSuccessDialog("Komponenten ble slettet");
                }
            } else if(i == liste.size()-1 && index == 0){
                System.out.println(index);
                Dialogs.showErrorDialog("Vi har ikke funnet komponenten");
            }
        }
    }

//Jeg ikke ferdig med denne metoden
    public void redigereKomponent(){
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
                int numero = JOptionPane.showConfirmDialog(null,  fields, "Redigerer komponent", JOptionPane.OK_CANCEL_OPTION);
                TextField[] algo = new TextField[(ListGui.get(index-1).length)*2];
                System.out.println(ListGui.get(index-1).length);
                if (numero == JOptionPane.OK_OPTION){
                    for(int l = 1; l < fields.length; l=l+2){
                        algo[l] = (TextField) fields[l];
                    }
                    for (int o = 1; o < algo.length; o=o+2){
                        System.out.print(algo[o].getText());
                    }
                    int prueba  = 1;
                    for(int p = 0; p < ListGui.get(index-1).length; p++){
                        if (p == 1){
                            prueba = 2;
                        }
                        ListGui.get(index-1)[p] = algo[p+prueba].getText();
                    }
                    //actualiza();
                }
            } else if(i == liste.size()-1 && index == 0){
                System.out.println(index);
                Dialogs.showErrorDialog("Vi har ikke funnet komponenten");
            }
        }
    }

}

