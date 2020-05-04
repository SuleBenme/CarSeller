package sample.bilregister;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Bil {
    private SimpleStringProperty biltype;
    private SimpleStringProperty bilmodell;
    private SimpleIntegerProperty antall;
    private SimpleDoubleProperty pris;
    private SimpleStringProperty komponent;
    private SimpleStringProperty variant;

    public Bil(String biltype, String bilmodell, int antall, double pris, String komponent, String variant)  {
        if(!BilValidator.velg(biltype)) {
            throw new IllegalArgumentException("Du må velge biltype!");
        }
        if(!BilValidator.bilmodell(bilmodell)) {
            throw new IllegalArgumentException("Bilmodellen kan ikke være tom eller inneholde tall");
        }
        if(!BilValidator.tom(variant)) {
            throw new IllegalArgumentException("Du må velge variant!");
        }
        if(!BilValidator.antall(antall)) {
            throw new IllegalArgumentException("Du kan bare bestille mellom 1 og 10 antall for hver komponent");
        }

        this.biltype = new SimpleStringProperty(biltype);
        this.bilmodell =  new SimpleStringProperty(bilmodell);
        this.antall = new SimpleIntegerProperty(antall);
        this.pris = new SimpleDoubleProperty(pris);
        this.komponent =  new SimpleStringProperty(komponent);
        this.variant =  new SimpleStringProperty(variant);
    }

    public String getBiltype() { return biltype.get(); }

    public void setBiltype(String biltype) { this.biltype = new SimpleStringProperty(biltype); }

    public String getBilmodell() { return bilmodell.get(); }

    public void setBilmodell(String bilmodell) { this.bilmodell = new SimpleStringProperty(bilmodell); }

    public int getAntall() { return antall.get(); }

    public void setAntall(int antall) { this.antall = new SimpleIntegerProperty(antall); }

    public double getPris() { return pris.get(); }

    public void setPris(double pris) { this.pris = new SimpleDoubleProperty(pris); }

    public String getKomponent() { return komponent.get(); }

    public void setKomponent(String komponent) { this.komponent = new SimpleStringProperty(komponent); }

    public String getVariant() { return variant.get(); }

    public void setVariant(String variant) { this.variant = new SimpleStringProperty(variant); }


    @Override
    public String toString() {
        return String.format("%s %s %s %s %s", getBiltype(), getBilmodell(), getAntall(), getPris(), getKomponent(), getVariant());
    }
}


