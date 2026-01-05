import java.io.*;
import java.util.Scanner;

public class Main {
    private static final String FILE_INPUT = "paterno_fisso_parte2.csv";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== PARTE 4: Ricerca Record ===");

        System.out.print("Cerca testo (es: Alabama, Florida, Italia): ");
        String cerca = scanner.nextLine().trim().toLowerCase();

        int trovati = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_INPUT))) {
            String linea;
            int riga = 0;

            while ((linea = br.readLine()) != null) {
                // Cerco ovunque nella riga, non solo in un campo
                if (linea.toLowerCase().contains(cerca)) {
                    String[] campi = linea.split(",", -1);

                    System.out.println("\n RECORD " + riga + ":");
                    System.out.println("  Indicatore: " + (campi.length > 0 ? campi[0].trim() : ""));
                    System.out.println("  Gruppo: " + (campi.length > 1 ? campi[1].trim() : ""));
                    System.out.println("  Stato: " + (campi.length > 2 ? campi[2].trim() : ""));
                    System.out.println("  Valore: " + (campi.length > 9 ? campi[9].trim() : ""));
                    System.out.println("  Campo 4 (Subgroup): " + (campi.length > 3 ? campi[3].trim() : ""));
                    System.out.println("  Campo 5: " + (campi.length > 4 ? campi[4].trim() : ""));
                    trovati++;
                    if (trovati >= 5) break; // Mostra max 5 risultati
                }
                riga++;
            }

            if (trovati == 0) {
                System.out.println(" Nessun record contenente '" + cerca + "'");
            }

        } catch (IOException e) {
            System.err.println("Errore: " + e.getMessage());
        }

        scanner.close();
    }
}
