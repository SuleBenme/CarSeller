package sample.costumerresgister;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import sample.gui.Dialogs;

public class CostumerRegister {

    private GridPane guiData;

    public CostumerRegister(GridPane guiData) {
        this.guiData = guiData;
    }

    public Costumer createPersonfromGUIandResetFields() {
        try {
            Costumer p = createPerson();
            resetFields();
            Dialogs.showSuccessDialog(p.getFornavn() + ", takk for at du handlet hos oss!");
            return p;
        } catch (IllegalArgumentException iae) {
            Dialogs.showErrorDialog(iae.getMessage());
        }
        return null;
    }

    private Costumer createPerson() {
        String fornavn = getString((TextField) guiData.lookup("#fornavn"));
        String etternavn = getString((TextField) guiData.lookup("#etternavn"));
        String adresse = getString((TextField) guiData.lookup("#adresse"));
        String telefonnr = getString((TextField) guiData.lookup("#telefonnr"));
        String levering = getChoiceBox((ChoiceBox<String>) guiData.lookup("#levering"));

        return new Costumer(fornavn, etternavn, adresse, telefonnr, levering);
    }

    private String getString(TextField field) {
        return field.getText();
    }

    private String getChoiceBox(ChoiceBox<String> choiceBox) {
        String selectedChoice = choiceBox.getSelectionModel().getSelectedItem();
        return selectedChoice;
    }

    private void resetFields() {
        ((TextField) guiData.lookup("#fornavn")).setText("");
        ((TextField) guiData.lookup("#etternavn")).setText("");
        ((TextField) guiData.lookup("#adresse")).setText("");
        ((TextField) guiData.lookup("#telefonnr")).setText("");
        ((ChoiceBox) guiData.lookup("#levering")).getSelectionModel().selectFirst();
    }

}
