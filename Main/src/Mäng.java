import java.util.Scanner;

public class Mäng {
    private static Scanner scanner = new Scanner(System.in);
    private static Kaitse kaitse = new Kaitse();

    public static void start() {
        System.out.println("*****************************************************************************************");
        System.out.println("Sa oled üks vapper sõdalane, kelle abi on siinsete maade kuningas palunud, ning seda ühel");
        System.out.println("eesmärgil: pääsa printsess hirmsa lohe käest. Rännates läbi mägede, oled peagi kohal ning");
        System.out.println("valmis temaga silmitsi seisma...");
        System.out.println("*****************************************************************************************");
        System.out.println("Kes sa täpsemalt oled? Oled sa täpse silma ja käega või oled sa suuteline oma löökidega");
        System.out.println("igat vastast vappuma panema? Tee oma valik:");
        System.out.println("1 - Mõõgamees. Annad matse ja talud neid rohkem.");
        System.out.println("2 - Vibumees. Suurem täpsus lendava lohe vastu, sul tasub vahet hoida.");

        Kangelane kangelane = null;
        while (kangelane == null) {
            System.out.println("(1/2): ");
            String valik = scanner.nextLine();
            if (valik.equals("1")) {
                kangelane = new Warrior();
            } else if (valik.equals("2")) {
                kangelane = new Archer();
            } else {
                System.out.println("Otsid maagi? Paraku on ainult need valikud! Sisesta kas 1 või 2, et jätkata.");
            }
        }

        System.out.println("\nOled jõudnud lohe kantsi. Elukas näeb, et taamal paistab " + kangelane.getNimi() + ".");
        System.out.println("Muidugi, tema ei tea mis su nimi on. Aga ega sa ise ka ei teadnud veel.");
        System.out.println("Sinu relvaks on " + kangelane.getRelvaNimi() + ".");

        Lohe lohe = new Lohe();
        System.out.println("\nVõitlus algab!");
        System.out.println("Märkad lohet õhus tiirutamas. Ta on valmis ründama!");

        // Loome esimese tegevuse ette ära, et saaksime vihjet kuvada
        LoheTegevus järgmineTegevus = lohe.valiTegevus();

        // Põhiline mängutsükkel
        while (kangelane.kasElus() && lohe.kasElus()) {
            System.out.println("\n************************************************************************************");
            // Mängijale antakse vihje
            System.out.println("Hoiad lohel silma peal ja märkad: " + järgmineTegevus.getVihje());
            kangelane.väljastaSeis();
            lohe.väljastaSeis();
            System.out.println("************************************************************************************");

            // 1. kangelase käik
            kangelaneKäik(kangelane, lohe);

            // Kontrollime, kas lohe suri rünnakuga
            if (!lohe.kasElus()) {
                break;
            }

            // 2. Lohe käik
            loheKäik(kangelane, lohe, järgmineTegevus);

            // 3. Vooru lõpetamise tegevused (peidust väljatulek, viha langemine)
            kangelane.lõpetaKord();
            lohe.lõpetaKord();

            järgmineTegevus = lohe.valiTegevus();
        }

        // Mängu lõpptulemus
        System.out.println("\n#####=====-----{ MÄNG LÄBI }-----=====#####");
        if (kangelane.kasElus()) {
            System.out.println("Palju õnne! Alistasid lohe ja printsess on päästetud!");
        } else {
            System.out.println("Kõik läheb silme eest pimedaks. Lohe osutus liiga tugevaks. Sinu teekond lõppes siin.");
        }
    }

    private static void kangelaneKäik(Kangelane kangelane, Lohe lohe) {
        System.out.println("\nSinu kord! Mida teed?");
        System.out.println("1 - Ründa");
        System.out.println("2 - Peida ennast");

        String tegevusValik = "";
        while (!tegevusValik.equals("1") && !tegevusValik.equals("2")) {
            System.out.print("Valik: ");
            tegevusValik = scanner.nextLine();
        }

        if (tegevusValik.equals("2")) {
            kangelane.setPeidus(true);
            System.out.println(kangelane.getNimi() + " otsib kiiresti varju ja tõmbab ennast kaitsesse!");
            return; // Käik on tehtud
        }

        // Kui valiti rünnak, määrame rünnaku tüübi vastavalt klassile
        Rünnak valitudRünnak = (kangelane instanceof Warrior) ? Rünnak.mõõk() : Rünnak.vibu();

        System.out.println("\nMillist kehaosa sihid?");
        System.out.println("1 - Pea (HP: " + lohe.getPea().getHp() + ")");
        System.out.println("2 - Kere (HP: " + lohe.getTorso().getHp() + ")");
        System.out.println("3 - Vasak tiib (HP: " + lohe.getVasakTiib().getHp() + ")");
        System.out.println("4 - Parem tiib (HP: " + lohe.getParemTiib().getHp() + ")");
        System.out.println("5 - Vasak jalg (HP: " + lohe.getVasakJalg().getHp() + ")");
        System.out.println("6 - Parem jalg (HP: " + lohe.getParemJalg().getHp() + ")");

        KehaOsa sihtmärk = null;
        while (sihtmärk == null) {
            System.out.print("Valik: ");
            String sihtValik = scanner.nextLine();
            switch (sihtValik) {
                case "1": sihtmärk = lohe.getPea(); break;
                case "2": sihtmärk = lohe.getTorso(); break;
                case "3": sihtmärk = lohe.getVasakTiib(); break;
                case "4": sihtmärk = lohe.getParemTiib(); break;
                case "5": sihtmärk = lohe.getVasakJalg(); break;
                case "6": sihtmärk = lohe.getParemJalg(); break;
                default: System.out.println("Lohel on kuus kehaosa. Vali üks neist!");
            }
        }

        // Kui kehaosa on juba hävitatud, läheb löök raisku
        if (sihtmärk.hävitatud()) {
            System.out.println("See kehaosa on juba hävitatud! Ründasid tühja õhku.");
            return;
        }

        // Arvutame täpsuse ja veeretame täringut
        int tabamusTõenäosus = kangelane.tabamusTõenäosus(valitudRünnak, lohe, sihtmärk);

        if (lohe.lendab() && kangelane instanceof Warrior) {
            System.out.println("Lohe lendab ja mõõgamehena on sul keeruline talle pihta saada. Poleks tal neid tiibu...");
        }

        int vise = Täring.veeretaProtsent();

        System.out.println("\nSinu tabamuse tõenäosus on " + tabamusTõenäosus + "% (Täringuveeretus: " + vise + ")");

        if (vise <= tabamusTõenäosus) {
            int damage = kangelane.teeHaiget(valitudRünnak, lohe);
            sihtmärk.saaHaiget(damage);
            lohe.suurendaViha(2); // Edukas löök vihastab lohet

            System.out.println("Pihtas! Tegid " + sihtmärk.getNimiOmastavas() + " pihta " + damage + " kahju.");

            // Kui jalg hävineb, sunnime lohet järgmine kord leeki heitma
            if ((sihtmärk == lohe.getVasakJalg() || sihtmärk == lohe.getParemJalg()) && sihtmärk.hävitatud()) {
                System.out.println("Sa hävitasid lohe jala!");
                System.out.println("Lohe möirgab valust ja valmistub leeke sülgama!");
                lohe.setSunniLeegigaRünnak(true);
            }
            // Kui tiib hävineb, kukub lohe maha
            if ((sihtmärk == lohe.getVasakTiib() || sihtmärk == lohe.getParemTiib()) && sihtmärk.hävitatud()) {
                if (!lohe.kasLendab() && lohe.lendab()) {
                    System.out.println("Sa purustasid lohe tiiva! Lohe prantsatab raskelt vastu maad ja enam ei tõuse!");
                    lohe.setLendab(false);
                } else {
                    System.out.println("Purustasid lohe tiiva! Ta on sunnitud sinuga maal silmitsi seisma.");
                }
            }
        } else {
            System.out.println("Mööda! Sinu rünnak ei tabanud sihtmärki.");
        }
    }

    private static void loheKäik(Kangelane kangelane, Lohe lohe, LoheTegevus järgmineTegevus) {
        System.out.println("\nLohe kord");
        System.out.println("Lohe tegevus: " + järgmineTegevus.getNimi());

        // Lohe algne kahju
        int sissetulevKahju = lohe.veeretaRünnakuDamage(järgmineTegevus);

        // Kui kangelane on kaitses, rakendame kahju vähendamist
        if (kangelane.isPeidus()) {
            sissetulevKahju = kaitse.blokeeriKahju(kangelane, sissetulevKahju);
        }

        kangelane.saaHaavata(sissetulevKahju);
        System.out.println("Lohe teeb sulle " + sissetulevKahju + " punkti kahju!");
    }
}
