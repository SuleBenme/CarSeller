package sample.bilregister;

import javafx.beans.property.SimpleStringProperty;
import sample.bilregister.Bil;

public class Farge extends Bil {
    private SimpleStringProperty farge;

    // Tom konstruktør, skal kanskje fylles senere
    public Farge(){
        super();
    }

    public Farge(String biltype, String merke, int antall, double pris, String farge) {
        super(biltype, merke, antall, pris);
        this.farge = new SimpleStringProperty(farge);
    }

    public String getKomponent() { return farge.get(); }

    public void setFarge(String farge) { this.farge.set(farge); }

    @Override
    public String toString() {
        return String.format("%s %s", super.toString(), getKomponent());
    }
}