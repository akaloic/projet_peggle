package model;
import java.awt.*;
import view.View;
public class Quadrilatere extends Obstacle {//peut etre un carré comme un rectangle
    public Quadrilatere(double x, double y, double largeur, double hauteur) {
        super(x, y, largeur, hauteur, false, 100);
    }
    public void dessine(Graphics g){
        int gx=(int)(this.x*View.getRatioX());
        int gy=(int)(this.y*View.getRatioY());
        int gw=(int)(this.largeur*View.getRatio());
        int gh=(int)(this.hauteur*View.getRatio());
        g.fillRect(gx,gy,gw,gh);
    }
    public Quadrilatere clone(double x, double y, double largeur, double hauteur){
        return new Quadrilatere(x, y, largeur, hauteur);
    }
}
