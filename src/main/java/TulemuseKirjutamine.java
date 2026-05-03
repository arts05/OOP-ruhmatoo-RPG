import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TulemuseKirjutamine {
    private static final String failinimi = "mängu_tulemused.txt";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public static void kirjutaTulemus(Kangelane kangelane, boolean võit, int voorudeArv, LocalDateTime algusaeg, LocalDateTime lõpuaeg) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(failinimi, true))) {
            pw.println("====================================");
            pw.println("Mängu aeg: " + algusaeg.format(formatter) + " --- " + lõpuaeg.format(formatter));
            pw.println("Tulemus: " + (võit ? "Võit" : "Kaotus"));
            pw.println("Kangelane: " + kangelane.getNimi());
            pw.println("Allesjäänud HP: " + kangelane.getHp() + "/" + kangelane.getMaxHp());
            pw.println("Voorude arv: " + voorudeArv);
            pw.println("====================================");
            pw.println();
        } catch (IOException e) {
            System.out.println("Tulemuse faili kirjutamisel tekkis viga: " + e.getMessage());
        }
    }
}
