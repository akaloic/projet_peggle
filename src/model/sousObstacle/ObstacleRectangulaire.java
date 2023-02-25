package model.sousObstacle;
import java.awt.*;
import model.Obstacle;

public class ObstacleRectangulaire extends Obstacle {
    
    public ObstacleRectangulaire(double x, double y) {
        super(x, y, 75, 50, false, 100);
    }
    
    public void dessine(Graphics2D g, double ratioX,double ratioY){
        g.fillRect((int) (x*ratioX),
                    (int) (y*ratioY),
                    (int) (largeur*ratioX),
                    (int) (hauteur*ratioX));
    }
}
