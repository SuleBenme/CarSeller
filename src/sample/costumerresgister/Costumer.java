package sample.costumerresgister;

import javafx.beans.property.SimpleStringProperty;

public class Costumer {
    private SimpleStringProperty fornavn;
    private SimpleStringProperty etternavn;
    private SimpleStringProperty adresse;
    private SimpleStringProperty telefonnr;
    private SimpleStringProperty levering;

    public Costumer(String fornavn, String etternavn, String adresse, String telefonnr, String levering)  {
        if(!PersonValidator.velg(levering)) {
            throw new IllegalArgumentException("Du må velge leveringsmåte!");
        }
        if(!PersonValidator.telefonnr(telefonnr)) {
            throw new IllegalArgumentException("Telefonnummer er ugyldig");
        }

        if(!PersonValidator.name(fornavn)) {
            throw new IllegalArgumentException("Fornavn er ugyldig");
        }

        if(!PersonValidator.name(etternavn)) {
            throw new IllegalArgumentException("Etternavn er ugyldig");
        }
        this.fornavn = new SimpleStringProperty(fornavn);
        this.etternavn =  new SimpleStringProperty(etternavn);
        this.adresse =  new SimpleStringProperty(adresse);
        this.telefonnr =  new SimpleStringProperty(telefonnr);
        this.levering = new SimpleStringProperty(levering);
    }

    public String getFornavn() { return fornavn.get(); }

    public void setFornavn(String fornavn) { this.fornavn = new SimpleStringProperty(fornavn); }

    public String getEtternavn() { return etternavn.get(); }

    public void setEtternavn(String etternavn) { this.etternavn = new SimpleStringProperty(etternavn); }

    public String getAdresse() { return adresse.get(); }

    public void setAdresse(String adresse) { this.adresse = new SimpleStringProperty(adresse); }

    public String getTelefonnr() { return telefonnr.get(); }

    public void setTelefonnr(String telefonnr) { this.telefonnr = new SimpleStringProperty(telefonnr); }

    public String getLevering() { return levering.get(); }

    public void setLevering(String levering) { this.levering = new SimpleStringProperty(levering); }


    @Override
    public String toString() {
        return String.format("%s %s %s %s %s", getFornavn(), getEtternavn(), getAdresse(), getTelefonnr(), getLevering());
    }
}


