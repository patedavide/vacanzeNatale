import java.io.*;
import java.util.Random;

public class GestioneFileCSV {

    private String fileInput;
    private String fileTemp;
    private String separatore;
    private int lunghezzaRecordFissa = -1;

    // Indici dei campi REALI che vogliamo visualizzare
    public static final int CAMPO_INDICATOR = 0;
    public static final int CAMPO_STATE = 2;
    public static final int CAMPO_VALUE = 9;

    private Random random = new Random();

    public GestioneFileCSV(String fileInput, String fileTemp, String separatore) {
        this.fileInput = fileInput;
        this.fileTemp = fileTemp;
        this.separatore = separatore;
    }

    // Punto 1
    public void aggiungiCampiFinali() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileInput));
             BufferedWriter bw = new BufferedWriter(new FileWriter(fileTemp))) {

            String linea;
            boolean primaRiga = true;

            while ((linea = br.readLine()) != null) {
                if (primaRiga) {
                    // intestazione
                    bw.write(linea + separatore + "miovalore" + separatore + "cancellato");
                    primaRiga = false;
                } else {
                    int valore = 10 + random.nextInt(11); // 10<=X<=20
                    bw.write(linea + separatore + valore + separatore + "N");
                }
                bw.newLine();
            }

            sostituisciFile();

        } catch (IOException e) {
            System.err.println("Errore: " + e.getMessage());
        }
    }

    // Punto 2
    public int contaCampi() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileInput))) {
            String intestazione = br.readLine();
            return intestazione.split(separatore, -1).length;
        } catch (IOException e) {
            return 0;
        }
    }

    // Punto 3
    public int calcolaLunghezzaMassimaRecord() {
        int max = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(fileInput))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.length() > max)
                    max = linea.length();
            }
        } catch (IOException e) {
            System.err.println("Errore: " + e.getMessage());
        }
        lunghezzaRecordFissa = max;
        return max;
    }

    // Punto 4
    public void rendiRecordFissi() {
        if (lunghezzaRecordFissa <= 0)
            calcolaLunghezzaMassimaRecord();

        try (BufferedReader br = new BufferedReader(new FileReader(fileInput));
             BufferedWriter bw = new BufferedWriter(new FileWriter(fileTemp))) {

            String linea;
            while ((linea = br.readLine()) != null) {
                StringBuilder sb = new StringBuilder(linea);
                while (sb.length() < lunghezzaRecordFissa)
                    sb.append(" ");
                bw.write(sb.toString());
                bw.newLine();
            }

            sostituisciFile();

        } catch (IOException e) {
            System.err.println("Errore: " + e.getMessage());
        }
    }

    // Punto 5
    public void aggiungiRecord(String[] campi) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileInput, true))) {
            bw.newLine();
            bw.write(String.join(separatore, campi));
        } catch (IOException e) {
            System.err.println("Errore: " + e.getMessage());
        }
    }

    // Punto 6
    public void visualizzaTreCampi() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileInput))) {
            String intestazione = br.readLine(); // intestazione
            System.out.println("=== Visualizzazione dei dati più importanti ===");
            System.out.println("Indicator | State | Value");

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] campi = linea.split(separatore, -1);

                String indicator = campi.length > CAMPO_INDICATOR ? campi[CAMPO_INDICATOR] : "";
                String state = campi.length > CAMPO_STATE ? campi[CAMPO_STATE] : "";
                String value = campi.length > CAMPO_VALUE ? campi[CAMPO_VALUE] : "";

                System.out.println(indicator + " | " + state + " | " + value);
            }
        } catch (IOException e) {
            System.err.println("Errore: " + e.getMessage());
        }
    }

    // Punto 7
    public void cercaPerIndicator(String indicator) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileInput))) {
            br.readLine(); // intestazione
            String linea;
            boolean trovato = false;

            while ((linea = br.readLine()) != null) {
                String[] campi = linea.split(separatore, -1);
                if (campi.length > CAMPO_INDICATOR && campi[CAMPO_INDICATOR].equalsIgnoreCase(indicator)) {
                    System.out.println("Record trovato:\n" + linea);
                    trovato = true;
                    break;
                }
            }

            if (!trovato) System.out.println("Record non trovato.");

        } catch (IOException e) {
            System.err.println("Errore: " + e.getMessage());
        }
    }

    // Punto 8
    public void modificaRecord(int riga, int campo, String nuovoValore) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileInput));
             BufferedWriter bw = new BufferedWriter(new FileWriter(fileTemp))) {

            String linea;
            int rigaCorrente = 0;

            while ((linea = br.readLine()) != null) {
                if (rigaCorrente == riga) {
                    String[] campi = linea.split(separatore, -1);
                    if (campo < campi.length)
                        campi[campo] = nuovoValore;
                    bw.write(String.join(separatore, campi));
                } else {
                    bw.write(linea);
                }
                bw.newLine();
                rigaCorrente++;
            }

            sostituisciFile();

        } catch (IOException e) {
            System.err.println("Errore: " + e.getMessage());
        }
    }

    // Punto 9
    public void cancellaLogicamente(int riga) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileInput));
             BufferedWriter bw = new BufferedWriter(new FileWriter(fileTemp))) {

            String linea;
            int rigaCorrente = 0;

            while ((linea = br.readLine()) != null) {
                if (rigaCorrente == riga) {
                    String[] campi = linea.split(separatore, -1);
                    if (campi.length > 0)
                        campi[campi.length - 1] = "S"; // marca come cancellato
                    bw.write(String.join(separatore, campi));
                } else {
                    bw.write(linea);
                }
                bw.newLine();
                rigaCorrente++;
            }

            sostituisciFile();

        } catch (IOException e) {
            System.err.println("Errore: " + e.getMessage());
        }
    }

    // Sostituisce il file originale con il file temporaneo
    private void sostituisciFile() {
        new File(fileInput).delete();
        new File(fileTemp).renameTo(new File(fileInput));
    }
}
