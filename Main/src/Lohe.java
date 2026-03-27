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


}
