package model;

public class Obstacle extends Corps{

    protected boolean estMort = false;
    protected int vie;

    public Obstacle(double x, double y, double largeur, double hauteur, int vie) {
        super(x, y, largeur, hauteur);
        this.vie = vie;
    }
    
}