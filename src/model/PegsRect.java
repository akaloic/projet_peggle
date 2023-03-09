package model;
import java.awt.*;
public class PegsRect extends Pegs {

    public PegsRect(double x, double y, int v, double rayon) {
        super(x, y, v, rayon);
        //TODO Auto-generated constructor stub
    }

    public void dessine(Graphics2D g, double ratioX,double ratioY){
        g.drawRect((int) (x*ratioX),
                    (int) (y*ratioY),
                    (int) (rayon*ratioX),
                    (int) (rayon*ratioX));
    }

    public void dessine(Graphics g, int widht, int weight){
        g.fillRect(0, 0, widht, weight);
    }

    public PegsRect clone(double x, double y, int v, double rayon){
        return new PegsRect(x, y, v, rayon);
    }
    
}
