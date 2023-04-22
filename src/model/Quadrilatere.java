package model;

import java.awt.*;
import view.Image;

import view.View;

public class Quadrilatere extends Obstacle { // peut etre un carré comme un rectangle
    public Point coinHautGauche, coinBasGauche, coinHautDroit, coinBasDroit;

    public Quadrilatere(double x, double y, double largeur, double hauteur) {
        super(x, y, largeur, hauteur, false, 100);
        rayon = (largeur + hauteur) / 2;
        coinHautGauche = new Point((int) x, (int) y);
        coinHautDroit = new Point((int) (x + largeur), (int) y);
        coinBasGauche = new Point((int) x, (int) (y + hauteur));
        coinBasDroit = new Point((int) (x + largeur), (int) (y + hauteur));
        if(largeur>=hauteur){
            this.image=Image.quadHorizontal;
        }
        else{
            this.image=Image.quadVertical;
        }
    }

    @Override
    public void dessine(Graphics g){
        int gx=(int)(this.x*View.ratioX);
        int gy=(int)(this.y*View.ratioY);
        int gw=(int)(this.largeur*View.ratioX);
        int gh=(int)(this.hauteur*View.ratioY);
        if(image == null){
            g.fillRect(gx,gy,gw,gh);
        }
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(this.image,gx, gy,gw,gh,null);
    }

    @Override
    public Quadrilatere clone(double x, double y, int v, double largeur,double hauteur){
        Quadrilatere q = new Quadrilatere(x, y, largeur, hauteur);
        q.image = null;
        return q;
    }

    @Override
    public boolean utiliseRayon(){
        return false;
    }

}
