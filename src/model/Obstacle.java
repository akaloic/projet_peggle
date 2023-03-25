package model;

import java.awt.Graphics;

public class Obstacle extends Objet {

    protected boolean estMort; 
    protected int vie;
    protected int rayon;

    public Obstacle(double x, double y, double largeur, double hauteur, boolean estMort, int vie) {
        super(x, y, largeur, hauteur,0);
        this.estMort = estMort;
        this.vie = vie;
    }
    public void perdDeLaVie(int degats){
        this.vie-=degats;
        if(isDead()){
            this.estMort = false;
        }
    }
    public boolean isDead(){
        return this.vie <= 0;
    }
    public Obstacle(int v){
        super();
        this.vie=v;
    }

    // ---------GETTER GETTER---------
    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }
    public int getVie(){
        return this.vie;
    }
    public boolean getEstMort(){
        return this.estMort;
    }
    public double getWidth(){
        return super.getLargeur();
    }
    public double getHeight(){
        return super.getHauteur();
    }
    public double getRayon(){
        return this.rayon;
    }
    // ---------GETTER SETTER---------
    public void setVie(int pdv){
        this.vie+= pdv;
    }
    public void setEstMort(boolean mort){
        this.estMort = mort;
    }
    public void dessine(Graphics g) {
    }

}