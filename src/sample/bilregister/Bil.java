package sample.bilregister;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Bil {
    private SimpleStringProperty biltype;
    private SimpleStringProperty merke;
    private SimpleIntegerProperty antall;
    private SimpleDoubleProperty pris;
    private SimpleStringProperty komponent;

    // Tom
    public Bil() {
    }

    public Bil(String biltype, String merke, int antall, double pris, String komponent)  {
        if(!BilValidator.velg(biltype)) {
            throw new IllegalArgumentException("Du må velge biltype!");
        }
        if(!BilValidator.merke(merke)) {
            throw new IllegalArgumentException("Bilmodellen kan ikke være tom eller inneholde tall");
        }

        this.biltype = new SimpleStringProperty(biltype);
        this.merke =  new SimpleStringProperty(merke);
        this.antall = new SimpleIntegerProperty(antall);
        this.pris = new SimpleDoubleProperty(pris);
        this.komponent =  new SimpleStringProperty(komponent);
    }
    /*
    public String getKlass(){
        String className = this.getClass().getSimpleName();
        return className;
    }

     */

    public String getBiltype() { return biltype.get(); }

    public void setBiltype(String biltype) { this.biltype = new SimpleStringProperty(biltype); }

    public String getMerke() { return merke.get(); }

    public void setMerke(String merke) { this.merke = new SimpleStringProperty(merke); }

    public int getAntall() { return antall.get(); }

    public void setAntall(int antall) { this.antall = new SimpleIntegerProperty(antall); }

    public double getPris() { return pris.get(); }

    public void setPris(double pris) { this.pris = new SimpleDoubleProperty(pris); }

    public String getKomponent() { return komponent.get(); }

    public void setKomponent(String komponent) { this.komponent = new SimpleStringProperty(komponent); }


    @Override
    public String toString() {
        return String.format("%s %s %s %s %s", getBiltype(), getMerke(), getAntall(), getPris(), getKomponent());
    }
}


