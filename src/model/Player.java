package model;

import java.security.cert.PolicyNode;

public class Player {
    public int score;
    public String pseudo;
    public int munition;
    public int pointGagneParBalleEnJeu;

    public Player(String s, int m) {
        pseudo = s;
        munition = m;
        score = 0;
        pointGagneParBalleEnJeu = 0;
    }

    public void calculScore(boolean detruit, int facteur, boolean balleEnJeu) {
        pointGagneParBalleEnJeu += facteur;
        if(balleEnJeu) {
            if (detruit){
                pointGagneParBalleEnJeu += 5;
                if(pointGagneParBalleEnJeu >= 150 && pointGagneParBalleEnJeu< 300 ) {
                    multiplicateurPoints(2);
                }else if (pointGagneParBalleEnJeu >= 300 && pointGagneParBalleEnJeu< 500) {
                    multiplicateurPoints(3);
                }else if(pointGagneParBalleEnJeu >= 500 && pointGagneParBalleEnJeu< 700) {
                    multiplicateurPoints(4);
                }else if(pointGagneParBalleEnJeu > 700) {
                    multiplicateurPoints(5);
                }
            }
        }
        score += pointGagneParBalleEnJeu;
        if(pointGagneParBalleEnJeu > 0) {
            pointGagneParBalleEnJeu -= 10;
        }
    }
    public void multiplicateurPoints(int multiplicateur) {
        pointGagneParBalleEnJeu += 5*multiplicateur;
    }
}
