package model;

import view.View;
import java.awt.*;

public class Pegs extends Obstacle {
    public double rayon = 50;

    public Pegs() {
        super(1);
    }

    public Pegs(double x, double y, int v) {
        super(x, y, 50, 50, false, v);
    }

    public Pegs(double x, double y, int v, int rayon) {
        super(x, y, rayon, rayon, false, v);
        this.rayon = rayon;
    }
    public double getRayon(){return this.rayon;}
    public double getDiametre(){return this.rayon*2;}
    public void dessine(Graphics g){
        int gx=(int)(this.x*View.ratioX);
        int gy=(int)(this.y*View.ratioY);
        int gw=(int)(this.rayon*View.ratioX);
        int gh=(int)(this.rayon*View.ratioY);
        g.fillOval(gx,gy,gw,gh);
    }
}
