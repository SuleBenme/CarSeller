package sample.gui;

import sample.io.FileSaver;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static javax.swing.JOptionPane.*;
import static sample.gui.Controller.KomponenterListe;

public class NyKomponent {
    private Gui element;
    private FileSaver saveKomponent;

    public NyKomponent(Gui element, FileSaver saveKomponent) throws Exception {
        this.element = element;
        this.saveKomponent = saveKomponent;
        leggeTilKomponent();
    }

    public int lagerNyKomponent() throws Exception {
        TextField komponent = new TextField();
        TextField antall_string = new TextField();
        int antall;

        Object[] fields = {
                "Hva skal bil-komponenten hete", komponent,
                "Antall varianter", antall_string
        };

        JOptionPane.showConfirmDialog(null, fields, "Ny komponent", JOptionPane.OK_CANCEL_OPTION);

        sjekkKomponent(komponent.getText());
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
            int svar = JOptionPane.showConfirmDialog(null, komponenOgPris, "Velg navn og pris", JOptionPane.OK_CANCEL_OPTION);
            if (svar == OK_OPTION) {
                try {
                    listePris.add(Double.parseDouble(pris.getText()));
                    liste[i] = variant.getText();
                } catch (Exception err) {
                    Dialogs.showErrorDialog("Skriv inn en gyldig pris");
                    i--;
                }
            } else if(svar == CANCEL_OPTION){
                throw new Exception("Prosessen har blitt avbrutt. Komponenten ble ikke lagret");
            }
        }

        if (antall <= 2) {
            element.getBilKomponent().add(createListe("RadioButton", komponent.getText()));
            element.lagerRadioKnapp(liste);
        } else if (antall > 2) {
            element.getBilKomponent().add(createListe("ChoiceBox", komponent.getText()));
            element.lagerChoiceBox(liste);
        }

        int length = element.getBilKomponent().size() - 1;
        element.getListGui().add(liste);
        element.getPris().add(listePris);
        return length;
    }




    public void leggeTilKomponent() throws Exception {
        int length = lagerNyKomponent();
        KomponenterListe.add(element.getBilKomponent().get(length).get(1));
        saveKomponent.saveToFile();

    }

    public void sjekkKomponent(String navn) throws Exception {
        for(int i = 0; i < KomponenterListe.size(); i++){
            System.out.println(KomponenterListe.get(i));
            if (KomponenterListe.get(i).equals(navn)){
                throw new Exception("Denne komponenten finnes allerede. Skriv inn en ny komponent");
            }
        }
    }

    public ArrayList<String> createListe(String type, String komponent){
        ArrayList<String> liste = new ArrayList<>();
        liste.add(type);
        liste.add(komponent);
        return liste;
    }

}
