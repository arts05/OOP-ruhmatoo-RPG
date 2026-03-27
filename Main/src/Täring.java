import java.util.Random;

public class Täring {
    private static final Random random = new Random();

    public static int veereta(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public static int rollPercent() {
        return veereta(1, 100);
    }
}
