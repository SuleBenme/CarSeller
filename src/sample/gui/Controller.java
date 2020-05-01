package sample.gui;

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
import sample.io.FileSaver;
import sample.io.FileWriter;

import java.io.*;
import java.net.URL;

import java.util.ResourceBundle;

import static sample.gui.Komponent.*;


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

    public static ObservableList<String> liste = FXCollections.observableArrayList();
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

        //Legger inn data i tableview
        tabell.setItems(BilListe);

        slettBil.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        slettBil.setCellFactory(param -> new TableCell<Bil, Bil>() {
            private final Button deleteButton = new Button("Slett");

            @Override
            protected void updateItem(Bil person, boolean empty) {
                super.updateItem(person, empty);

                if (person == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(deleteButton);
                deleteButton.setOnAction(event -> BilListe.remove(person));
            }
        });

        BilListe.addListener((ListChangeListener<Bil>) c -> {
            System.out.println("Changed on " + c);
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

        NyKomponent nyKomponent = new NyKomponent();
        Gui nyElement = new Gui(GridPane);
        Komponent komponent = new Komponent(nyElement, hovedGridPane);

        try {
            FileWriter file = new FileWriter();
            file.readFromFile(komponentChoicebox, nyElement);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        komponent.komponentChoiceboxSelect(komponentChoicebox, GridPane, create, nyElement);

        //Jeg må fikse denne metoden
        redigere.setOnAction(e -> {
            /*
            FileWriter file = new FileWriter();
            file.redigereKomponent();
             */
        });

        slett.setOnAction(e -> {
            FileWriter file = new FileWriter();
            file.slettKomponent();
            file.lagerGuiElementer(nyElement);
        });

        //Lager ny komponent
        addKomponent.setOnAction(e -> {
            try {
                int length = nyKomponent.lagerNyKomponent(BilKomponent, ListGui, Pris, nyElement);
                liste.add(BilKomponent.get(length).get(1));
                FileSaver file = new FileSaver();
                file.saveToFile();
                Dialogs.showSuccessDialog("Ny komponent ble lagt og lagret");
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
        komponentChoicebox.getSelectionModel().selectFirst();

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
        stage.setScene(new Scene(root, 600, 384));
        stage.show();
    }

    public void loggInnKunde(){
        anchorPane.getChildren().remove(addKomponent);
        anchorPane.getChildren().remove(slett);
        anchorPane.getChildren().remove(redigere);
        erSuperBruker = true;
    }


}
