package sample.gui;

import javafx.scene.control.*;
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
    //Hver variant har pris
    public static ArrayList<ArrayList<Double>> Pris = new ArrayList<>();

    private int komponent_selected;

    private Gui nyElement;

    private GridPane hovedGridPane;

    public Komponent(Gui nyElement, GridPane hovedGridPane){
        this.nyElement = nyElement;
        this.hovedGridPane = hovedGridPane;
    }

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
                                        ArrayList<RadioButton> radioButton = nyElement.henteFraRadioButtonListe("RadioKnapp", j);
                                        radioButtonTableview(radioButton, create);
                                    } else if(BilKomponent.get(i).get(0).equals("ChoiceBox")){
                                        ChoiceBox<String> choiceBox = nyElement.henteFraChoiceBoxListe( "ChoiceBox", j);
                                         choiceBoxTableview(choiceBox, create);
                                    }
                                }
                            }
                        }
                    }
                });
    }

    public void choiceBoxTableview(ChoiceBox<String> choice, Button create){
        create.setOnAction(e -> {
            try{
                String komponent = nyElement.selectChoiceBox(choice);
                Bil bil = new Bil(getBiltype(), getBilmodell(), getAntall(), Pris.get(komponent_selected).get(variant_index), getBilkomponent(),komponent);
                BilListe.add(bil);
            }
            catch(IllegalArgumentException err){
                Dialogs.showErrorDialog(err.getMessage());
            }
        });
    }

    public void radioButtonTableview(ArrayList<RadioButton> radio, Button create) {
        create.setOnAction(e -> {
            try {
                String komponent = nyElement.selectRadioButton(radio);
                Bil bil = new Bil(getBiltype(), getBilmodell(), getAntall(), Pris.get(komponent_selected).get(variant_index), getBilkomponent() ,komponent);
                BilListe.add(bil);
            } catch (IllegalArgumentException err) {
                Dialogs.showErrorDialog(err.getMessage());
            }
        });
    }

    private String getBilmodell() {
        String name = getString((TextField) hovedGridPane.lookup("#bilmodellText"));
        return name;
    }

    private String getBilkomponent() {
        String bilkomponent = getChoiceBox((ChoiceBox<String>) hovedGridPane.lookup("#komponentChoicebox"));
        return bilkomponent;
    }

    private int getAntall() {
        int antall = getInt((TextField) hovedGridPane.lookup("#antall"));
        return antall;
    }
    private String getBiltype() {
        String biltype = getChoiceBox((ChoiceBox<String>) hovedGridPane.lookup("#biltypeChoicebox"));
        return biltype;
    }

    private String getString(TextField field) {
        return field.getText();
    }

    private int getInt(TextField field) {
        return Integer.parseInt(getString(field));
    }

    private String getChoiceBox(ChoiceBox<String> choiceBox) {
        String selectedChoice = choiceBox.getSelectionModel().getSelectedItem();
        return selectedChoice;
    }


}
