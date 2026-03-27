public class MängijaRünnak {
    /**
     * Justkui konteinerobjekt, mis hoiab kahte tähtsat infokildu koos:
     * 1) mis tüüpi rünnak valiti
     * 2) millist kehaosa sihitakse
     */
    private Rünnak rünnak;
    private KehaOsa sihtmärk;

    public MängijaRünnak(Rünnak rünnak, KehaOsa sihtmärk) {
        this.rünnak = rünnak;
        this.sihtmärk = sihtmärk;
    }

    public Rünnak getRünnak() {
        return rünnak;
    }

    public KehaOsa getSihtmärk() {
        return sihtmärk;
    }
}
