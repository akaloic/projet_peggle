package model.sousObstacle;

import model.Obstacle;
import model.Pegs;

public class PegEtoile extends Pegs{
    private double height;
    private double width;
    public PegEtoile(double x, double y, double largeur, double hauteur, boolean estMort, int vie) {
        super(x,y,vie,0);
        this.width = largeur;
        this.height = hauteur;
    }
}
