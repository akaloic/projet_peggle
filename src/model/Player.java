package model;

public class Player {
    public int score;
    public String pseudo;
    public int munition;

    public Player(String s, int m) {
        pseudo = s;
        munition = m;
        score = 0;
    }

    public void calculScore(boolean detruit, int facteur) {
        score += facteur;
        if (detruit)
            score += 5;
    }
}
