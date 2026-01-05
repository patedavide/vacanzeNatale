import java.io.*;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    private static final String FILE_INPUT = "paterno.csv";
    private static final String FILE_OUTPUT = "paterno_modificato_parte1.csv";

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_INPUT));
             BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_OUTPUT))) {

            String linea;
            boolean primaLinea = true;

            while ((linea = br.readLine()) != null) {
                // DEBUG: mostra separatore reale
                System.out.println("DEBUG Riga: " + linea.substring(0, 50));

                // PROVA DIVERSI SEPARATORI
                String[] campi = linea.split("\\|", -1); // Pipe
                if (campi.length == 1) {
                    campi = linea.split(",", -1); // Virgola
                    System.out.println("Usato separatore VIRGOLA");
                }

                System.out.println("Numero campi nel record: " + campi.length);

                if (primaLinea) {
                    String nuovoHeader = linea + ",miovalore,cancellato";
                    bw.write(nuovoHeader);
                    primaLinea = false;
                } else {
                    int miovalore = ThreadLocalRandom.current().nextInt(10, 21);
                    String nuovoRecord = linea + "," + miovalore + ",N";
                    bw.write(nuovoRecord);
                }
                bw.newLine();
            }

            System.out.println("File modificato salvato: " + FILE_OUTPUT);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
