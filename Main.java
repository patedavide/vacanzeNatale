public class Main {

    public static void main(String[] args) {

        GestioneFileCSV gestione = new GestioneFileCSV(
                "dati.csv",
                "temp.csv",
                ","
        );

        gestione.aggiungiCampiFinali();                 // commit 1
        System.out.println("Numero campi: " + gestione.contaCampi()); // commit 2

        System.out.println("Lunghezza max record: " +
                gestione.calcolaLunghezzaMassimaRecord()); // commit 3

        gestione.rendiRecordFissi();                    // commit 4

        gestione.aggiungiRecord(new String[]{
                "1000", "Mario", "Rossi", "18", "N"
        });                                             // commit 5

        gestione.visualizzaTreCampi(0, 1, 2);           // commit 6

        gestione.cercaPerCampo(0, "1000");              // commit 7

        gestione.modificaRecord(1, 1, "MODIFICATO");    // commit 8

        gestione.cancellaLogicamente(2);                // commit 9
    }
}
