package model;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import view.View;
public class Pegs extends Obstacle{
    private int rayon = 25;
    public Pegs(){
        super(1);
    }
    public Pegs(double x, double y, int v,BufferedImage img){
        super(x, y, 25,25, false, v);
        Random r = new Random();
        super.vie=r.nextInt(3)+1;
        this.image = img;
    }
    public Pegs(double x, double y, int v){
        super(x, y, 25,25, false, v);
    }
    public Pegs(double x, double y, int v,int rayon){
        super(x, y, rayon,rayon, false, v);
        this.rayon=rayon;
    }

    @Override
    public boolean collision(Balle balle) {
        return ((this.getRayon() + balle.rayon) / 2) >= Math.sqrt((balle.x - this.getX()*View.ratioX) * (balle.x - this.getX()*View.ratioX) + (balle.y - this.getY()*View.ratioY) * (balle.y - this.getY()*View.ratioY));
    }

    @Override
    public void rebond(Balle balle) {
        if (collision(balle)) {
          double n = balle.vX; // Variable auxiliaire pour garder vX avant qu'on modifie sa valeur
          balle.vX = balle.vX - (2 * (balle.vX * (balle.x - this.getX()*View.ratioX) + balle.vY * ((balle.y - this.getY()*View.ratioY)))
              / ((balle.x - this.getX()*View.ratioX) * (balle.x - this.getX()*View.ratioX) + (balle.y - this.getY()*View.ratioY) * (balle.y - this.getY()*View.ratioY)))
              * (balle.x - this.getX()*View.ratioX);
          balle.vY = balle.vY - (2 * (n * (balle.x - this.getX()*View.ratioX) + balle.vY * ((balle.y - this.getY()*View.ratioY)))
              / ((balle.x - this.getX()*View.ratioX) * (balle.x - this.getX()*View.ratioX) + (balle.y - this.getY()*View.ratioY) * (balle.y - this.getY()*View.ratioY)))
              * (balle.y - this.getY()*View.ratioY);
        }
    }


    public double getRayon(){return this.rayon;}
    public double getDiametre(){return this.rayon*2;}
    public void dessine(Graphics g){
        int gx=(int)(this.x*View.ratioX);
        int gy=(int)(this.y*View.ratioY);
        int gw=(int)(this.rayon*View.getRatio());
        int gh=(int)(this.rayon*View.getRatio());
        if(image == null){
            g.fillOval(gx,gy,gw,gh);
        }
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(this.image,gx, gy,gw,gh,null);
    }

    public Pegs clone(double x, double y, int v, double rayon){
        return new Pegs(x, y, v, (int)rayon);
    }
    public void setRayon(double i){
        this.rayon = (int)i;
    }
}
