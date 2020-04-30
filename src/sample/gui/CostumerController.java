package sample.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    private Label label;

    private CostumerRegister registerPerson;

    private ObservableList<String> Leveringsmåte = FXCollections.observableArrayList("Velg", "Pakke i postkassen", "Hjemlevering", "Hjem samme dag");

    private ObservableList<Costumer> CostumerListe = FXCollections.observableArrayList();

    private boolean ja;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        registerPerson = new CostumerRegister(gridPaneCostumer);
        knapp.setOnAction(e -> { registerPerson();});
        levering.setItems(Leveringsmåte);
        levering.getSelectionModel().selectFirst();
    }

    public void initData(ObservableList<Bil> liste){
        String out = "";
        for(Bil item : liste){
            out+=item.toString() + "\n";
        }
        label.setText(out);
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
            Controller controller = loader.getController();
            if (ja == true) {
                controller.loggInnKunde();
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
