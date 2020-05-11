package sample.gui;

import sample.io.FileSaver;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import static javax.swing.JOptionPane.CANCEL_OPTION;
import static sample.controllers.HandleController.KomponenterListe;

public class RedigereKomponent {
    private Gui redigereKomponent;

    public RedigereKomponent(Gui redigereKomponent, FileSaver lagreRedigering) throws Exception {
        this.redigereKomponent = redigereKomponent;
        redigereKomponent();
        LagerGuiElemeter.lagerGuiElementer(redigereKomponent);
        lagreRedigering.saveToFile();
    }

    public void redigereKomponent() throws IOException {
        String m = JOptionPane.showInputDialog("Hvilken komponent vil du redigere?");

        int index = 0;

        for(int i = 0; i < KomponenterListe.size(); i++){
            if(KomponenterListe.get(i).toLowerCase().equals(m.toLowerCase())){
                index=i;
            }
            if (i == KomponenterListe.size()-1 && index != 0){
                ArrayList<TextField> input_liste = new ArrayList<>();
                ArrayList<String> string_liste = new ArrayList<>();
                ArrayList<Object> objects = new ArrayList<>();

                int varinat_index = redigereKomponent.getListGui().get(index-1).length;

                for (int o = 0; o < varinat_index; o++){
                    string_liste.add("Variant nr " + (o+1) + " . Endre navn");
                    TextField komponent = new TextField(redigereKomponent.getListGui().get(index-1)[o]);
                    input_liste.add(komponent);
                }
                for(int p = 0; p < varinat_index; p++){
                    objects.add(string_liste.get(p));
                    objects.add(input_liste.get(p));
                }
                int svar = JOptionPane.showConfirmDialog(null,  objects.toArray(), "Redigerer komponent", JOptionPane.OK_CANCEL_OPTION);

                if (svar == JOptionPane.OK_OPTION){
                    for(int l = 1; l < objects.size(); l=l+2){
                        input_liste.add((TextField) objects.get(l));
                    }
                    for(int p = 0; p < (redigereKomponent.getListGui().get(index-1).length); p++){
                        redigereKomponent.getListGui().get(index-1)[p] = input_liste.get(p).getText();
                    }

                } else if(svar == CANCEL_OPTION){
                throw new IOException("Prosessen har blitt avbrutt. Komponenten ble ikke lagret");
                }
            } else if(i == KomponenterListe.size()-1 && index == 0){
                Dialogs.showErrorDialog("Vi har ikke funnet komponenten");
            }
        }
    }
}
