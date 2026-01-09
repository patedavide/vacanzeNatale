public class Main {
    public static void main(String[] args) {

        GestioneFileCSV gestione = new GestioneFileCSV(
                "paterno.csv",
                "paterno_modificato.csv",
                ","
        );

        // 1
        gestione.aggiungiCampiFinali();

        // 2
        System.out.println("Numero campi: " + gestione.contaCampi());

        // 3
        System.out.println("Lunghezza max record: " + gestione.calcolaLunghezzaMassimaRecord());

        // 4
        gestione.rendiRecordFissi();

        // 5
        String[] nuovoRecord = {
                "\"Received Counseling or Therapy, Last 4 Weeks\"",
                "By Sex",
                "United States",
                "Male",
                "2",
                "15",
                "\"Sep 16 - Sep 28, 2020\"",
                "09/16/2020",
                "09/28/2020",
                "6.9",
                "6.5",
                "7.3",
                "6.5 - 7.3",
                "", // miovalore
                ""  // cancellato
        };
        gestione.aggiungiRecord(nuovoRecord);

        // 6
        gestione.visualizzaTreCampi();

        // 7
        gestione.cercaPerIndicator("\"Received Counseling or Therapy, Last 4 Weeks\"");

        // 8
        gestione.modificaRecord(1, 9, "80.5"); // esempio

        // 9
        gestione.cancellaLogicamente(2);
    }
}
