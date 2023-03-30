package model;
import java.awt.*;
import java.awt.image.BufferedImage;
import view.View;
public class Pegs extends Obstacle{
    private int rayon = 25;
    public Pegs(){
        super(1);
    }
    public Pegs(double x, double y, int v,BufferedImage img){
        super(x*View.ratioX, y*View.ratioY, 25,25, false, v);
        this.image = img;
    }
    public Pegs(double x, double y, int v){
        super(x*View.ratioX, y*View.ratioY, 25,25, false, v);
    }
    public Pegs(double x, double y, int v,int rayon){
        super(x*View.ratioX, y*View.ratioY, rayon,rayon, false, v);
        this.rayon=rayon;
    }

    @Override
    public boolean collision(Balle balle) {
        return ((this.getRayon() + balle.rayon) / 2) >= Math.sqrt((balle.x - this.getX()) * (balle.x - this.getX()) + (balle.y - this.getY()) * (balle.y - this.getY()));
    }

    @Override
    public void rebond(Balle balle) {
        if (collision(balle)) {
          double n = balle.vX; // Variable auxiliaire pour garder vX avant qu'on modifie sa valeur
          balle.vX = balle.vX - (2 * (balle.vX * (balle.x - this.getX()) + balle.vY * ((balle.y - this.getY())))
              / ((balle.x - this.getX()) * (balle.x - this.getX()) + (balle.y - this.getY()) * (balle.y - this.getY())))
              * (balle.x - this.getX());
          balle.vY = balle.vY - (2 * (n * (balle.x - this.getX()) + balle.vY * ((balle.y - this.getY())))
              / ((balle.x - this.getX()) * (balle.x - this.getX()) + (balle.y - this.getY()) * (balle.y - this.getY())))
              * (balle.y - this.getY());
        }
    }


    public double getRayon(){return this.rayon;}
    public double getDiametre(){return this.rayon*2;}
    public void dessine(Graphics g){
        int gx=(int)(this.x);
        int gy=(int)(this.y);
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
