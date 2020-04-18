package sample.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.bilregister.Bil;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static sample.gui.Gui.*;


public class Controller implements Initializable {
    @FXML
    private TableView<Bil> tabell;

    @FXML
    private TableColumn<Bil, String> biltypeColumn;

    @FXML
    private TableColumn<Bil, String> bilmodelColumn;

    @FXML
    private TableColumn<Bil, String> bilkomponentColumn;

    @FXML
    private TableColumn<Bil, String> komponentColumn;

    @FXML
    private TableColumn<Bil, Integer> antallColumn;

    @FXML
    private TableColumn<Bil, Double> prisColumn;

    @FXML
    private GridPane GridPane;

    @FXML
    private ChoiceBox biltypeChoicebox;

    @FXML
    private ChoiceBox komponentChoicebox;

    @FXML
    private TextField bilmodellText;

    @FXML
    private Button create;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    //Lager en costumer fxml fil
    public void loadCostumerFxml(){
        stage.setTitle("Constumer");

        Button button1 = new Button("Button 1");
        Button button2 = new Button("Button 2");
        GridPane gridPane = new GridPane();

        gridPane.add(button1, 0, 0, 1, 1);
        gridPane.add(button2, 1, 0, 1, 1);

        Scene scene = new Scene(gridPane, 240, 100);
        stage.setScene(scene);
        stage.show();
    }
    //Tableview
    private ObservableList<Bil> BilListe = FXCollections.observableArrayList();
    //Biltype
    private ObservableList<String> BilType = FXCollections.observableArrayList("Velg", "Elektrisk", "Bensin", "Hybrid");
    //Bilkomponent
    private ArrayList<ArrayList<String>> BilKomponent = new ArrayList<>();
    //Hver komponent har forskjellige varianter
    private ArrayList<String[]> ListGui = new ArrayList<>();

    private String [] Farge = {"Svart", "Hvit", "Blå", "Rød"};
    private String [] Interiør = {"Svart og hvitt", "Krem"};

    @FXML
    private Button addKomponent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NyKomponent nyKomponent = new NyKomponent();

        BilKomponent.add(nyKomponent.createListe("Velg", "Velg"));
        BilKomponent.add(nyKomponent.createListe("ChoiceBox", "Farge"));
        BilKomponent.add(nyKomponent.createListe("RadioButton", "Interiør"));


        biltypeColumn.setCellValueFactory(new PropertyValueFactory<>("Biltype"));
        bilmodelColumn.setCellValueFactory(new PropertyValueFactory<>("Merke"));
        bilkomponentColumn.setCellValueFactory(new PropertyValueFactory<>("Klass"));
        komponentColumn.setCellValueFactory(new PropertyValueFactory<>("Komponent"));
        antallColumn.setCellValueFactory(new PropertyValueFactory<>("Antall"));
        prisColumn.setCellValueFactory(new PropertyValueFactory<>("Pris"));

        ListGui.add(Farge);
        ListGui.add(Interiør);

        //Legger inn data i tableview
        tabell.setItems(BilListe);

        Gui nyElement = new Gui();
        nyElement.leggeInnNullVerdier();

        for(int i = 0; i < ListGui.size(); i++){
            if (ListGui.get(i).length <= 2 && ListGui.get(i).length > 0){
                nyElement.lagerRadioKnapp(ListGui.get(i));
            } else if(ListGui.get(i).length > 2){
                nyElement.lagerChoiceBox(ListGui.get(i));
            }
        }

        ObservableList<String> liste = FXCollections.observableArrayList();
        // Fyll på choiceboksene
        for(int i = 0; i < BilKomponent.size(); i++) {
            liste.add(BilKomponent.get(i).get(1));
        }

        //Lager ny komponent
        addKomponent.setOnAction(e -> {
            try {
                int length = nyKomponent.lagerNyKomponent(BilKomponent, ListGui, nyElement);
                liste.add(BilKomponent.get(length).get(1));
                Dialogs.showSuccessDialog("Ny komponent ble lagt");
            } catch (Exception err){
                Dialogs.showErrorDialog(err.getMessage());
            }
        });

        komponentChoicebox.setItems(liste);
        biltypeChoicebox.setItems(BilType);
        // "Velg" står først i choicebox
        biltypeChoicebox.getSelectionModel().selectFirst();
        komponentChoicebox.getSelectionModel().selectFirst();

        komponentChoicebox.getSelectionModel().selectedIndexProperty().addListener(
                (ov, oldVal, newVal) -> {
                    int optionSelected = newVal.intValue();

                    if (optionSelected == 0) {
                        System.out.println("Du må velge en bil-komponent");
                    }

                    for(int i = 1; i < BilKomponent.size(); i++){
                        if (optionSelected == i){
                            optionSelected--;
                            for (int j = 0; j < ListGui.size(); j++){
                                if (j == optionSelected) {
                                    GridPane.getChildren().clear();
                                    if (BilKomponent.get(i).get(0) == "RadioButton"){
                                        henteFraRadioButtonListe(radioButtonListe, GridPane, "RadioKnapp", j);
                                    } else if(BilKomponent.get(i).get(0) == "ChoiceBox"){
                                        henteFraChoiceBoxListe(choiceBoxListe, GridPane, "ChoiceBox", j);
                                    }
                                }
                            }
                        }
                    }

                    /*

                        create.setOnAction(e -> {
                            try{
                                Bil bil = new Farge((String) biltypeChoicebox.getSelectionModel().getSelectedItem(), bilmodellText.getText(), 4, 45.5, nyElement.selectChoiceBox(komponent));
                                BilListe.add(bil);
                            } catch (IllegalArgumentException err) {
                                Dialogs.showErrorDialog(err.getMessage());
                            }
                        });

                    }
                    */
                     /*
                    //Interiør komponent
                    if (optionSelected == 2) {
                        GridPane.getChildren().clear();
                        dividir(vacio, GridPane, "Prueba2", 1);
                        /*
                        ArrayList<RadioButton> komponent = nyElement.velgInteriør();
                        create.setOnAction(e -> {
                            try{
                                Bil bil = new Farge((String) biltypeChoicebox.getSelectionModel().getSelectedItem(), bilmodellText.getText(), 4, 45.5, nyElement.selectRadioButton(komponent));
                                BilListe.add(bil);
                            }
                            catch(IllegalArgumentException err){
                                Dialogs.showErrorDialog(err.getMessage());
                            }
                        });
                    }
                    */
                });

    }

    public void henteFraRadioButtonListe(ArrayList<ArrayList<RadioButton>> lista, GridPane gridPane, String navn, int optionSelected){
        for(int i = 0; i < lista.size(); i++){
            if (i == optionSelected){
                for(int j = 0; j < lista.get(i).size(); j++){
                        Label label = new Label(navn);
                        gridPane.add(label, 0, 0);
                        gridPane.add(lista.get(i).get(j), j, 1);
                }
            }
        }
    }

    public void henteFraChoiceBoxListe(ArrayList<ChoiceBox<String>> liste, GridPane gridPane, String navn, int optionSelected){
        for(int i = 0; i < liste.size(); i++){
            if (i == optionSelected) {
                Label label = new Label(navn);
                gridPane.add(label, 0, 0);
                gridPane.add(liste.get(i), 0, 1);
            }
        }
    }

}
