import java.io.*;

public class Main {
    private static final String FILE_INPUT = "paterno_modificato_parte1.csv";
    private static final String FILE_OUTPUT = "paterno_fisso_parte2.csv";

    // Array per lunghezze massime campi (17 campi totali)
    private static int[] maxLunghezzeCampi;
    private static int lunghezzaMaxRecord;

    public static void main(String[] args) {
        // Fase 1: Calcola lunghezze massime
        calcolaMaxLunghezze();

        // Fase 2: Applica padding fisso
        applicaPaddingFisso();
    }

    private static void calcolaMaxLunghezze() {
        maxLunghezzeCampi = new int[17]; // 15 originali + 2 nuovi
        lunghezzaMaxRecord = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_INPUT))) {
            String linea;
            int numLinea = 0;

            while ((linea = br.readLine()) != null) {
                String[] campi = linea.split("\\|", -1);
                int lenRecord = linea.length();

                if (lenRecord > lunghezzaMaxRecord) {
                    lunghezzaMaxRecord = lenRecord;
                }

                for (int i = 0; i < campi.length; i++) {
                    int lenCampo = campi[i].length();
                    if (lenCampo > maxLunghezzeCampi[i]) {
                        maxLunghezzeCampi[i] = lenCampo;
                    }
                }
                numLinea++;
            }

            // Stampa risultati in italiano
            System.out.println("Lunghezza massima del record: " + lunghezzaMaxRecord + " caratteri");
            System.out.println("Lunghezze massime per campo:");
            String[] nomiCampi = {"Indicatore","Gruppo","Stato","Sottogruppo","Fase","Periodo Tempo",
                    "Etichetta Periodo","Data Inizio","Data Fine","Valore","CI Basso","CI Alto",
                    "Intervallo Confidenza","Range Quartile","Flag Soppressione","MioValore","Cancellato"};
            for (int i = 0; i < maxLunghezzeCampi.length; i++) {
                System.out.println("Campo " + (i+1) + " (" + nomiCampi[i] + "): " + maxLunghezzeCampi[i] + " char");
            }

        } catch (IOException e) {
            System.err.println("Errore lettura file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void applicaPaddingFisso() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_INPUT));
             BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_OUTPUT))) {

            String linea;
            int recordsProcessati = 0;

            while ((linea = br.readLine()) != null) {
                String[] campi = linea.split("\\|", -1);
                StringBuilder recordFisso = new StringBuilder();

                // Padding per ogni campo alla sua lunghezza massima
                for (int i = 0; i < campi.length; i++) {
                    if (i > 0) recordFisso.append("|");
                    String campo = campi[i];
                    int spaziNecessari = maxLunghezzeCampi[i] - campo.length();
                    recordFisso.append(campo);
                    recordFisso.append(" ".repeat(Math.max(0, spaziNecessari)));
                }

                // Padding finale al record più lungo se necessario
                while (recordFisso.length() < lunghezzaMaxRecord) {
                    recordFisso.append(" ");
                }
                if (recordFisso.length() > lunghezzaMaxRecord) {
                    recordFisso.setLength(lunghezzaMaxRecord);
                }

                bw.write(recordFisso.toString());
                bw.newLine();
                recordsProcessati++;
            }

            System.out.println("File con record a lunghezza fissa creato: " + FILE_OUTPUT);
            System.out.println("Tutti i " + recordsProcessati + " record hanno ora " + lunghezzaMaxRecord + " caratteri");

        } catch (IOException e) {
            System.err.println("Errore scrittura file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
