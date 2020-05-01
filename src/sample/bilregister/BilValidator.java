package sample.bilregister;

public class BilValidator {

    static boolean bilmodell(String bilmodell) {
        return !bilmodell.equals("") && bilmodell.matches("[^\\d]+");
    }

    static boolean velg(String velg) {
        return !velg.equals("Velg");
    }


    /* Kunde validering foregår her også
    // Source: https://howtodoinjava.com/regex/java-regex-validate-email-address/
    static boolean email(String email) {
        return email.matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    }
     */


}
