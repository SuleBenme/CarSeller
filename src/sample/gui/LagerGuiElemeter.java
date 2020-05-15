package sample.gui;

public class LagerGuiElemeter {

    public static void lagerGuiElementer(Gui elementer) throws Exception {
        elementer.reset();

        for (String[] strings : elementer.getListGui()) {
            if (strings.length <= 2 && strings.length > 0) {
                elementer.lagerRadioKnapp(strings);
            } else if (strings.length > 2) {
                elementer.lagerChoiceBox(strings);
            }
        }
    }
}
