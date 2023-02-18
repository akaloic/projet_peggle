package model.sousObstacle;

import model.Obstacle;

public class ObstacleRebondissant extends Obstacle{ // Obstacle ayant une vie infinie, ne fera qu'en sorte que la balle rebondit sur l'obstacle

    protected final static boolean estMort = true;
    public ObstacleRebondissant(double x, double y, double largeur, double hauteur) {
        super(x, y, largeur, hauteur, estMort, 10000000);
    }
    
}
