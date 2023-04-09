package model;
import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable{
    public int score;
    public String pseudo;
    public int munition;
    public ArrayList<ArrayList<Obstacle>> liste = new ArrayList<ArrayList<Obstacle>>();

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
