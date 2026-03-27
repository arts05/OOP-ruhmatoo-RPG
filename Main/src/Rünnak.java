public class Rünnak {
    // Millega mängija ründab
    private String name;

    private static final Rünnak mõõk = new Rünnak("Mõõk");
    private static final Rünnak vibu = new Rünnak("Vibu");

    public Rünnak(String name) {
        this.name = name;
    }

    // Tagastab mõõga-objekti
    public static Rünnak mõõk() {
        return mõõk;
    }
    // Tagastab vibu-objekti
    public static Rünnak vibu() {
        return vibu;
    }
}
