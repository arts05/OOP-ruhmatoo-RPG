import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;

public class Lohe {
    // Lohe eri kehaosad on eraldi objektid
    private KehaOsa pea;
    private KehaOsa torso;
    private KehaOsa vasakTiib;
    private KehaOsa paremTiib;
    private KehaOsa paremJalg;
    private KehaOsa vasakJalg;

    // Kas lohe hetkel lendab
    private boolean lendab;
    // Viha mõjutab tule hetimise tõenäosust
    private int viha;
    // Kui jalg hävineb, sunnime lohe järgmisel käigul kohe leeki kasutama
    private boolean sunniLeegigaRünnak;

    public Lohe(){
        // Vaikeväärtused juhuks, kui failist lugemine ei õnnestu
        int peaHp = 80;
        int torsoHp = 150;
        int vasakTiibHp = 50;
        int paremTiibHp = 50;
        int vasakJalgHp = 40;
        int paremJalgHp = 40;
        boolean algusesLendab = false;
        int algneViha = 0;

        try {
            InputStream inputStream = getClass().getResourceAsStream("/lohe_andmed.txt");

            if (inputStream == null) {
                throw new IOException("Faili lohe_andmed.txt ei leitud!");
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String rida;
            while ((rida = br.readLine()) != null) {
                rida = rida.trim();

                if (rida.isEmpty()) {continue;}

                String[] osad = rida.split("=");
                if (osad.length != 2) {
                    continue;
                }

                String võti = osad[0].trim();
                String väärtus = osad[1].trim();

                if (võti.equals("pea")) {
                    peaHp = Integer.parseInt(väärtus);
                } else if (võti.equals("torso")) {
                    torsoHp = Integer.parseInt(väärtus);
                } else if (võti.equals("vasakTiib")) {
                    vasakTiibHp = Integer.parseInt(väärtus);
                } else if (võti.equals("paremTiib")) {
                    paremTiibHp = Integer.parseInt(väärtus);
                } else if (võti.equals("vasakJalg")) {
                    vasakJalgHp = Integer.parseInt(väärtus);
                } else if (võti.equals("paremJalg")) {
                    paremJalgHp = Integer.parseInt(väärtus);
                } else if (võti.equals("lendab")) {
                    algusesLendab = Boolean.parseBoolean(väärtus);
                } else if (võti.equals("viha")) {
                    algneViha = Integer.parseInt(väärtus);
                }
            }

            br.close();

        } catch (IOException e) {
            System.out.println("Lohe andmete faili lugemisel tekkis viga. Kasutatakse vaikeväärtusi.");
        } catch (NumberFormatException e) {
            System.out.println("Lohe andmete failis oli vigane arv. Kasutatakse vaikeväärtusi.");
        }

        // Loome kehaosad failist saadud või vaikeväärtustega
        pea = new KehaOsa("Pea", peaHp);
        torso = new KehaOsa("Torso", torsoHp);
        vasakTiib = new KehaOsa("Vasak tiib", vasakTiibHp);
        paremTiib = new KehaOsa("Parem tiib", paremTiibHp);
        vasakJalg = new KehaOsa("Vasak jalg", vasakJalgHp);
        paremJalg = new KehaOsa("Parem jalg", paremJalgHp);

        lendab = algusesLendab;
        viha = algneViha;
        sunniLeegigaRünnak = false;

        }

    public boolean kasElus() {
        /**
         * Lohe sureb, kui:
         * 1) mõlemad jalad hävitatud
         * 2) pea on hävitatud
         * 3) torso on hävitatud
         */
        boolean kasJaladLäinud = vasakJalg.hävitatud() && paremJalg.hävitatud();
        boolean peaLäinud = pea.hävitatud();
        boolean torsoLäinud = torso.hävitatud();

        return !(kasJaladLäinud || peaLäinud || torsoLäinud);
    }

    public void väljastaSeis() {
        System.out.println("Lohe | lendab: " + (lendab ? "jah" : "ei") + " | viha: " + viha);
        System.out.println("Pea: " + pea.getHp() + "/" + pea.getMaxHp() +
                " | Keha: " + torso.getHp() + "/" + torso.getMaxHp());
        System.out.println("Vasak tiib: " + vasakTiib.getHp() + "/" + vasakTiib.getMaxHp() +
                " | Parem tiib: " + paremTiib.getHp() + "/" + paremTiib.getMaxHp());
        System.out.println("Vasak jalg: " + vasakJalg.getHp() + "/" + vasakJalg.getMaxHp() +
                " | Parem jalg: " + paremJalg.getHp() + "/" + paremJalg.getMaxHp());
    }

    public boolean saabLennata() {
        // Lohe saab ainult lennata juhul, kui mõlemad tiivad on alles
        return !vasakTiib.hävitatud() && !paremTiib.hävitatud();
    }

    public void suurendaViha(int vihaKogus) {
        viha += vihaKogus;
        if (viha > 10) {
            viha = 10;
        }
    }

    public LoheTegevus valiTegevus() {
        // kui tiivad on hävitatud, siis enam lennata ei saa.
        if (!saabLennata()) {
            lendab = false;
        }

        uuendaLennuSeis();

        // Kui eelenvalt on sunniLeegigaRünnak = true, siis kohe kasutab leeki.
        if (sunniLeegigaRünnak) {
            sunniLeegigaRünnak = false;
            // Saab leeki ainult kasutada siis, kui lendab
            if (lendab) {
                return LoheTegevus.heidaLeeki();
            }
        }

        if (lendab) {
            // Tõenäosus kasvab koos raevuga
            int leegitõenäosus = 15 + viha * 7;
            if (leegitõenäosus > 80) {
                leegitõenäosus = 80;
            }

            int veereta = Täring.veeretaProtsent();

            if (veereta <= leegitõenäosus) {
                // Tagastame heidaLeeki objekti
                return LoheTegevus.heidaLeeki();
            } else {
                // Muidu sabaga rünnak
                return LoheTegevus.ründaSabaga();
            }
        } else {
            // Kui lohe on maas, teeb ta kas jala- või sabarünnaku
            if (vasakJalg.hävitatud() && paremJalg.hävitatud()) {
                return LoheTegevus.ründaSabaga();
            }

            int veereta = Täring.veeretaProtsent();
            if (veereta <= 50) {
                return LoheTegevus.ründaJalaga();
            } else {
                return LoheTegevus.ründaSabaga();
            }
        }
    }

    public int veeretaRünnakuDamage(LoheTegevus lohetegevus) {
        /**
         * LoheTegevus ütleb meile, et mis tüüpi rünnaki on lohe valinud
         * Selle põhjal arvutatakse välja, palju kahju lohe kangelasele teeb.
         */
        if (lohetegevus == LoheTegevus.heidaLeeki()) {
            return Täring.veereta(18,30);
        } else if (lohetegevus == LoheTegevus.ründaSabaga()) {
            return Täring.veereta(12 , 22);
        } else if (lohetegevus == LoheTegevus.ründaJalaga()) {
            return Täring.veereta(14, 22);
        } else {
            return 10;
        }
    }

    public void lõpetaKord() {
        // Iga vooruga raev läheb natukene alla.
        if (viha > 0) {
            viha--;
        }
    }

    public boolean lendab() {
        return lendab;
    }

    public void uuendaLennuSeis() {
        // Kui tiivad katki, siis lennata ei saa
        if (!saabLennata()) {
            lendab = false;
            return;
        }

        int täring = Täring.veeretaProtsent();

        if (!lendab) {
            // Lohe on maas
            // Mida vihasem ta on, seda suurem tõenäosus õhtu tõusta

            int õhtuTõusmiseProtsent = 15 + viha*6;

            if (õhtuTõusmiseProtsent > 75) {
                õhtuTõusmiseProtsent = 75;
            }

            if (täring <= õhtuTõusmiseProtsent) {
                lendab = true;
                System.out.println("Lohe tõuseb õhku.");
            }
        } else {
            // Lohe on õhus.
            // Mida väiksem viha, seda suurem tõenäosus maanduda
            int maandumiseTõenäosus = 60 - viha * 5;

            if (maandumiseTõenäosus < 10) {
                maandumiseTõenäosus = 10;
            }

            if (täring <= maandumiseTõenäosus) {
                lendab = false;
                System.out.println("Lohe maandub.");
            }
        }
    }

    public KehaOsa getVasakJalg() {
        return vasakJalg;
    }

    public KehaOsa getParemJalg() {
        return paremJalg;
    }

    public KehaOsa getParemTiib() {
        return paremTiib;
    }

    public KehaOsa getVasakTiib() {
        return vasakTiib;
    }

    public KehaOsa getTorso() {
        return torso;
    }

    public KehaOsa getPea() {
        return pea;
    }

    public void setLendab(boolean lendab) {
        this.lendab = lendab;
    }

    public void setSunniLeegigaRünnak(boolean sunniLeegigaRünnak) {
        this.sunniLeegigaRünnak = sunniLeegigaRünnak;
    }
}
