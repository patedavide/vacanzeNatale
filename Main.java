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
                String[] campi = linea.split("\\|", -1);

                System.out.println("Numero campi nel record: " + campi.length); // da 15 a 17

                if (primaLinea) {
                    String nuovoHeader = linea + "|miovalore|cancellato";
                    bw.write(nuovoHeader);
                    primaLinea = false;
                } else {
                    int miovalore = ThreadLocalRandom.current().nextInt(10, 21);
                    String nuovoRecord = linea + "|" + miovalore + "|N";
                    bw.write(nuovoRecord);
                }
                bw.newLine();
            }

            System.out.println("Parte 1 completata. File: " + FILE_OUTPUT);
            System.out.println("Campi originali: 15, totali ora: 17");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
