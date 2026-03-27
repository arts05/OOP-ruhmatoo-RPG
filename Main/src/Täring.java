import java.util.Random;

public class Täring {
    // Üks Random objekt kogu programmi jaoks
    private static final Random random = new Random();

    public static int veereta(int min, int max) {
        // Tagastab juhuarvu min-max
        return random.nextInt(max - min + 1) + min;
    }

    public static int veeretaProtsent() {
        // Tagastab 1-100
        return veereta(1, 100);
    }
}
