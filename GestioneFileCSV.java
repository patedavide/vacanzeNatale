import java.io.*;

public class GestioneFileCSV {

    private String fileInput;
    private String fileTemp;
    private String separatore;
    private int lunghezzaRecordFissa = -1;

    // indici dei campi REALI del CSV
    public static final int CAMPO_INDICATOR = 0;
    public static final int CAMPO_STATE = 2;
    public static final int CAMPO_VALUE = 9;

    public GestioneFileCSV(String fileInput, String fileTemp, String separatore) {
        this.fileInput = fileInput;
        this.fileTemp = fileTemp;
        this.separatore = separatore;
    }

    //Punto numero 1

    public void aggiungiCampiFinali() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileInput));
             BufferedWriter bw = new BufferedWriter(new FileWriter(fileTemp))) {

            String linea;
            boolean primaRiga = true;

            while ((linea = br.readLine()) != null) {
                if (primaRiga) {
                    bw.write(linea + separatore + "miovalore" + separatore + "cancellato");
                    primaRiga = false;
                } else {
                    int valore = 10 + (int)(Math.random() * 11);
                    bw.write(linea + separatore + valore + separatore + "N");
                }
                bw.newLine();
            }

            sostituisciFile();

        } catch (IOException e) {
            System.err.println("Errore: " + e.getMessage());
        }
    }

    //Punto numero 2

    public int contaCampi() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileInput))) {
            String intestazione = br.readLine();
            return intestazione.split(separatore, -1).length;
        } catch (IOException e) {
            return 0;
        }
    }

    //Punto numero 3

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

    //Punto numero 3 bis

    public int[] calcolaLunghezzaMassimaCampi() {
        int[] maxCampi;

        try (BufferedReader br = new BufferedReader(new FileReader(fileInput))) {
            String linea = br.readLine();
            String[] campi = linea.split(separatore, -1);
            maxCampi = new int[campi.length];

            for (int i = 0; i < campi.length; i++)
                maxCampi[i] = campi[i].length();

            while ((linea = br.readLine()) != null) {
                campi = linea.split(separatore, -1);
                for (int i = 0; i < campi.length; i++) {
                    if (campi[i].length() > maxCampi[i])
                        maxCampi[i] = campi[i].length();
                }
            }

            return maxCampi;

        } catch (IOException e) {
            return null;
        }
    }

    //Punto numero 4

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

    //Punto numero 5

    public void aggiungiRecord(String[] campi) {
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(fileInput, true))) {

            bw.newLine();
            bw.write(String.join(separatore, campi));

        } catch (IOException e) {
            System.err.println("Errore: " + e.getMessage());
        }
    }

    //Punto numero 6

    public void visualizzaTreCampi() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileInput))) {
            br.readLine(); // intestazione
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] campi = linea.split(separatore, -1);
                System.out.println(
                        campi[CAMPO_INDICATOR] + " | " +
                                campi[CAMPO_STATE] + " | " +
                                campi[CAMPO_VALUE]
                );
            }
        } catch (IOException e) {
            System.err.println("Errore: " + e.getMessage());
        }
    }

    //Punto numero 7

    public void cercaPerIndicator(String indicator) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileInput))) {
            br.readLine();
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] campi = linea.split(separatore, -1);
                if (campi[CAMPO_INDICATOR].equalsIgnoreCase(indicator)) {
                    System.out.println("Record trovato:\n" + linea);
                    return;
                }
            }
            System.out.println("Record non trovato");
        } catch (IOException e) {
            System.err.println("Errore: " + e.getMessage());
        }
    }

    //Punto numero 8

    public void modificaRecord(int riga, int campo, String nuovoValore) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileInput));
             BufferedWriter bw = new BufferedWriter(new FileWriter(fileTemp))) {

            String linea;
            int rigaCorrente = 0;

            while ((linea = br.readLine()) != null) {
                if (rigaCorrente == riga) {
                    String[] campi = linea.split(separatore, -1);
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

    //Punto numero 9
    public void cancellaLogicamente(int riga) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileInput));
             BufferedWriter bw = new BufferedWriter(new FileWriter(fileTemp))) {

            String linea;
            int rigaCorrente = 0;

            while ((linea = br.readLine()) != null) {
                if (rigaCorrente == riga) {
                    String[] campi = linea.split(separatore, -1);
                    campi[campi.length - 1] = "S";
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

    private void sostituisciFile() {
        new File(fileInput).delete();
        new File(fileTemp).renameTo(new File(fileInput));
    }
}
