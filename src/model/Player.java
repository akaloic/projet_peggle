package model;

public class Player {
    protected int score;
    protected String pseudo;
    protected int munitions;

    public Player(int n, String s){
        pseudo=s;
        munitions=n;
        score=0;
    }

    public void setPseudo(String s){
        pseudo=s;
    }
    public void setMunitions(int n){
        munitions=n;
    }
    public void setScore(int n){
        score=n;
    }
    public int getMunitions(){
        return munitions;
    }
    public int getScore(){
        return score;
    }
    public String getPseudo(){
        return pseudo;
    }

    public void addScore(int n){
        score+=n;
    }
}
