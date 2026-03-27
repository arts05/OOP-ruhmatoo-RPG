public class KehaOsa {
    private String nimi;
    private int maxHp;
    private int hp;

    public KehaOsa(String nimi, int maxHp) {
        this.nimi = nimi;
        this.maxHp = maxHp;
        this.hp = maxHp;
    }

    public void saaHaiget(int damage) {
        hp -= damage;
        if (hp < 0) {
            hp = 0;
        }
    }

    public boolean hävitatud() {
        return hp <= 0;
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
}
