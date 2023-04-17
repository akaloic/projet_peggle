package model;
import java.awt.*;
import view.Image;

import view.View;
public class Quadrilatere extends Obstacle {//peut etre un carré comme un rectangle
    int typeCollision = 0;
    public Point coinHautGauche, coinBasGauche, coinHautDroit, coinBasDroit;
    public Quadrilatere(double x, double y, double largeur, double hauteur) {
        super(x, y, largeur, hauteur, false, 100);
        /*coinHautGauche = new Point((int) x, (int) y);
        coinHautDroit = new Point((int) ((x + largeur)), (int) y);
        coinBasGauche = new Point((int) x, (int) (y + hauteur));
        coinBasDroit = new Point((int) (x + largeur), (int) (y + hauteur));*/

        if(largeur>=hauteur){
            this.image=Image.quadHorizontal;
        }
        else{
            this.image=Image.quadVertical;
        }
    }

    @Override
    public boolean collision(Balle balle) {
        //System.out.println(this.x*View.ratioX+"    "+this.largeur*View.ratioX+"    "+this.coinHautDroit.x*View.ratioX);
        coinHautGauche = new Point((int) (this.x*View.ratioX), (int) (this.y*View.ratioY));
        coinHautDroit = new Point((int) ((this.x+this.largeur)*View.ratioX), (int) (this.y*View.ratioY));
        coinBasGauche = new Point((int) (this.x*View.ratioX), (int) ((this.y + this.hauteur)*View.ratioY));
        coinBasDroit = new Point((int) ((this.x + this.largeur)*View.ratioX), (int) ((this.y + this.hauteur)*View.ratioY));

    if ((balle.y >= this.y * View.ratioY && balle.y <= (this.y + this.hauteur) * View.ratioY)
        && (((balle.x + balle.rayon/2) >= this.x * View.ratioX && balle.x<= this.x*View.ratioX) || ((balle.x - balle.rayon/2)<= (this.x + this.largeur) * View.ratioX && balle.x>=(this.x + this.largeur) * View.ratioX ))) {
      System.out.println("Collision quadrilatere 1");
      typeCollision = 1;
      return true;
    } 
    else if ((balle.x>= this.x * View.ratioX && balle.x<= (this.x + this.largeur) * View.ratioX)
        && (((balle.y + balle.rayon/2) >= this.y * View.ratioY && balle.y <= this.y * View.ratioY) || ((balle.y - balle.rayon/2)<= (this.y + this.hauteur) * View.ratioY && balle.y >= (this.y + this.hauteur) * View.ratioY))) {
      System.out.println("Collision quadrilatere 2");
      typeCollision = 2;
      return true;
    }

    else if(balle.rayon/2>= Math.sqrt((balle.x+balle.rayon/4-this.coinHautGauche.x)*(balle.x+balle.rayon/4-this.coinHautGauche.x) + (balle.y+balle.rayon/4 - this.coinHautGauche.y)*(balle.y+balle.rayon/4 - this.coinHautGauche.y))){
      //collision coin haut gauche
      System.out.println("Collision quadrilatere 3");
      typeCollision = 3;
      return true;
    }
    else if(balle.rayon/2>= Math.sqrt((balle.x+balle.rayon/4 - this.coinHautDroit.x)*(balle.x+balle.rayon/4 - this.coinHautDroit.x) + (balle.y+balle.rayon/4 - this.coinHautDroit.y)*(balle.y+balle.rayon/4 - this.coinHautDroit.y))){
      //collision coin haut droit
      System.out.println("Collision quadrilatere 4");
      typeCollision = 4;
      return true;
    }
    else if(balle.rayon/2>= Math.sqrt((balle.x+balle.rayon/4 - this.coinBasGauche.x)*(balle.x+balle.rayon/4 - this.coinBasGauche.x) + (balle.y+balle.rayon/4 - this.coinBasGauche.y)*(balle.y+balle.rayon/4 - this.coinBasGauche.y))){
      //collision coin bas gauche
      System.out.println("Collision quadrilatere 5");
      typeCollision = 5;
      return true;
    }
    else if(balle.rayon/2>= Math.sqrt((balle.x+balle.rayon/4 - this.coinBasDroit.x)*(balle.x+balle.rayon/4-this.coinBasDroit.x) + (balle.y+balle.rayon/4 - this.coinBasDroit.y)*(balle.y+balle.rayon/4-this.coinBasDroit.y))){
      //collision coin bas droit
      System.out.println("Collision quadrilatere 6");
      typeCollision = 6;
      return true;
    }
    return false;

    }

    @Override
    public void rebond(Balle balle) {
        if (collision(balle)) {
            switch (this.typeCollision) {
                case 1:
                  balle.vX = balle.vX * -0.95;
                  break;
                case 2:
                  balle.vY = balle.vY * -0.95;
                  break;
                case 3:
                    coin(this.coinHautGauche,balle);
                  break;
                case 4:
                    coin(this.coinHautDroit,balle);
                  break;
                case 5:
                    coin(this.coinBasGauche,balle);
                  break;
                case 6:
                    coin(this.coinBasDroit,balle);
                  break;
              } 
        }
    }

    public void coin(Point point,Balle balle){
        double n1 = balle.vX; // Variable auxiliaire pour garder balle.vX avant qu'on modifie sa valeur
        balle.vX = balle.vX - (2 * (balle.vX * (balle.x - point.x) + balle.vY * (balle.y - point.y)))
            / (((balle.x - point.x)) * ((balle.x - point.x))+ ((balle.y - point.y)) * ((balle.y - point.y)))
            * ((balle.x - point.x)) * 0.95;
        balle.vY = balle.vY - (2 * (n1 * (balle.x - point.x) + balle.vY * (balle.y - point.y)))
            / (((balle.x - point.x)) * ((balle.x - point.x)) + ((balle.y - point.y)) * ((balle.y - point.y)))
            * ((balle.y - point.y)) * 0.95;
    }

    public void perdDeLaVie(int degats) {
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
