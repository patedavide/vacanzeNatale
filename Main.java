public class Main {

    public static void main(String[] args) {

        GestioneFileCSV gestione = new GestioneFileCSV(
                "paterno.csv",   // CSV reale
                "temp_cancellazione.csv",
                ","
        );

        gestione.aggiungiCampiFinali();              //Punto 1
        System.out.println("Numero campi: " + gestione.contaCampi()); //Punto 2

        System.out.println("Lunghezza max record: " +
                gestione.calcolaLunghezzaMassimaRecord()); //Punto 3

        gestione.rendiRecordFissi();                 //Punto 4

        gestione.visualizzaTreCampi();               //Punto 6

        gestione.cercaPerIndicator("Life expectancy"); //Punto 7

        gestione.modificaRecord(1, 9, "80.5");       //Punto 8

        gestione.cancellaLogicamente(2);             //Punto 9
    }
}
