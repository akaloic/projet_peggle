package model.sousObstacle;

import model.Obstacle;
import view.View;
import java.awt.*;
public class ObstacleRectangulaire extends Obstacle {
    
    public ObstacleRectangulaire(double x, double y) {
        super(x, y, 75, 50, false, 100);
    }
    public void dessine(Graphics g){
        int gx=(int)(this.x*View.getRatioX());
        int gy=(int)(this.y*View.getRatioY());
        int gw=(int)(this.largeur*View.getRatioX());
        int gh=(int)(this.hauteur*View.getRatioY());
        g.fillRect(gx,gy,gw,gh);
    }
}
