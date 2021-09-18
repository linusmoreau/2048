public class ScoreKeeper {
    private int score;
    static int best_score;

    public ScoreKeeper() {
        score = 0;
    }

    public int getScore() {
        return score;
    }

    public int getBestScore() {
        return best_score;
    }

    public void addScore(int amount) {
        score += amount;
        if (score > best_score) {
            best_score = score;
        }
    }

    public void setScore(int s) {
        score = s;
    }

    public void setBestScore(int s) {
        best_score = s;
    }
}
