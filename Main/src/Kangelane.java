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
        if (hp < 0) {
            hp = 0;
        }
    }

    public void lõpetaKord() {
        peidus = false;
    }

    public void väljastaSeis() {
        System.out.println(nimi + " | HP: " + hp + "/" + maxHp);
    }

    public int tabamusTõenäosus(Rünnak rünnak, Lohe lohe, KehaOsa sihtmärk) {
        int tõenäosus;

        if (rünnak == Rünnak.möök()) {
            tõenäosus = meleeTäpsus;
        } else {
            tõenäosus = vibuTäpsus;
        }

        if (lohe.lendab()) {
            if (rünnak == Rünnak.möök()) {
                tõenäosus -= 35;
            } else {
                tõenäosus += 10;
            }
        } else {
            if (rünnak == Rünnak.möök()) {
                tõenäosus += 10;
            }
        }

        if (sihtmärk == lohe.getPea()) {
            tõenäosus -= 20;
        } else if (sihtmärk == lohe.getTorso()) {
            tõenäosus += 10;
        } else if (sihtmärk == lohe.getVasakTiib() || sihtmärk == lohe.getParemTiib()) {
            if (lohe.lendab() && rünnak == Rünnak.tulega()) {
                tõenäosus += 5;
            }
        } else if (sihtmärk == lohe.getParemJalg() || sihtmärk == lohe.getVasakJalg()) {
            if (lohe.lendab()) {
                tõenäosus -= 10;
            }
        }

        if (peidus) {
            tõenäosus -= 10;
        }

        if (tõenäosus < 5) {
            tõenäosus = 5;
        }
        if (tõenäosus > 95) {
            tõenäosus = 95;
        }

        return tõenäosus;
    }

    public int teeHaiget(Rünnak rünnak, Lohe lohe) {
        int damage;

        if (rünnak == Rünnak.mõõk()) {
            damage = Täring.veereta(15, 25);
        } else {
            damage = Täring.veereta(12, 20);
        }

        if (this instanceof Warrior && rünnak = Rünnak.mõõk() && !lohe.lendab()) {
            damage += 6;
        }

        if (this instanceof Archer && rünnak = Rünnak.vibu() && lohe.lendab()) {
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
