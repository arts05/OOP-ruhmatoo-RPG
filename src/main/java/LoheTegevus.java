public class LoheTegevus {
    // Mida lohe järgmisena teeb
    private String nimi;
    private String vihje;

    private static final LoheTegevus leek = new LoheTegevus("Heidab leeki", "Lohe tõmbab sügavalt hinge.");
    private static final LoheTegevus saba = new LoheTegevus("Ründab sabaga", "Lohe tõstab saba kõrgele.");
    private static final LoheTegevus jalg = new LoheTegevus("Lööb jalaga", "Lohe surub keha madalamale ja pingestub.");

    public LoheTegevus(String nimi, String vihje) {
        this.nimi = nimi;
        this.vihje = vihje;
    }

    public static LoheTegevus heidaLeeki() {
        return leek;
    }

    public static LoheTegevus ründaSabaga() {
        return saba;
    }

    public static LoheTegevus ründaJalaga() {
        return jalg;
    }

    public String getNimi() {
        return nimi;
    }

    public String getVihje() {
        return vihje;
    }
}
