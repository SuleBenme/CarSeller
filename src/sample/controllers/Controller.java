package sample.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.Main;
import sample.bilregister.Bil;
import sample.gui.*;
import sample.io.FileSaver;
import sample.io.FileWriter;

import java.io.*;
import java.net.URL;

import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private AnchorPane anchorPane;

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
    private TableColumn<Bil, Bil> slettBil;

    @FXML
    private GridPane GridPane;

    @FXML
    private GridPane hovedGridPane;

    @FXML
    private ChoiceBox biltypeChoicebox;

    @FXML
    private ChoiceBox komponentChoicebox;

    @FXML
    private TextField bilmodellText;

    @FXML
    private TextField antall;

    @FXML
    private Button create;

    @FXML
    private Button slett;

    @FXML
    private Button redigere;

    @FXML
    private Button addKomponent;

    @FXML
    private GridPane tilKasse;

    private boolean erSuperBruker;

    //Liste av komponenter
    public static ObservableList<String> KomponenterListe = FXCollections.observableArrayList();
    //Tableview
    public static  ObservableList<Bil> BilListe = FXCollections.observableArrayList();
    //Biltype
    private ObservableList<String> BilType = FXCollections.observableArrayList("Velg", "Elektrisk", "Bensin", "Hybrid");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        biltypeColumn.setCellValueFactory(new PropertyValueFactory<>("Biltype"));
        bilmodelColumn.setCellValueFactory(new PropertyValueFactory<>("Bilmodell"));
        bilkomponentColumn.setCellValueFactory(new PropertyValueFactory<>("Komponent"));
        komponentColumn.setCellValueFactory(new PropertyValueFactory<>("Variant"));
        antallColumn.setCellValueFactory(new PropertyValueFactory<>("Antall"));
        prisColumn.setCellValueFactory(new PropertyValueFactory<>("Pris"));

        slettFraTablaview(slettBil);

        oppdatereListe(BilListe);

        //Legger inn data i tableview
        tabell.setItems(BilListe);

        komponentChoicebox.setItems(KomponenterListe);
        biltypeChoicebox.setItems(BilType);

        // "Velg" står først i choicebox
        biltypeChoicebox.getSelectionModel().selectFirst();
        komponentChoicebox.getSelectionModel().selectFirst();

        Gui nyElement = new Gui(GridPane);
        Komponent komponent = new Komponent(nyElement, hovedGridPane);

        //Leser data fra output.text
        try {
            FileWriter file = new FileWriter(nyElement);
            file.readFromFile(komponentChoicebox);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        //Velge komponent og variant
        komponent.komponentChoiceboxSelect(komponentChoicebox, create);

        //Jeg må fikse denne metoden
        redigere.setOnAction(e -> {
            /*
            FileWriter file = new FileWriter();
            file.redigereKomponent();
             */
        });
        //Sletter komponent ved å skrive inn navn
        slett.setOnAction(e -> {
            FileWriter file = new FileWriter(nyElement);
            file.slettKomponent();
            file.lagerGuiElementer();
        });

        //Lager ny komponent
        addKomponent.setOnAction(e -> {
            try {
                FileSaver saveKomponent = new FileSaver(nyElement);
                new NyKomponent(nyElement, saveKomponent);
                Dialogs.showSuccessDialog("Ny komponent ble lagt og lagret");
            } catch (IndexOutOfBoundsException o) {
                Dialogs.showErrorDialog("Maks 10");
            } catch (Exception err){
                Dialogs.showErrorDialog(err.getMessage());
            }
        });

    }

    public void loadCostumerFXML(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/costumer.fxml"));
        Stage stage = Main.getPrimaryStage();
        Parent root = null;
        try {
            root = loader.load();
            CostumerController costumerController = loader.getController();
            costumerController.initData(BilListe);
            costumerController.setMode(erSuperBruker);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        stage.setTitle("Kasse");
        stage.setScene(new Scene(root, 560, 460));
        stage.show();
    }

    public void loggInnKunde(){
        anchorPane.getChildren().remove(addKomponent);
        anchorPane.getChildren().remove(slett);
        anchorPane.getChildren().remove(redigere);
        erSuperBruker = true;
    }

    public void slettFraTablaview(TableColumn<Bil, Bil> slettBil){
        slettBil.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        slettBil.setCellFactory(param -> new TableCell<Bil, Bil>() {
            private final Button deleteButton = new Button("Slett");

            @Override
            protected void updateItem(Bil bil, boolean empty) {
                super.updateItem(bil, empty);

                if (bil == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(deleteButton);
                deleteButton.setOnAction(event -> BilListe.remove(bil));
            }
        });
    }

    public void oppdatereListe(ObservableList<Bil> BilListe){
        BilListe.addListener((ListChangeListener<Bil>) c -> {
            System.out.println("Ble en endret i " + c);
            if (!BilListe.isEmpty()){
                tilKasse.getChildren().clear();
                Button knapp = new Button("Til kasse");
                knapp.setOnAction(e -> loadCostumerFXML());
                double total = 0 ;
                for (Bil item : tabell.getItems()) {
                    total = total + item.getPris();
                }
                Label label = new Label("Pris: " +total);

                tilKasse.add(knapp, 0, 0);
                tilKasse.add(label, 1, 0);
            } else {
                tilKasse.getChildren().clear();
            }
        });
    }

}
