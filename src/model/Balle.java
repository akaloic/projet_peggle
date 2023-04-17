package model;

import view.View;

public class Balle {

  public double x;
  public double vY;
  public double y;
  public double vX;
  public double v0;
  public final double diametre = 16;
  public final double rayon = diametre/2;
  public final double g = 800;

  public Balle(double x0, double y0, double v0, double angle) {
    this.x = x0;
    this.y = y0;
    this.vX = Math.cos(Math.toRadians(angle)) * v0;
    this.vY = Math.sin(Math.toRadians(angle)) * v0;
  }

  public void update() {
    x = x + 0.01 * vX;
    y = y + 0.01 * vY;
    vY = vY + 0.01 * g;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public int collision(Obstacle o) {
    boolean est_dans_rectangle = (this.x)*View.ratioX>(o.getX())*View.ratioX && (this.x)*View.ratioX<(o.getX()+o.largeur)*View.ratioX && (this.y)*View.ratioX>(o.getY())*View.ratioY && (this.y)*View.ratioY<(o.getY()+o.hauteur)*View.ratioY;
    if (o instanceof Pegs
        && ((o.getRayon()/2 + rayon)*View.ratioY ) >= Math.sqrt(((x - o.getX()) * View.ratioX) * ((x - o.getX()) * View.ratioX)
            + ((y - o.getY()) * View.ratioY) * ((y - o.getY()) * View.ratioY))) // rebond cercle
      return 1;
    if (o instanceof Quadrilatere && !est_dans_rectangle) { 
      if ((y*View.ratioY >= o.y * View.ratioY && y*View.ratioY <= (o.y + o.hauteur) * View.ratioY)
          && (((x + rayon)*View.ratioX >= o.x * View.ratioX && x*View.ratioX<= o.x*View.ratioX) || ((x - rayon)*View.ratioX <= (o.x + o.largeur) * View.ratioX && x*View.ratioX>=(o.x + o.largeur) * View.ratioX ))) {
        System.out.println("Collision quadrilatere 2");
        return 2;
      } 
      if ((x*View.ratioX>= o.x * View.ratioX && x*View.ratioX <= (o.x + o.largeur) * View.ratioX)
          && (((y + rayon)*View.ratioY >= o.y * View.ratioY && y*View.ratioY <= o.y * View.ratioY) || ((y - rayon)*View.ratioY <= (o.y + o.hauteur) * View.ratioY && y*View.ratioY >= (o.y + o.hauteur) * View.ratioY))) {
        System.out.println("Collision quadrilatere 3");
        return 3;
      }
      if(rayon>= Math.sqrt((x-((Quadrilatere)o).coinHautGauche.x)*View.ratioX*(x-((Quadrilatere)o).coinHautGauche.x)*View.ratioX + (y-((Quadrilatere)o).coinHautGauche.y)*View.ratioY*(x-((Quadrilatere)o).coinHautGauche.y)*View.ratioY)){
        //collision coin haut gauche
        return 4;
      }
      if(rayon>= Math.sqrt((x-((Quadrilatere)o).coinHautDroit.x)*View.ratioX*(x-((Quadrilatere)o).coinHautDroit.x)*View.ratioX + (y-((Quadrilatere)o).coinHautDroit.y)*View.ratioY*(x-((Quadrilatere)o).coinHautDroit.y)*View.ratioY)){
        //collision coin haut droit
        return 5;
      }
      if(rayon>= Math.sqrt((x-((Quadrilatere)o).coinBasGauche.x)*View.ratioX*(x-((Quadrilatere)o).coinBasGauche.x)*View.ratioX + (y-((Quadrilatere)o).coinBasGauche.y)*View.ratioY*(x-((Quadrilatere)o).coinBasGauche.y)*View.ratioY)){
        //collision coin bas gauche
        return 6;
      }
      if(rayon>= Math.sqrt((x-((Quadrilatere)o).coinBasDroit.x)*View.ratioX*(x-((Quadrilatere)o).coinBasDroit.x)*View.ratioX + (y-((Quadrilatere)o).coinBasDroit.y)*View.ratioY*(x-((Quadrilatere)o).coinBasDroit.y)*View.ratioY)){
        //collision coin bas droit
        return 7;
      }
      

    }
    return 0;
  }

  public void rebond(Obstacle o) {
    int collision = collision(o);
    switch (collision) {
      case 1:
        double n = vX; // Variable auxiliaire pour garder vX avant qu'on modifie sa valeur
        vX = vX - (2 * (vX * (x - o.getX()) * View.ratioX + vY * (y - o.getY()) * View.ratioY))
            / (((x - o.getX()) * View.ratioX) * ((x - o.getX()) * View.ratioX)+ ((y - o.getY()) * View.ratioY) * ((y - o.getY()) * View.ratioY))
            * ((x - o.getX()) * View.ratioX) * 0.95;
        vY = vY - (2 * (n * (x - o.getX()) * View.ratioX + vY * (y - o.getY()) * View.ratioY))
            / (((x - o.getX()) * View.ratioX) * ((x - o.getX()) * View.ratioX) + ((y - o.getY()) * View.ratioY) * ((y - o.getY()) * View.ratioY))
            * ((y - o.getY()) * View.ratioY) * 0.95;
        break;
      case 2:
        vX = vX * -0.95;
        break;
      case 3:
        vY = vY * -0.95;
        break;
      case 4:
        double n1 = vX; // Variable auxiliaire pour garder vX avant qu'on modifie sa valeur
        vX = vX - (2 * (vX * (x - ((Quadrilatere)o).coinHautGauche.x) * View.ratioX + vY * (y - ((Quadrilatere)o).coinHautGauche.y) * View.ratioY))
            / (((x - ((Quadrilatere)o).coinHautGauche.x) * View.ratioX) * ((x - ((Quadrilatere)o).coinHautGauche.x) * View.ratioX)+ ((y - ((Quadrilatere)o).coinHautGauche.y) * View.ratioY) * ((y - ((Quadrilatere)o).coinHautGauche.y) * View.ratioY))
            * ((x - ((Quadrilatere)o).coinHautGauche.x) * View.ratioX) * 0.95;
        vY = vY - (2 * (n1 * (x - ((Quadrilatere)o).coinHautGauche.x) * View.ratioX + vY * (y - ((Quadrilatere)o).coinHautGauche.y) * View.ratioY))
            / (((x - ((Quadrilatere)o).coinHautGauche.x) * View.ratioX) * ((x - ((Quadrilatere)o).coinHautGauche.x) * View.ratioX) + ((y - ((Quadrilatere)o).coinHautGauche.y) * View.ratioY) * ((y - ((Quadrilatere)o).coinHautGauche.y) * View.ratioY))
            * ((y - ((Quadrilatere)o).coinHautGauche.y) * View.ratioY) * 0.95;
        break;
      case 5:
        double n2 = vX; // Variable auxiliaire pour garder vX avant qu'on modifie sa valeur
        vX = vX - (2 * (vX * (x - ((Quadrilatere)o).coinHautDroit.x) * View.ratioX + vY * (y - ((Quadrilatere)o).coinHautDroit.y) * View.ratioY))
            / (((x - ((Quadrilatere)o).coinHautDroit.x) * View.ratioX) * ((x - ((Quadrilatere)o).coinHautDroit.x) * View.ratioX)+ ((y - ((Quadrilatere)o).coinHautDroit.y) * View.ratioY) * ((y - ((Quadrilatere)o).coinHautDroit.y) * View.ratioY))
            * ((x - ((Quadrilatere)o).coinHautDroit.x) * View.ratioX) * 0.95;
        vY = vY - (2 * (n2 * (x - ((Quadrilatere)o).coinHautDroit.x) * View.ratioX + vY * (y - ((Quadrilatere)o).coinHautDroit.y) * View.ratioY))
            / (((x - ((Quadrilatere)o).coinHautDroit.x) * View.ratioX) * ((x - ((Quadrilatere)o).coinHautDroit.x) * View.ratioX) + ((y - ((Quadrilatere)o).coinHautDroit.y) * View.ratioY) * ((y - ((Quadrilatere)o).coinHautDroit.y) * View.ratioY))
            * ((y - ((Quadrilatere)o).coinHautDroit.y) * View.ratioY) * 0.95;
        break;
      case 6:
        double n3 = vX; // Variable auxiliaire pour garder vX avant qu'on modifie sa valeur
        vX = vX - (2 * (vX * (x - ((Quadrilatere)o).coinBasGauche.x) * View.ratioX + vY * (y - ((Quadrilatere)o).coinBasGauche.y) * View.ratioY))
            / (((x - ((Quadrilatere)o).coinBasGauche.x) * View.ratioX) * ((x - ((Quadrilatere)o).coinBasGauche.x) * View.ratioX)+ ((y - ((Quadrilatere)o).coinBasGauche.y) * View.ratioY) * ((y - ((Quadrilatere)o).coinBasGauche.y) * View.ratioY))
            * ((x - ((Quadrilatere)o).coinBasGauche.x) * View.ratioX) * 0.95;
        vY = vY - (2 * (n3 * (x - ((Quadrilatere)o).coinBasGauche.x) * View.ratioX + vY * (y - ((Quadrilatere)o).coinBasGauche.y) * View.ratioY))
            / (((x - ((Quadrilatere)o).coinBasGauche.x) * View.ratioX) * ((x - ((Quadrilatere)o).coinBasGauche.x) * View.ratioX) + ((y - ((Quadrilatere)o).coinBasGauche.y) * View.ratioY) * ((y - ((Quadrilatere)o).coinBasGauche.y) * View.ratioY))
            * ((y - ((Quadrilatere)o).coinBasGauche.y) * View.ratioY) * 0.95;
        break;
      case 7:
        double n4 = vX; // Variable auxiliaire pour garder vX avant qu'on modifie sa valeur
          vX = vX - (2 * (vX * (x - ((Quadrilatere)o).coinBasDroit.x) * View.ratioX + vY * (y - ((Quadrilatere)o).coinBasDroit.y) * View.ratioY))
              / (((x - ((Quadrilatere)o).coinBasDroit.x) * View.ratioX) * ((x - ((Quadrilatere)o).coinBasDroit.x) * View.ratioX)+ ((y - ((Quadrilatere)o).coinBasDroit.y) * View.ratioY) * ((y - ((Quadrilatere)o).coinBasDroit.y) * View.ratioY))
              * ((x - ((Quadrilatere)o).coinBasDroit.x) * View.ratioX) * 0.95;
          vY = vY - (2 * (n4 * (x - ((Quadrilatere)o).coinBasDroit.x) * View.ratioX + vY * (y - ((Quadrilatere)o).coinBasDroit.y) * View.ratioY))
              / (((x - ((Quadrilatere)o).coinBasDroit.x) * View.ratioX) * ((x - ((Quadrilatere)o).coinBasDroit.x) * View.ratioX) + ((y - ((Quadrilatere)o).coinBasDroit.y) * View.ratioY) * ((y - ((Quadrilatere)o).coinBasDroit.y) * View.ratioY))
              * ((y - ((Quadrilatere)o).coinBasDroit.y) * View.ratioY) * 0.95;
        break;
    }
  }

  public void rebondMur() {
    vX = vX * -0.95;
  }

}
