package model;
import java.awt.*;

import view.View;
import java.awt.*;
public class Pegs extends Obstacle{
    private double rayon=50;
    public Pegs(){
        super(1);
    }
    public Pegs(double x, double y, int v){
        super(x, y, 50,50, false, v);
    }
    public Pegs(double x, double y, int v,int rayon){
        super(x, y, rayon,rayon, false, v);
        this.rayon=rayon;
    }
    public double getRayon(){return this.rayon;}
    public double getDiametre(){return this.rayon*2;}
    public void dessine(Graphics g){
        int gx=(int)(this.x*View.getRatioX());
        int gy=(int)(this.y*View.getRatioY());
        int gw=(int)(this.rayon*View.getRatioX());
        int gh=(int)(this.rayon*View.getRatioY());
        g.fillOval(gx,gy,gw,gh);
    }

    public Pegs clone(double x, double y, int v, double rayon){
        return new Pegs(x, y, v, (int)rayon);
    }


}

