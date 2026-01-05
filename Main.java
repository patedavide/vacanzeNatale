import java.io.*;
import java.util.Scanner;

public class Main {
    private static final String FILE_INPUT = "paterno_fisso_parte2.csv";
    private static final int LUNGHEZZA_RECORD = 333;  // I TUOI dati!

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== PARTE 3: Aggiungi + Visualizza ===");

        aggiungiRecordEsempio();
        visualizzaDati(scanner);

        scanner.close();
    }

    private static void aggiungiRecordEsempio() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_INPUT, true))) {
            // Record esempio ITALIA
            String nuovoRecord = "Terapia Mentale,Italia,Adulto,2026,25.5,23.1,28.0,23.1-28.0,15,N";

            // Padding a 333 char
            StringBuilder padded = new StringBuilder(nuovoRecord);
            while (padded.length() < LUNGHEZZA_RECORD) {
                padded.append(" ");
            }

            bw.write(padded.toString());
            bw.newLine();

            System.out.println(" Nuovo record ITALIA aggiunto!");

        } catch (IOException e) {
            System.err.println("Errore: " + e.getMessage());
        }
    }

    private static void visualizzaDati(Scanner scanner) {
        System.out.print("Visualizza record # (0=primo): ");
        int numRecord = scanner.nextInt();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_INPUT))) {
            String linea;
            int riga = 0;

            while ((linea = br.readLine()) != null && riga <= numRecord) {
                if (riga == numRecord) {
                    String[] campi = linea.split(",", -1);
                    System.out.println("\n=== RECORD " + numRecord + " ===");
                    System.out.println("1. Indicatore: " + (campi.length > 0 ? campi[0].trim() : "N/A"));
                    System.out.println("3. Stato: " + (campi.length > 2 ? campi[2].trim() : "N/A"));
                    System.out.println("10. Valore: " + (campi.length > 9 ? campi[9].trim() : "N/A"));
                    return;
                }
                riga++;
            }
            System.out.println("Record " + numRecord + " non trovato");
        } catch (IOException e) {
            System.err.println("Errore: " + e.getMessage());
        }
    }
}
