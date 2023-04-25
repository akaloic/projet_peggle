
package model;
import java.io.Serializable;
import java.util.ArrayList;
import controller.Sauvegarde;
public class Player implements Serializable{
    public int score;
    public String pseudo;
    public int munition;
    public int progression = 1;
    public int pointGagneParBalleEnJeu;
    public int[]listeScore = new int[5];
    public ArrayList<Integer> listeScoreEdit = new ArrayList<Integer>();
    public ArrayList<ArrayList<Obstacle>> liste = new ArrayList<ArrayList<Obstacle>>();

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
                    pointGagneParBalleEnJeu += 5*1.5;
                }else if (pointGagneParBalleEnJeu >= 300 && pointGagneParBalleEnJeu< 500) {
                    pointGagneParBalleEnJeu += 5*2;
                }else if(pointGagneParBalleEnJeu >= 500 && pointGagneParBalleEnJeu< 700) {
                    pointGagneParBalleEnJeu += 5*3;
                }else if(pointGagneParBalleEnJeu > 700) {
                    pointGagneParBalleEnJeu += 5*4;
                }
            }
        }
        score += pointGagneParBalleEnJeu;
        if(pointGagneParBalleEnJeu > 5) {
            pointGagneParBalleEnJeu -= 5;
        }
    }
    public String getPseudo(){
        return this.pseudo;
    }
    public void setPseudo(String s){
        this.pseudo = s;
    }
    public void setScore(int i){
        if(Sauvegarde.numNiveau == -1){
            if( score > listeScore[i]){
                listeScore[i] = score;
            } 
        }else{
            if( score > listeScoreEdit.get(Sauvegarde.numNiveau)){
                listeScoreEdit.set(Sauvegarde.numNiveau, score);
            } 
        }
    }
}