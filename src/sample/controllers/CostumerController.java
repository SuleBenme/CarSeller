package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.Main;
import sample.bilregister.Bil;
import sample.costumerresgister.Costumer;
import sample.costumerresgister.CostumerRegister;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CostumerController implements Initializable {

    @FXML
    private GridPane gridPaneCostumer;

    @FXML
    private TextField fornavn;

    @FXML
    private TextField etternavn;

    @FXML
    private TextField adresse;

    @FXML
    private TextField telefonnr;

    @FXML
    private Button knapp;

    @FXML
    private ChoiceBox<String> levering;

    @FXML
    private TableView<Bil> tabell;

    @FXML
    private TableColumn<Bil, String> biltypeColumn;

    @FXML
    private TableColumn<Bil, String> bilmodellColumn;

    @FXML
    private TableColumn<Bil, String> komponentColumn;

    @FXML
    private TableColumn<Bil, String> variantColumn;

    @FXML
    private TableColumn<Bil, Integer> antallColumn;

    @FXML
    private TableColumn<Bil, Double> prisColumn;

    private CostumerRegister registerPerson;

    private ObservableList<String> Leveringsmåte = FXCollections.observableArrayList("Velg", "Pakke i postkassen", "Hjemlevering", "Hjem samme dag");

    private ObservableList<Costumer> CostumerListe = FXCollections.observableArrayList();

    private boolean ja;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        biltypeColumn.setCellValueFactory(new PropertyValueFactory<>("Biltype"));
        bilmodellColumn.setCellValueFactory(new PropertyValueFactory<>("Bilmodell"));
        komponentColumn.setCellValueFactory(new PropertyValueFactory<>("Komponent"));
        variantColumn.setCellValueFactory(new PropertyValueFactory<>("Variant"));
        antallColumn.setCellValueFactory(new PropertyValueFactory<>("Antall"));
        prisColumn.setCellValueFactory(new PropertyValueFactory<>("Pris"));

        registerPerson = new CostumerRegister(gridPaneCostumer);
        knapp.setOnAction(e -> { registerPerson();});
        levering.setItems(Leveringsmåte);
        levering.getSelectionModel().selectFirst();
    }

    public void initData(ObservableList<Bil> liste){
        tabell.setItems(liste);
    }

    private void registerPerson() {
        Costumer newPerson = registerPerson.createPersonfromGUIandResetFields();
        if(newPerson != null) {
            CostumerListe.add(newPerson);
            System.out.print(CostumerListe);
            loadFXML();
        }
    }
    public void setMode(boolean superbruker){
        ja = superbruker;
    }

    public void loadFXML() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/sample.fxml"));
        Stage stage = Main.getPrimaryStage();
        Parent root = null;
        try {
            root = loader.load();
            HandleController handleController = loader.getController();
            if (ja == true) {
                handleController.loggInnKunde();
            }

        } catch (
                IOException ex) {
            ex.printStackTrace();
        }
        stage.setTitle("Kjøp");
        stage.setScene(new Scene(root, 896, 400));
        stage.show();
    }
}
