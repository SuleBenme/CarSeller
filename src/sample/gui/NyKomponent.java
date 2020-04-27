package sample.gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class NyKomponent {

    public int lagerNyKomponent(ArrayList<ArrayList<String>> bilKomponent, ArrayList<String[]> listGui, ArrayList<ArrayList<Double>> prisListe, Gui element) throws Exception {
        TextField komponent = new TextField();
        TextField antall_string = new TextField();
        int antall;

        Object[] fields = {
                "Hva skal bil-komponenten hete", komponent,
                "Antall varianter", antall_string
        };

        JOptionPane.showConfirmDialog(null,  fields, "Ny komponent", JOptionPane.OK_CANCEL_OPTION);

        try {
            antall = Integer.parseInt(antall_string.getText());
            if (antall < 1) {
                throw new Exception("Antall komponenter Må være større enn 0");
            } else if (antall > 10) {
                throw new Exception("Antall komponenter Må være lik eller mindre enn 10");
            }
        } catch (Exception err) {
            throw new Exception("Skriv inn et gyldig tall");
        }


        String[] liste = new String[antall];
        ArrayList<Double> listePris = new ArrayList<>();

            for (int i = 0; i < antall; i++) {
                TextField variant = new TextField();
                TextField pris = new TextField();
                Object[] komponenOgPris = {
                        "Komponent " + (i + 1) + " skal hete ...", variant,
                        "Pris ...", pris
                };
                JOptionPane.showConfirmDialog(null, komponenOgPris, "Velg navn og pris", JOptionPane.OK_CANCEL_OPTION);
                try{
                    listePris.add(Double.parseDouble(pris.getText()));
                    liste[i] = variant.getText();
                } catch (Exception err){
                    Dialogs.showErrorDialog("Skriv inn en gyldig pris");
                    i--;
                }
            }

            if (antall <= 2) {
                bilKomponent.add(createListe("RadioButton", komponent.getText()));
                element.lagerRadioKnapp(liste);
            } else if (antall > 2) {
                bilKomponent.add(createListe("ChoiceBox", komponent.getText()));
                element.lagerChoiceBox(liste);
            }
            int length = bilKomponent.size() - 1;
            listGui.add(liste);
            prisListe.add(listePris);
            return length;
    }

    public ArrayList<String> createListe(String type, String komponent){
        ArrayList<String> liste = new ArrayList<>();
        liste.add(type);
        liste.add(komponent);
        return liste;
    }

}
