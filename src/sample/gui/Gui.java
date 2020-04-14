package sample.gui;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;


public class Gui {

    private GridPane guiData;
    private List<RadioButton> verdier = new ArrayList<>();


    public Gui(GridPane guiData) {
        this.guiData = guiData;
    }

    public ChoiceBox<String> velgFarge(){
        String [] farger = {"Hvit (inkludert)", "Svart", "Blå", "Rød" };
        ChoiceBox farge = new ChoiceBox();
        for (int i = 0; i < farger.length; i++) {
            farge.getItems().add(farger[i]);
        }
        farge.getSelectionModel().selectFirst();
        Label label = new Label("Velg farge:");
        guiData.add(label, 0, 0);
        guiData.add(farge, 0, 1);
        return farge;
    }
    public String selectChoiceBox (ChoiceBox<String> choice){
            String selectedChoice = choice.getSelectionModel().getSelectedItem();
            return selectedChoice;
    }

    public List<RadioButton> velgInteriør(){
        Label label = new Label("Velg interiør:");
        RadioButton radio1 = new RadioButton("Helsvart");
        RadioButton radio2 = new RadioButton("Svart og hvit");
        verdier.add(radio1);
        verdier.add(radio2);
        ToggleGroup group1 = new ToggleGroup();
        for(int i = 0; i < verdier.size(); i++){
            verdier.get(i).setToggleGroup(group1);
        }
        guiData.add(label, 0, 0);
        guiData.add(radio1, 0, 1);
        guiData.add(radio2, 1, 1);
        return verdier;
    }
    public String selectRadioButton(List<RadioButton> radioListe) {
        for(int i = 0; i < radioListe.size(); i++){
            if(radioListe.get(i).isSelected()){
                System.out.print(radioListe.get(i).getText());
                return radioListe.get(i).getText();
            }
        }
        return null;
    }
}


