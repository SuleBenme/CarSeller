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
import sample.io.FileSaver;
import sample.io.FileWriter;

import java.io.*;
import java.net.URL;

import java.util.ResourceBundle;

import static sample.gui.Komponent.*;


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

    @FXML
    private Button save;

    @FXML
    private Button read;

    @FXML
    private Button slett;

    @FXML
    private Button redigere;

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

    public static ObservableList<String> liste = FXCollections.observableArrayList();
    //Tableview
    public static  ObservableList<Bil> BilListe = FXCollections.observableArrayList();
    //Biltype
    private ObservableList<String> BilType = FXCollections.observableArrayList("Velg", "Elektrisk", "Bensin", "Hybrid");

    @FXML
    private Button addKomponent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        biltypeColumn.setCellValueFactory(new PropertyValueFactory<>("Biltype"));
        bilmodelColumn.setCellValueFactory(new PropertyValueFactory<>("Merke"));
        bilkomponentColumn.setCellValueFactory(new PropertyValueFactory<>("Klass"));
        komponentColumn.setCellValueFactory(new PropertyValueFactory<>("Komponent"));
        antallColumn.setCellValueFactory(new PropertyValueFactory<>("Antall"));
        prisColumn.setCellValueFactory(new PropertyValueFactory<>("Pris"));
        //Legger inn data i tableview
        tabell.setItems(BilListe);

        NyKomponent nyKomponent = new NyKomponent();
        Gui nyElement = new Gui();
        Komponent komponent = new Komponent();

        komponent.komponentChoiceboxSelect(komponentChoicebox, GridPane, create, nyElement);

        save.setOnAction(e -> {
            try {
                FileSaver file = new FileSaver();
                file.saveToFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        redigere.setOnAction(e -> {
            FileWriter file = new FileWriter();
            file.redigereKomponent();
        });

        slett.setOnAction(e -> {
            FileWriter file = new FileWriter();
            file.slettKomponent();
            file.actualiza(nyElement);
        });

        read.setOnAction(e -> {
            try {
                FileWriter file = new FileWriter();
                file.readFromFile(komponentChoicebox, nyElement);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        //Lager ny komponent
        addKomponent.setOnAction(e -> {
            try {
                int length = nyKomponent.lagerNyKomponent(BilKomponent, ListGui, Pris, nyElement);
                liste.add(BilKomponent.get(length).get(1));
                Dialogs.showSuccessDialog("Ny komponent ble lagt");
            } catch (IndexOutOfBoundsException o) {
                Dialogs.showErrorDialog("Maks 10");
            } catch (Exception err){
                Dialogs.showErrorDialog(err.getMessage());
            }
        });


        komponentChoicebox.setItems(liste);
        biltypeChoicebox.setItems(BilType);
        // "Velg" står først i choicebox
        biltypeChoicebox.getSelectionModel().selectFirst();

    }

}
