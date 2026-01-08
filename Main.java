import java.io.*;
import java.util.Scanner;

public class Main {
    private static final String FILE_INPUT = "paterno_fisso_parte2.csv";
    private static final String TEMP_FILE = "temp_cancellazione.csv";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("PARTE 5: Cancellazione Logica");

        cancellaLogicamente(scanner);
        debugTuttiCampi(scanner);

        scanner.close();
    }

    private static void cancellaLogicamente(Scanner scanner) {
        System.out.print("Record da cancellare (numero): ");
        int recordDaCancellare = scanner.nextInt();
        scanner.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_INPUT));
             BufferedWriter bw = new BufferedWriter(new FileWriter(TEMP_FILE))) {

            String linea;
            int rigaCorrente = 0;

            while ((linea = br.readLine()) != null) {
                if (rigaCorrente == recordDaCancellare) {
                    String[] campi = linea.split(",", -1);
                    System.out.println("Record " + rigaCorrente + " ha " + campi.length + " campi");

                    // FORZA ULTIMO CAMPO = "S"
                    campi[campi.length - 1] = "S";

                    // Ricostruisci
                    StringBuilder nuovaRiga = new StringBuilder();
                    for (int i = 0; i < campi.length; i++) {
                        if (i > 0) nuovaRiga.append(",");
                        nuovaRiga.append(campi[i]);
                    }
                    // Padding ESATTO 333
                    while (nuovaRiga.length() < 333) nuovaRiga.append(" ");
                    if (nuovaRiga.length() > 333) nuovaRiga.setLength(333);

                    bw.write(nuovaRiga.toString());
                    System.out.println("Ultimo campo impostato = 'S'");
                } else {
                    bw.write(linea);
                }
                bw.newLine();
                rigaCorrente++;
            }

            // Sostituisci
            new File(FILE_INPUT).delete();
            new File(TEMP_FILE).renameTo(new File(FILE_INPUT));
            System.out.println(" Record " + recordDaCancellare + " modificato");

        } catch (IOException e) {
            System.err.println("Errore: " + e.getMessage());
        }
    }

    private static void debugTuttiCampi(Scanner scanner) {
        System.out.print("Record # : ");
        int debugRecord = scanner.nextInt();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_INPUT))) {
            String linea;
            int riga = 0;

            while ((linea = br.readLine()) != null && riga <= debugRecord) {
                if (riga == debugRecord) {
                    String[] campi = linea.split(",", -1);
                    System.out.println("\n RECORD " + debugRecord + " (" + campi.length + " campi) ");

                    // STAMPA TUTTI I CAMPI
                    for (int i = 0; i < Math.min(20, campi.length); i++) {
                        String valore = campi[i].trim();
                        System.out.println("Campo " + (i+1) + ": '" + valore + "' (" + valore.length() + " char)");
                    }
                    if (campi.length > 20) {
                        System.out.println("... + " + (campi.length - 20) + " campi");
                    }
                    return;
                }
                riga++;
            }
        } catch (IOException e) {
            System.err.println("Errore: " + e.getMessage());
        }
    }
}
