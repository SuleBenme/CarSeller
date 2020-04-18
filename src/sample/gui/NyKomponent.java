package sample.gui;

import javax.swing.*;
import java.util.ArrayList;

public class NyKomponent {
    public int lagerNyKomponent(ArrayList<ArrayList<String>> bilKomponent, ArrayList<String[]> listGui, Gui prueba) throws Exception {
        String komponent = JOptionPane.showInputDialog("Hva skal bil-komponenten hete?");
        int antall = Integer.parseInt(JOptionPane.showInputDialog("Antall komponenter"));
        if (antall < 1){
            throw new Exception("Antall komponenter Må være større enn 0");
        } else if(antall > 10){
            throw new Exception("Antall komponenter Må være lik eller mindre enn 10");
        }
        String [] liste = new String[antall];
        for(int i = 0; i < antall; i++){
            String komponentNavn = JOptionPane.showInputDialog("Komponent " + (i+1) + " skal hete ..." );
            liste[i] = komponentNavn;
        }

        if (antall <= 2){
            bilKomponent.add(createListe("RadioButton", komponent));
            prueba.lagerRadioKnapp(liste);
        } else if(antall > 2){
            bilKomponent.add(createListe("ChoiceBox", komponent));
            prueba.lagerChoiceBox(liste);
        }
        int length = bilKomponent.size() -1;
        listGui.add(liste);
        return length;
    }

    public ArrayList<String> createListe(String type, String komponent){
        ArrayList<String> liste = new ArrayList<>();
        liste.add(type);
        liste.add(komponent);
        return liste;
    }
}
