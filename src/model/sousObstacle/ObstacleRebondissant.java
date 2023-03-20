package model.sousObstacle;

import model.Obstacle;
import java.awt.*;
import view.View;

public class ObstacleRebondissant extends Obstacle{ // Obstacle ayant une vie infinie, ne fera qu'en sorte que la balle rebondit sur l'obstacle

    protected final static boolean estMort = false;

    public ObstacleRebondissant(double x, double y) {
        super(x, y, 50, 50, estMort, 10000000);
    }
    public ObstacleRebondissant(double x, double y, double rayon) {
        super(x, y, rayon, rayon, estMort, 10000000);
    }
    public void dessine(Graphics g){
        int gx=(int)(this.x*View.ratioX);
        int gy=(int)(this.y*View.ratioY);
        int gw=(int)(this.largeur*View.ratioX);
        int gh=(int)(this.hauteur*View.ratioY);
        g.fillOval(gx,gy,gw,gh);
    }
}