package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.gui.Controller;

import java.io.IOException;

public class Main extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("sample", stage));
        stage.setScene(scene);
        stage.show();
    }

    private static Parent loadFXML(String fxml, Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        Parent root = (Parent)fxmlLoader.load();
        Controller controller = (Controller) fxmlLoader.getController();
        controller.setStage(stage);
        return root;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
