public abstract class Kangelane {
    private String nimi;
    private int maxHp;
    private int hp;
    private int meleeTäpsus;
    private int vibuTäpsus;
    private int kaitse;
    private String relvaNimi;
    private boolean peidus;

    public Kangelane(String nimi, int maxHp, int meleeTäpsus, int vibuTäpsus, int kaitse, String relvaNimi) {
        this.nimi = nimi;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.meleeTäpsus = meleeTäpsus;
        this.vibuTäpsus = vibuTäpsus;
        this.kaitse = kaitse;
        this.relvaNimi = relvaNimi;
        this.peidus = false;
    }

    public boolean kasElus() {
        return hp > 0;
    }

    public void saaHaavata(int loheRünnak) {
        hp -= loheRünnak;

        // HP ei tohi minna negatiivseks, juhul kui negatiivne, siis tegelane on surnud.
        if (hp < 0) {
            hp = 0;
        }
    }

    public void lõpetaKord() {
        // Iga vooru lõpus eemaldame kangelasel peidus oleku, et peitmine kestaks ainult ühe lohe rünnaku jagu
        peidus = false;
    }

    public void väljastaSeis() {
        System.out.println(nimi + " | HP: " + hp + "/" + maxHp);
    }

    /**
     * Arvutab, kui suur tõenäosus on kangelasel lohele pihta saada.
     *
     * @param rünnak   Relvavalik — mõõk või vibu
     * @param lohe     Lohe olek — kas lendab, milliseid kehaosi sihitakse
     * @param sihtmärk Konkreetne kehaosa, mida sihitakse
     * @return Tabamustõenäosus protsendina (5–95)
     */
    public int tabamusTõenäosus(Rünnak rünnak, Lohe lohe, KehaOsa sihtmärk) {
        int tõenäosus;

        // Baastäpsuse arvutamine vastavalt rünnakutüübile.
        if (rünnak == Rünnak.mõõk()) {
            tõenäosus = meleeTäpsus;
        } else {
            tõenäosus = vibuTäpsus;
        }

        // Kui lohe lendab, on vibuga kergem tabada ja mõõgaga raskem
        if (lohe.lendab()) {
            if (rünnak == Rünnak.mõõk()) {
                tõenäosus -= 35;
            } else {
                tõenäosus += 10;
            }
        } else {
            // Kui lohe on maas, saab mõõgaga paremini ligi
            if (rünnak == Rünnak.mõõk()) {
                tõenäosus += 10;
            }
        }

        // Erinevad kehaosad erineva tabamustõenäosusega
        if (sihtmärk == lohe.getPea()) {
            tõenäosus -= 20;
        } else if (sihtmärk == lohe.getTorso()) {
            tõenäosus += 10;
        } else if (sihtmärk == lohe.getVasakTiib() || sihtmärk == lohe.getParemTiib()) {
            if (lohe.lendab() && rünnak == Rünnak.vibu()) {
                tõenäosus += 5;
            }
        } else if (sihtmärk == lohe.getParemJalg() || sihtmärk == lohe.getVasakJalg()) {
            if (lohe.lendab()) {
                tõenäosus -= 10;
            }
        }

        // Kui mängija on peidus, on palju raskem tabada
        if (peidus) {
            tõenäosus -= 10;
        }

        if (tõenäosus < 5)  tõenäosus = 5;
        if (tõenäosus > 95) tõenäosus = 95;

        return tõenäosus;
    }

    public int teeHaiget(Rünnak rünnak, Lohe lohe) {
        int damage;

        // Mõõk teeb natukene rohkem kahju kui vibu
        if (rünnak == Rünnak.mõõk()) {
            damage = Täring.veereta(15, 30);
        } else {
            damage = Täring.veereta(12, 20);
        }

        // Warrior teeb maas oleva lohe vastu mõõgaga rohkem kahju
        if (this instanceof Warrior && rünnak == Rünnak.mõõk() && !lohe.lendab()) {
            damage += 10;
        }

        // Archer teeb lendava lohe vastu vibuga rohkem kahju
        if (this instanceof Archer && rünnak == Rünnak.vibu() && lohe.lendab()) {
            damage += 6;
        }

        return damage;
    }

    public String getNimi() {
        return nimi;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getHp() {
        return hp;
    }

    public int getKaitse() {
        return kaitse;
    }

    public String getRelvaNimi() {
        return relvaNimi;
    }

    public boolean isPeidus() {
        return peidus;
    }

    public void setPeidus(boolean peidus) {
        this.peidus = peidus;
    }
}
