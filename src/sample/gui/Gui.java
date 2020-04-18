package sample.gui;

import javafx.scene.control.*;
import java.util.ArrayList;

public class Gui {
    private final int maksAntall = 10;
    public static ArrayList<ArrayList<RadioButton>> radioButtonListe= new ArrayList<>();
    public static ArrayList<ChoiceBox<String>> choiceBoxListe = new ArrayList<>();
    private int index = 0;

    public void leggeInnNullVerdier(){
        for(int i = 0; i < maksAntall; i++){
            radioButtonListe.add(null);
            choiceBoxListe.add(null);
        }
    }
    public void lagerChoiceBox(String[] liste) {
        ChoiceBox<String> choicebox = new ChoiceBox();
        for (int i = 0; i < liste.length; i++) {
            choicebox.getItems().add(liste[i]);
        }
        choicebox.getSelectionModel().selectFirst();
        choiceBoxListe.set(index++, choicebox);
    }

    public void lagerRadioKnapp(String[] liste){
        ArrayList<RadioButton> nyArray = new ArrayList<>();
        for(int i = 0; i < liste.length; i++){
            RadioButton radio = new RadioButton(liste[i]);
            nyArray.add(radio);
        }
        ToggleGroup group1 = new ToggleGroup();
        for(int i = 0; i < nyArray.size(); i++){
            nyArray.get(i).setToggleGroup(group1);
        }
        radioButtonListe.set(index++, nyArray);
    }

    //Skal brukes senere i tableview
    public String selectRadioButton(ArrayList<RadioButton> radioListe) {
        for(int i = 0; i < radioListe.size(); i++){
            if(radioListe.get(i).isSelected()){
                return radioListe.get(i).getText();
            }
        }
        return null;
    }
    //Skal brukes senere i tableview
    public String selectChoiceBox(ChoiceBox<String> choice) {
        String selectedChoice = choice.getSelectionModel().getSelectedItem();
        return selectedChoice;
    }
}


