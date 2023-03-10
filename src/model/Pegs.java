package model;
import java.awt.*;

public class Pegs extends Obstacle{
    public double rayon;
    public boolean couleur = false;
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
        if(couleur){
            g.setColor(Color.blue);
        }
        else{
            g.setColor(Color.red);
        }   
        g.fillOval((int) (x*ratioX),
                    (int) (y*ratioY),
                    (int) (rayon*ratioX),
                    (int) (rayon*ratioX));       
    }

    public void dessine(Graphics g, int widht, int weight){
        g.fillOval(0, 0, widht, weight);
    }

    public Pegs clone(double x, double y, int v, double rayon){
        return new Pegs(x, y, v, rayon);
    }


}

