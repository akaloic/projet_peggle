package model.sousObjet;

import model.Objet;

public class Obstacle extends Objet {

    protected boolean estMort; 
    protected int vie;

    public Obstacle(double x, double y, double largeur, double hauteur, double angle, boolean estMort, int vie) {
        super(x, y, largeur, hauteur, angle);
        this.estMort = estMort;
        this.vie = vie;
    }
    public void perdDeLaVie(int degats){
        this.vie-=degats;
        if(this.vie <= 0){
            this.estMort = false;
        }
    }

    // ---------GETTER GETTER---------
    public int getVie(){
        return this.vie;
    }
    public boolean getEstMort(){
        return this.estMort;
    }
    // ---------GETTER SETTER---------
    public void setVie(int pdv){
        this.vie+= pdv;
    }
    public void setEstMort(boolean mort){
        this.estMort = mort;
    }

}