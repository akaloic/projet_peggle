package model.sousObstacle;

import model.Obstacle;

public class ObstacleRebondissant extends Obstacle{ // Obstacle ayant une vie infinie, ne fera qu'en sorte que la balle rebondit sur l'obstacle

    protected final static boolean estMort = false;

    public ObstacleRebondissant(double x, double y) {
        super(x, y, 50, 50, estMort, 10000000);
    }
}
