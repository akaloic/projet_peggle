package model;

import java.security.cert.PolicyNode;

public class Player {
    public int score;
    public String pseudo;
    public int munition;
    public int pointGagneParBalleEnJeu = 0;

    public Player(String s, int m) {
        pseudo = s;
        munition = m;
        score = 0;
    }

    public void calculScore(boolean detruit, int facteur) {
        score += facteur;
        int pointGagneParBalleEnJeu = facteur;
        if (detruit){
            pointGagneParBalleEnJeu += 5;
            score += 5;
        }
    }
    public void multiplicateurPoints(int multiplicateur) {
        score = score + 5*multiplicateur;
    }
    
}
