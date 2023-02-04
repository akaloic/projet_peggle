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

    // ---------GETTER SETTER---------
    // ---------GETTER SETTER---------

}