
package model;
import java.io.Serializable;
import java.util.ArrayList;
import controller.Sauvegarde;
public class Player implements Serializable{
    public int score;
    public String pseudo;
    public int munition;
    public int[]listeScore = new int[5];
    public ArrayList<Integer> listeScoreEdit = new ArrayList<Integer>();
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