package model;
import java.awt.*;
public class PegsRect extends Pegs {

    public PegsRect(double x, double y, int v, double rayon) {
        super(x, y, v, (int)rayon);
        //TODO Auto-generated constructor stub
    }


    public void dessine(Graphics g){
        g.fillRect(0, 0, (int)this.getRayon(), (int)this.getRayon());
    }

    public PegsRect clone(double x, double y, int v, double rayon){
        return new PegsRect(x, y, v, rayon);
    }


    
}
