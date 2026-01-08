public class GestioneFileCSV {
    private String nomeFileInput;
    private String nomeFileTemp;
    private String separatore;
    private int[] lunghezzeCampiMax;
    private int CAMPO_MIOVALORE = 16;
    private int CAMPO_CANCELLATO = 17;

    public GestioneFileCSV(String nomeFileInput, String nomeFileTemp, String separatore, int[] lunghezzeCampiMax) {
        this.nomeFileInput = nomeFileInput;
        this.nomeFileTemp = nomeFileTemp;
        this.separatore = separatore;
        this.lunghezzeCampiMax = lunghezzeCampiMax;
    }

}
