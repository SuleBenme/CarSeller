package sample.bilregister;

import javafx.beans.property.SimpleStringProperty;
import sample.bilregister.Bil;


public class Interiør extends Bil {
    private SimpleStringProperty interiør;

    // Tom konstruktør, skal kanskje fylles senere
    public Interiør(){
        super();
    }

    public Interiør(String biltype, String merke, int antall, double pris, String interiør) {
        super(biltype, merke, antall,pris);
        this.interiør = new SimpleStringProperty(interiør);
    }

    public String getKomponent() { return interiør.get(); }

    public void setInteriør(String interiør) { this.interiør.set(interiør); }


    @Override
    public String toString() {
        return String.format("%s %s", super.toString(), getKomponent());
    }
}