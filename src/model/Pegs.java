package model;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import view.View;
import java.awt.*;
public class Pegs extends Obstacle{
    public BufferedImage image;
    private int rayon = 25;
    public Pegs(){
        super(1);
    }
    public Pegs(double x, double y, int v,BufferedImage img){
        super(x, y, 50,50, false, v);
        this.image = img;
    }
    public Pegs(double x, double y, int v,int rayon){
        super(x, y, rayon,rayon, false, v);
        this.rayon=rayon;
    }
    public double getRayon(){return this.rayon;}
    public double getDiametre(){return this.rayon*2;}
    public void dessine(Graphics g){
        /*int gx=(int)(this.x*View.ratioX);
        int gy=(int)(this.y*View.ratioY);
        int gw=(int)(this.rayon);
        int gh=(int)(this.rayon);
        g.fillOval(gx,gy,gw,gh);*/
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(image,(int)(this.x*View.ratioX), (int)(this.y*View.ratioY),(int)this.rayon,(int)this.rayon,null);
    }

    public Pegs clone(double x, double y, int v, double rayon){
        return new Pegs(x, y, v, (int)rayon);
    }




}

