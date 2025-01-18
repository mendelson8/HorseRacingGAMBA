package horses.databases;

public class BetInfo {
    public int horse;
    public int amount;

    public BetInfo(int horse, int amount) {
        this.horse= horse;
        this.amount = amount;
    }

    public int getScore() {
        return horse;
    }

    public void setScore(int horse) {
        this.horse = horse;
    }

    public int getLevel() {
        return amount;
    }

    public void setLevel(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "PlayerStats{" +
                "horse=" + horse +
                ", amount=" + amount +
                '}';
    }
}
