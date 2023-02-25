package model;
import java.awt.*;

public class Pegs extends Obstacle{
    public double rayon;
    public Pegs(int v,double r){
        super(v);
        this.rayon=r;
    }
    public Pegs(double x, double y, int v, double rayon){
        super(x, y, rayon,rayon, false, v);
        this.rayon=rayon;
    }

    public double getRayon(){
        return this.rayon;
    }

    public void dessine(Graphics2D g, double ratioX,double ratioY){
        g.fillOval((int) (x*ratioX),
                    (int) (y*ratioY),
                    (int) (rayon*ratioX),
                    (int) (rayon*ratioX));
    }
}
