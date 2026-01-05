import java.io.*;

public class Main {
    private static final String FILE_INPUT = "paterno_modificato_parte1.csv";
    private static final String FILE_OUTPUT = "paterno_fisso_parte2.csv";
    private static int[] maxLunghezzeCampi;
    private static int lunghezzaMaxRecord;

    public static void main(String[] args) {
        calcolaMaxLunghezze();
        applicaPaddingFisso();
    }

    private static void calcolaMaxLunghezze() {
        maxLunghezzeCampi = new int[17];
        lunghezzaMaxRecord = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_INPUT))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] campi = linea.split(",", -1);  // VIRGOLA!
                int lenRecord = linea.length();

                if (lenRecord > lunghezzaMaxRecord) {
                    lunghezzaMaxRecord = lenRecord;
                }

                for (int i = 0; i < Math.min(campi.length, maxLunghezzeCampi.length); i++) {
                    maxLunghezzeCampi[i] = Math.max(maxLunghezzeCampi[i], campi[i].length());
                }
            }

            System.out.println(" Lunghezza max record: " + lunghezzaMaxRecord);
            System.out.println("File con padding creato: " + FILE_OUTPUT);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void applicaPaddingFisso() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_INPUT));
             BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_OUTPUT))) {

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] campi = linea.split(",", -1);
                StringBuilder recordFisso = new StringBuilder();

                for (int i = 0; i < Math.min(campi.length, maxLunghezzeCampi.length); i++) {
                    if (i > 0) recordFisso.append(",");
                    String campo = campi[i];
                    recordFisso.append(campo);
                    recordFisso.append(" ".repeat(maxLunghezzeCampi[i] - campo.length()));
                }

                while (recordFisso.length() < lunghezzaMaxRecord) {
                    recordFisso.append(" ");
                }

                bw.write(recordFisso.toString());
                bw.newLine();
            }
            System.out.println(" PARTE 2 COMPLETATA!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
