package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogginnController implements Initializable {
    @FXML
    private Button superbruker;

    @FXML
    private Button kunde;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        superbruker.setOnAction(e -> loadSuperbrukerFXML());
        kunde.setOnAction(e -> loadKundeFXML());
    }

    public void loadSuperbrukerFXML() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/handle.fxml"));
        Stage stage = Main.getPrimaryStage();
        Parent root = null;
        try {
            root = loader.load();

        } catch (
                IOException ex) {
            ex.printStackTrace();
        }
        stage.setTitle("Kjøp");
        stage.setScene(new Scene(root, 896, 400));
        stage.show();
    }

    public void loadKundeFXML() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/handle.fxml"));
        Stage stage = Main.getPrimaryStage();
        Parent root = null;
        try {
            root = loader.load();
            HandleController handleController = loader.getController();
            handleController.loggInnKunde();

        } catch (
                IOException ex) {
            ex.printStackTrace();
        }
        stage.setTitle("Kjøp");
        stage.setScene(new Scene(root, 896, 400));
        stage.show();
    }

}
