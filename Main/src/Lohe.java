public class Lohe {
    private KehaOsa pea;
    private KehaOsa torso;
    private KehaOsa vasakTiib;
    private KehaOsa paremTiib;
    private KehaOsa paremJalg;
    private KehaOsa vasakJalg;

    private boolean lendab;
    private int viha;
    private boolean sunniLeegigaRünnak;

    public Lohe() {
        pea = new KehaOsa("Pea", 80);
        torso = new KehaOsa("Torso", 150);
        vasakTiib = new KehaOsa("Vasak tiib", 50);
        paremTiib = new KehaOsa("Parem tiib", 50);
        vasakJalg = new KehaOsa("Vasak jalg", 40);
        paremJalg = new KehaOsa("Parem jalg", 40);

        lendab = true;
        viha = 0;
        sunniLeegigaRünnak = false;
    }

    public boolean kasElus() {
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
        System.out.println("Vasak jalg: " vasakJalg.getHp() + "/" + vasakJalg.getMaxHp() +
                " | Parem jalg: " + paremJalg.getHp() + "/" + paremJalg.getMaxHp());
    }

    public boolean kasLendab() {
        return !vasakTiib.hävitatud() && !paremTiib.hävitatud();
    }

    public void suurendaViha(int vihaKogus) {
        viha += vihaKogus;
        if (viha > 10) {
            viha = 10;
        }
    }

    public LoheTegevus valiTegevus() {
        if (!kasLendab()) {
            lendab = false;
        }

        if (sunniLeegigaRünnak) {
            sunniLeegigaRünnak = false;
            if (lendab) {
                return LoheTegevus.heidaLeeki();
            }
        }

        if (lendab) {
            int leegitõenäosus = 15 + viha * 7;
            if (leegitõenäosus > 80) {
                leegitõenäosus = 80;
            }

            int veereta = Täring.veeretaProtsent();

            if (veereta <= leegitõenäosus) {
                return LoheTegevus.heidaLeeki();
            } else {
                return LoheTegevus.ründaSabaga();
            }
        } else {
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
        if (viha > 0) {
            viha--;
        }
    }

    public boolean lendab() {
        return lendab;
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
