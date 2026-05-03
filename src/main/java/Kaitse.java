public class Kaitse {
    private String nimi;

    public Kaitse() {
        this.nimi = "Kaitsepositsioon";
    }

    public int blokeeriKahju(Kangelane kangelane, int sissetulevKahju) {
        // Arvutame, kui palju kahju blokeeritakse. Blokeeritud kahju = baaskaitse + kerge õnnefaktor (1 ... 4)
        int blokeeritud = kangelane.getKaitse() + Täring.veereta(1, 4);

        int tegelikKahju = sissetulevKahju - blokeeritud;

        // Kahju ei saa minna negatiivseks (et kaitse ei raviks mängijat)
        if (tegelikKahju < 0) {
            tegelikKahju = 0;
        }

        System.out.println(">>> " + kangelane.getNimi() + " blokeeris " + blokeeritud + " punkti kahju! <<<");
        return tegelikKahju;
    }

    public String getNimi() {
        return nimi;
    }
}
