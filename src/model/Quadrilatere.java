package model;
import java.awt.*;
import view.Image;

import view.View;
public class Quadrilatere extends Obstacle {//peut etre un carré comme un rectangle
    public Quadrilatere(double x, double y, double largeur, double hauteur) {
        super(x, y, largeur, hauteur, false, 100);
        rayon = (largeur + hauteur)/2;
        if(largeur>=hauteur){
            this.image=Image.quadHorizontal;
        }
        else{
            this.image=Image.quadVertical;
        }
    }

    @Override
    public boolean collision(Balle balle) {
        return false;
    }

    @Override
    public void rebond(Balle balle) {
        if (collision(balle)) {

        }
    }
    public void dessine(Graphics g){
        int gx=(int)(this.x*View.ratioX);
        int gy=(int)(this.y*View.ratioY);
        int gw=(int)(this.largeur*View.getRatio());
        int gh=(int)(this.hauteur*View.getRatio());
        if(image == null){
            g.fillRect(gx,gy,gw,gh);
        }
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(this.image,gx, gy,gw,gh,null);
    }

    public Quadrilatere clone(double x, double y, int v, double rayon){
        return new Quadrilatere(x, y, 20, rayon);
    }

}
