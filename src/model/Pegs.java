package model;
import java.awt.*;
import java.awt.image.BufferedImage;

import view.View;
public class Pegs extends Obstacle{
    public BufferedImage image;
    private static int rayon = 25;
    public Pegs(){
        super(1);
    }
    public Pegs(double x, double y, int v,BufferedImage img){
        super(x, y, rayon,rayon, false, v);
        this.image = img;
    }
    public Pegs(double x, double y, int v){
        super(x, y, rayon,rayon, false, v);
    }
    public Pegs(double x, double y, int v,int r){
        super(x, y, rayon,rayon, false, v);
        rayon=r;
    }
    public double getRayon(){return rayon;}
    public double getDiametre(){return rayon*2;}
    public void dessine(Graphics g){
        int gx=(int)(this.x*View.ratioX);
        int gy=(int)(this.y*View.ratioY);
        int gw=(int)(rayon*View.getRatio());
        int gh=(int)(rayon*View.getRatio());
        g.fillOval(gx,gy,gw,gh);
        //Graphics2D g2d = (Graphics2D)g;
        //g2d.drawImage(Niveau.image,gx, gy,gw,gh,null);
    }

    public Pegs clone(double x, double y, int v, double rayon){
        return new Pegs(x, y, v, (int)rayon);
    }
    public void setRayon(double i){
        rayon = (int)i;
    }




}

