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
import sample.bilregister.Farge;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


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
    private ObservableList<Bil> BilListe = FXCollections.observableArrayList();
    private ObservableList<String> BilType = FXCollections.observableArrayList("Velg", "Elektrisk", "Bensin", "Hybrid");
    private ObservableList<String> BilKomponent = FXCollections.observableArrayList("Velg", "Farge", "Interiør");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        biltypeColumn.setCellValueFactory(new PropertyValueFactory<>("Biltype"));
        bilmodelColumn.setCellValueFactory(new PropertyValueFactory<>("Merke"));
        bilkomponentColumn.setCellValueFactory(new PropertyValueFactory<>("Klass"));
        komponentColumn.setCellValueFactory(new PropertyValueFactory<>("Komponent"));
        antallColumn.setCellValueFactory(new PropertyValueFactory<>("Antall"));
        prisColumn.setCellValueFactory(new PropertyValueFactory<>("Pris"));

        tabell.setItems(BilListe);

        // Fyll på choiceboksene
        biltypeChoicebox.setItems(BilType);
        komponentChoicebox.setItems(BilKomponent);
        // "Velg" står først i choicebox
        biltypeChoicebox.getSelectionModel().selectFirst();
        komponentChoicebox.getSelectionModel().selectFirst();

        komponentChoicebox.getSelectionModel().selectedIndexProperty().addListener(
                (ov, oldVal, newVal) -> {
                    int optionSelected = newVal.intValue();
                    if (optionSelected == 0) {
                        System.out.println("Du må velge en bil-komponent");
                    }
                    // Farge komponent
                    if (optionSelected == 1) {
                        GridPane.getChildren().clear();
                        Gui nyElement = new Gui(GridPane);
                        ChoiceBox<String> komponent = nyElement.velgFarge();
                        create.setOnAction(e -> {
                            try{
                                Bil bil = new Farge((String) biltypeChoicebox.getSelectionModel().getSelectedItem(), bilmodellText.getText(), 4, 45.5, nyElement.selectChoiceBox(komponent));
                                BilListe.add(bil);
                            } catch (IllegalArgumentException err) {
                                Dialogs.showErrorDialog(err.getMessage());
                            }
                        });
                    }
                    //Interiør komponent
                    if (optionSelected == 2) {
                        GridPane.getChildren().clear();
                        Gui nyElement = new Gui(GridPane);
                        List<RadioButton> komponent = nyElement.velgInteriør();
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
                });
    }

}
