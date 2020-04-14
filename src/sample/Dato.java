package sample;

import static java.lang.Integer.parseInt;

public class Dato {

    private int dag, måned, år;
    private String månedToString;
    private String dato;

    public Dato(String dato) {
        this.dato = dato;
    }

    public void split(String dato) throws Exception {
        String[] navn = {"januar", "februar", "mars", "april", "mai",
                "juni", "juli", "august", "september", "oktober", "november",
                "desember"};

        if (dato.contains("-")) {
            String[] parts = dato.split("-", 3);
            dag = parseInt(parts[0]); // dag
            måned = parseInt(parts[1]); // måned
            år = parseInt(parts[2]); //år

            if (måned > 0 && måned < 13) {
                månedToString = navn[måned - 1];
            } else {
                throw new Exception("Feil måned");
            }
            if (dag > 30 || dag < 0) {
                throw new Exception("Feil dag");
            }
            if (år < 1900 || år > 2020) {
                throw new Exception("Feil år. Den Må være MELLOM 1900 og 2020");
            }
        }

    }

    //	skriv	en	toString	metode	slik	at	datoen	kommer	ut	på	følgende	format:
    public String toString() {
        //	dd	månedsnavn	år,	f.eks	1	januar	2019
        return dag + "." + månedToString + "." + år;
    }
}
