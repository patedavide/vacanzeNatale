import java.io.*;

public class GestioneFileCSV {

    private String nomeFileInput;
    private String nomeFileTemp;
    private String separatore;
    private int lunghezzaRecordFissa = -1;

    public GestioneFileCSV(String nomeFileInput, String nomeFileTemp, String separatore) {
        this.nomeFileInput = nomeFileInput;
        this.nomeFileTemp = nomeFileTemp;
        this.separatore = separatore;
    }

    // Punto numero 1
    public void aggiungiCampiFinali() {
        try (BufferedReader br = new BufferedReader(new FileReader(nomeFileInput));
             BufferedWriter bw = new BufferedWriter(new FileWriter(nomeFileTemp))) {

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
        try (BufferedReader br = new BufferedReader(new FileReader(nomeFileInput))) {
            String intestazione = br.readLine();
            return intestazione.split(separatore, -1).length;
        } catch (IOException e) {
            return 0;
        }
    }

    //Punto numero 3
    public int calcolaLunghezzaMassimaRecord() {
        int max = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(nomeFileInput))) {
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

    //Punto numero 3 avanzato
    public int[] calcolaLunghezzaMassimaCampi() {
        int[] maxCampi = null;

        try (BufferedReader br = new BufferedReader(new FileReader(nomeFileInput))) {
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
        } catch (IOException e) {
            System.err.println("Errore: " + e.getMessage());
        }

        return maxCampi;
    }

    //Punto numero 4
    public void rendiRecordFissi() {

        if (lunghezzaRecordFissa <= 0)
            calcolaLunghezzaMassimaRecord();

        try (BufferedReader br = new BufferedReader(new FileReader(nomeFileInput));
             BufferedWriter bw = new BufferedWriter(new FileWriter(nomeFileTemp))) {

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
                new FileWriter(nomeFileInput, true))) {

            bw.newLine();
            bw.write(String.join(separatore, campi));

        } catch (IOException e) {
            System.err.println("Errore: " + e.getMessage());
        }
    }

    //Punto numero 6
    public void visualizzaTreCampi(int c1, int c2, int c3) {
        try (BufferedReader br = new BufferedReader(new FileReader(nomeFileInput))) {
            br.readLine(); // intestazione
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] campi = linea.split(separatore, -1);
                System.out.println(campi[c1] + " | " + campi[c2] + " | " + campi[c3]);
            }
        } catch (IOException e) {
            System.err.println("Errore: " + e.getMessage());
        }
    }

    // Punto numero 7
    public void cercaPerCampo(int indiceCampo, String valore) {
        try (BufferedReader br = new BufferedReader(new FileReader(nomeFileInput))) {
            br.readLine();
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] campi = linea.split(separatore, -1);
                if (campi[indiceCampo].trim().equals(valore)) {
                    System.out.println("Record trovato: " + linea);
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
        try (BufferedReader br = new BufferedReader(new FileReader(nomeFileInput));
             BufferedWriter bw = new BufferedWriter(new FileWriter(nomeFileTemp))) {

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
        try (BufferedReader br = new BufferedReader(new FileReader(nomeFileInput));
             BufferedWriter bw = new BufferedWriter(new FileWriter(nomeFileTemp))) {

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
        new File(nomeFileInput).delete();
        new File(nomeFileTemp).renameTo(new File(nomeFileInput));
    }
}
