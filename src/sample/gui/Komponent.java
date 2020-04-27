package sample.gui;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
import sample.bilregister.Bil;

import java.util.ArrayList;
import static sample.gui.Controller.*;
import static sample.gui.Gui.variant_index;

public class Komponent {

    //Bilkomponent
    public static ArrayList<ArrayList<String>> BilKomponent = new ArrayList<>();
    //Hver komponent har forskjellige varianter
    public static ArrayList<String[]> ListGui = new ArrayList<>();
    //Hver variant har en pris
    public static ArrayList<ArrayList<Double>> Pris = new ArrayList<>();

    public int komponent_selected;

    public void komponentChoiceboxSelect(ChoiceBox<String> komponentChoicebox, GridPane gridPane, Button create, Gui nyElement){
        komponentChoicebox.getSelectionModel().selectedIndexProperty().addListener(
                (ov, oldVal, newVal) -> {
                    int optionSelected = newVal.intValue();
                    if (optionSelected == 0) {
                        System.out.println("Du må velge en bil-komponent");
                    }

                    for(int i = 1; i < BilKomponent.size(); i++){
                        if (optionSelected == i){
                            optionSelected--;
                            komponent_selected = optionSelected;
                            for (int j = 0; j < ListGui.size(); j++){
                                if (j == optionSelected) {
                                    gridPane.getChildren().clear();
                                    if (BilKomponent.get(i).get(0).equals("RadioButton")){
                                        ArrayList<RadioButton> radioButton = nyElement.henteFraRadioButtonListe(gridPane, "RadioKnapp", j);
                                        radioButtonTableview(radioButton, create);
                                    } else if(BilKomponent.get(i).get(0).equals("ChoiceBox")){
                                        ChoiceBox<String> choiceBox = nyElement.henteFraChoiceBoxListe(gridPane, "ChoiceBox", j);
                                         choiceBoxTableview(choiceBox, create);
                                    }
                                }
                            }
                        }
                    }
                });
    }

    public void choiceBoxTableview(ChoiceBox<String> choice, Button create){
        Gui nyElement = new Gui();
        create.setOnAction(e -> {
            try{
                String komponent = nyElement.selectChoiceBox(choice);
                Bil bil = new Bil("Biltype", "Merke", 0, Pris.get(komponent_selected).get(variant_index), komponent);
                BilListe.add(bil);
            }
            catch(IllegalArgumentException err){
                Dialogs.showErrorDialog(err.getMessage());
            }
        });
    }

    public void radioButtonTableview(ArrayList<RadioButton> radio, Button create) {
        Gui nyElement = new Gui();
        create.setOnAction(e -> {
            try {
                String komponent = nyElement.selectRadioButton(radio);
                Bil bil = new Bil("Biltype", "Merke", 0, Pris.get(komponent_selected).get(variant_index), komponent);
                BilListe.add(bil);
            } catch (IllegalArgumentException err) {
                Dialogs.showErrorDialog(err.getMessage());
            }
        });
    }

}
