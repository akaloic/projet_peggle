package model;

public class Obstacle extends Corps{

    protected boolean estMort = false;

    public Obstacle(double x, double y, double largeur, double hauteur, double vitesseX, double vitesseY) {
        super(x, y, largeur, hauteur, 0, 0);
    }
    
}