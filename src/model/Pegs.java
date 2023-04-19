package model;
import java.awt.*;
import java.util.Random;

import view.View;
import view.Image;
public class Pegs extends Obstacle{
    private int rayon = 25;
    public Pegs(){
        super(1);
    }
    public Pegs(double x, double y, int v){
        super(x, y, 25,25, false, v);
        Random r = new Random();
        super.vie=r.nextInt(3)+1;
        switch(super.vie){
            default:
                    this.image=Image.pegRondRouge; break;
            case 2:
                    this.image=Image.pegRondRose; break;
            case 3:
                    this.image=Image.pegRondBleu; break;
        }
    }
    public Pegs(double x, double y, int v,double largeur, double hauteur){
        super(x, y, largeur,hauteur, false, v);
        this.rayon=(int)((hauteur+largeur)/2);
    }

    @Override
    public boolean collision(Balle balle) {
        return ((this.getRayon() + balle.rayon) / 2) >= Math.sqrt((balle.x - this.getX()) * (balle.x - this.getX()) + (balle.y - this.getY()) * (balle.y - this.getY()));
    }

    @Override
    public void rebond(Balle balle) {
        if (collision(balle)) {
            //System.out.println(this.x+"      "+balle.x+"       "+this.y+"      "+balle.y);
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
    @Override
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
    @Override
    public Pegs clone(double x, double y, int v, double largeur,double hauteur){
        Pegs p = new Pegs(x, y, v, largeur,hauteur);
        p.rayon=(int)((hauteur+largeur)/2);
        return p;
    }
    public void setRayon(double i){
        this.rayon = (int)i;
    }

    @Override
    public boolean utiliseRayon(){
        return true;
    }
}
