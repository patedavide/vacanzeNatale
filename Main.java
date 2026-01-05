import java.io.*;
import java.util.Scanner;

public class Main {
    private static final String FILE_INPUT = "paterno_fisso_parte2.csv";
    private static final int LUNGHEZZA_RECORD = 2456; // Da Parte 2
    private static final int NUM_CAMPI = 17;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== PARTE 3: Aggiungi Record + Visualizza ===");

        // Funzionalità 1: Aggiungi record in coda
        aggiungiRecordEsempio();

        // Funzionalità 2: Visualizza 3 campi significativi
        visualizzaDati(scanner);

        scanner.close();
    }

    private static void aggiungiRecordEsempio() {
        try (BufferedWriter bwAppend = new BufferedWriter(new FileWriter(FILE_INPUT, true))) {

            String nuovoRecord = "Nuovo Indicatore|Nuovo Gruppo|Italia|Adulto|3|202601|" +
                    "Gennaio 2026 - Esempio|20260101|20260131|25.5|23.1|28.0|" +
                    "23.1 - 28.0|24.5-27.8||15|N";

            StringBuilder recordPadded = new StringBuilder(nuovoRecord);
            while (recordPadded.length() < LUNGHEZZA_RECORD) {
                recordPadded.append(" ");
            }
            if (recordPadded.length() > LUNGHEZZA_RECORD) {
                recordPadded.setLength(LUNGHEZZA_RECORD);
            }

            bwAppend.write(recordPadded.toString());
            bwAppend.newLine();

            System.out.println("Nuovo record aggiunto in coda!");
            System.out.println("Anteprima: " + recordPadded.substring(0, 80) + "...");

        } catch (IOException e) {
            System.err.println("Errore aggiunta record: " + e.getMessage());
        }
    }

    private static void visualizzaDati(Scanner scanner) {
        System.out.print("\nInserisci numero record da visualizzare (0 per primo): ");
        int numRecord = scanner.nextInt();

        try (RandomAccessFile raf = new RandomAccessFile(FILE_INPUT, "r")) {
            long pos = (long) numRecord * LUNGHEZZA_RECORD;
            raf.seek(pos);

            byte[] buffer = new byte[LUNGHEZZA_RECORD];
            raf.readFully(buffer);
            String record = new String(buffer).trim();

            String[] campi = record.split("\\|");

            if (campi.length >= 11) {
                System.out.println("\n=== Record " + numRecord + " ===");
                System.out.println("1. Indicatore: " + campi[0].trim());
                System.out.println("3. Stato: " + campi[2].trim());
                System.out.println("10. Valore: " + campi[9].trim());
                System.out.println("16. MioValore: " + campi[15].trim());
                System.out.println("17. Cancellato: " + campi[16].trim());
            } else {
                System.out.println("Record non trovato o corrotto (campi: " + campi.length + ")");
            }

        } catch (IOException e) {
            System.err.println("Errore lettura record: " + e.getMessage());
        }
    }
}
