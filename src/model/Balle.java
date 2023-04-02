package model;

import view.View;

public class Balle {

  public double x;
  public double vY;
  public double y;
  public double vX;
  public double v0;
  public final double diametre = 50;
  public final double g = 800;

  public Balle(double x0, double y0, double v0, double angle) {
    this.x = x0;
    this.y = y0;
    this.vX = Math.cos(Math.toRadians(angle)) * v0;
    this.vY = Math.sin(Math.toRadians(angle)) * v0;
  }

  public void update() {
    x = x + 0.03 * vX;
    y = y + 0.03 * vY;
    this.vY = vY + 0.03 * g;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public int collision(Obstacle o) {
    if(o instanceof Pegs && ((o.getRayon() + this.diametre) / 2) >= Math.sqrt((this.x - o.getX()*View.ratioX) * (this.x - o.getX()*View.ratioX) + (this.y - o.getY()*View.ratioY) * (this.y - o.getY()*View.ratioY)))return 1;
    if(o instanceof Quadrilatere){
      if((this.y>=o.y*View.ratioY && this.y <=(o.y+o.hauteur)*View.ratioY) && (this.x+this.diametre/2>=o.x*View.ratioX || this.x-this.diametre/2<=(o.x+o.largeur)*View.ratioX)){
        System.out.println("Collision quadrilatere 2");
        return 2;
      }
      if((this.x>=o.x*View.ratioX && this.x <=(o.x+o.largeur)*View.ratioX) && (this.y+this.diametre/2>=o.y*View.ratioY || this.y-this.diametre/2<=(o.y+o.hauteur)*View.ratioY)){
        System.out.println("Collision quadrilatere 3");
        return 3;
      }
    }
    return 0;
  
  
  }

  public void rebond(Obstacle o) {
    int collision = collision(o);
    switch(collision){
      case 1 : double n = this.vX; // Variable auxiliaire pour garder vX avant qu'on modifie sa valeur
          this.vX = this.vX - (2 * (this.vX * (this.x - o.getX()*View.ratioX) + this.vY * ((this.y - o.getY()*View.ratioY)))
          / ((this.x - o.getX()*View.ratioX) * (this.x - o.getX()*View.ratioX) + (this.y - o.getY()*View.ratioY) * (this.y - o.getY()*View.ratioY)))
          * (this.x - o.getX()*View.ratioX);
          this.vY = this.vY - (2 * (n * (this.x - o.getX()*View.ratioX) + this.vY * ((this.y - o.getY()*View.ratioY)))
          / ((this.x - o.getX()*View.ratioX) * (this.x - o.getX()*View.ratioX) + (this.y - o.getY()*View.ratioY) * (this.y - o.getY()*View.ratioY)))
          * (this.y - o.getY()*View.ratioY); 
          break;
      case 2 : this.vX=this.vX*-1; break;
      case 3 : this.vY=this.vY*-1; break; 

    }
  }
  public void rebondMur(){
    this.vX = this.vX *-1;
  }

}
