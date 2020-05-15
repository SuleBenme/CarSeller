package sample.gui;

import sample.io.FileSaver;

import javax.swing.*;

import static sample.controllers.HandleController.KomponenterListe;

public class SlettKomponent {
    private Gui slettKomponent;

    public SlettKomponent(Gui slettKomponent, FileSaver saveKomponent) throws Exception {
        this.slettKomponent = slettKomponent;
        slettKomponent();
        LagerGuiElemeter.lagerGuiElementer(slettKomponent);
        saveKomponent.saveToFile();
    }

    public void slettKomponent () {
        String m = JOptionPane.showInputDialog("Hvilken komponente du vil slette?");
        int index = 0;

        if (m != null) {

            for (int i = 0; i < KomponenterListe.size(); i++) {
                if (KomponenterListe.get(i).toLowerCase().equals(m.toLowerCase())) {
                    index = i;
                }

                if (i == KomponenterListe.size() - 1 && index != 0) {
                    int svar = JOptionPane.showConfirmDialog(null, "Er du sikker? Komponenten skal slettes", "Bekreft",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (svar == JOptionPane.YES_OPTION) {
                        KomponenterListe.remove(index);
                        slettKomponent.getBilKomponent().remove(index);
                        slettKomponent.getListGui().remove(index - 1);
                        slettKomponent.getPris().remove(index - 1);
                        Dialogs.showSuccessDialog("Komponenten ble slettet");
                    } else if (svar == JOptionPane.NO_OPTION) {
                        Dialogs.showErrorDialog("Komponenten ble ikke slettet");
                    }
                } else if (i == KomponenterListe.size() - 1 && index == 0) {
                    Dialogs.showErrorDialog("Vi har ikke funnet komponenten");
                }
            }
        }
    }

}
