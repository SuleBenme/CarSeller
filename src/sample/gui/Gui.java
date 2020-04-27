package sample.gui;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;
import static sample.gui.Komponent.Pris;

public class Gui {
    private final int maksAntall = 10;
    private ArrayList<ArrayList<RadioButton>> radioButtonListe= new ArrayList<>();
    private ArrayList<ChoiceBox<String>> choiceBoxListe = new ArrayList<>();
    private int komponent_index = 0;
    public static int variant_index = 0;

   Gui(){
       //Legger inn null verdier i listen
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
        choiceBoxListe.set(komponent_index++, choicebox);
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
        radioButtonListe.set(komponent_index++, nyArray);
    }

    //Skal brukes senere i tableview
    public String selectRadioButton(ArrayList<RadioButton> radioListe) {
        for(int i = 0; i < radioListe.size(); i++){
            if(radioListe.get(i).isSelected()){
                variant_index = i;
                return radioListe.get(i).getText();
            }
        }
        return null;
    }
    //Skal brukes senere i tableview
    public String selectChoiceBox(ChoiceBox<String> choice) {
        String selectedChoice = choice.getSelectionModel().getSelectedItem();
        variant_index = choice.getSelectionModel().getSelectedIndex();
        return selectedChoice;
    }

    public ArrayList<RadioButton> henteFraRadioButtonListe(GridPane gridPane, String navn, int optionSelected){
        System.out.println(radioButtonListe.size());
        for(int i = 0; i < radioButtonListe.size(); i++){
            if (i == optionSelected){
                for(int j = 0; j < radioButtonListe.get(i).size(); j++){
                    Label label = new Label(navn);
                    Label pris = new Label("Pris: "+Pris.get(i).get(j).toString());
                    gridPane.add(label, 0, 0);
                    gridPane.add(radioButtonListe.get(i).get(j), j, 1);
                    gridPane.add(pris, j, 2);
                }
                return radioButtonListe.get(i);
            }
        }
        return null;
    }
    public ChoiceBox<String> henteFraChoiceBoxListe(GridPane gridPane, String navn, int optionSelected){
        System.out.println(choiceBoxListe.size());
        for(int i = 0; i < choiceBoxListe.size(); i++){
            if (i == optionSelected) {
                Label label = new Label(navn);
                Label pris = new Label("");
                gridPane.add(label, 0, 0);
                gridPane.add(choiceBoxListe.get(i), 0, 1);
                gridPane.add((pris), 0, 2);
                choiceBoxListe.get(i).getSelectionModel().selectedIndexProperty().addListener(
                        (ov, oldVal, newVal) -> {
                            int index = newVal.intValue();
                            for(int j = 0; j < Pris.get(optionSelected).size(); j++){
                                if (index == j){
                                    pris.setText("Pris: " + Pris.get(optionSelected).get(j).toString());
                                }
                            }

                        });
                choiceBoxListe.get(i).getSelectionModel().selectFirst();
                return choiceBoxListe.get(i);
            }
        }
        return null;
    }
}


