package model.sousObjet.sousObstacle;

import model.sousObjet.Obstacle;

public class ObstacleRectangulaire extends Obstacle {
    
    public ObstacleRectangulaire(double x, double y, double largeur, double hauteur, boolean estMort, int vie) {
        super(x, y, largeur, hauteur, estMort, vie);
    }
    
}