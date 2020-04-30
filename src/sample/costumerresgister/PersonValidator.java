package sample.costumerresgister;

public class PersonValidator {
    static boolean telefonnr(String phone) {
        return !phone.isEmpty() && phone.length() < 20 &&
                phone.matches("[\\d+(]+([\\d()-]+(?: [\\d()-]+)*)");
    }
    static boolean velg(String velg) {
        return !velg.equals("Velg");
    }

    static boolean name(String name) {
        return !name.isEmpty() && name.matches("[^\\d]+");
    }


}
