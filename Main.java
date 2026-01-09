public class Main {

    public static void main(String[] args) {

        GestioneFileCSV gestione = new GestioneFileCSV(
                "dati.csv",
                "temp.csv",
                ","
        );

        gestione.aggiungiCampiFinali();                 // Punto 1
        System.out.println("Numero campi: " + gestione.contaCampi()); // Punto 2

        System.out.println("Lunghezza max record: " +
                gestione.calcolaLunghezzaMassimaRecord()); // Punto 3

        gestione.rendiRecordFissi();                    // Punto 4

        gestione.aggiungiRecord(new String[]{
                "1000", "Mario", "Rossi", "18", "N"
        });                                             // Punto 5

        gestione.visualizzaTreCampi(0, 1, 2);           // Punto 6

        gestione.cercaPerCampo(0, "1000");              // Punto 7

        gestione.modificaRecord(1, 1, "MODIFICATO");    // Punto 8

        gestione.cancellaLogicamente(2);                // Punto 9
    }
}
