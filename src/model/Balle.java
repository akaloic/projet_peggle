package model;

import view.View;

public class Balle {

  protected double x;
  protected double vY;
  protected double y;
  protected double vX;
  protected double v0;
  protected final double rayon = 50;
  protected final double g = 400;

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

  public double getRayon() {
    return this.rayon;
  }

  public boolean collision(Obstacle o) {
    return ((o.getRayon() + this.rayon) / 2) >= Math.sqrt((this.x - o.getX()*View.ratioX) * (this.x - o.getX()*View.ratioX) + (this.y - o.getY()*View.ratioY) * (this.y - o.getY()*View.ratioY));
  }

  public void rebond(Obstacle o) {
    if (collision(o)) {
      double n = this.vX; // Variable auxiliaire pour garder vX avant qu'on modifie sa valeur
      this.vX = this.vX - (2 * (this.vX * (this.x - o.getX()*View.ratioX) + this.vY * ((this.y - o.getY()*View.ratioY)))
          / ((this.x - o.getX()*View.ratioX) * (this.x - o.getX()*View.ratioX) + (this.y - o.getY()*View.ratioY) * (this.y - o.getY()*View.ratioY)))
          * (this.x - o.getX()*View.ratioX);
      this.vY = this.vY - (2 * (n * (this.x - o.getX()*View.ratioX) + this.vY * ((this.y - o.getY()*View.ratioY)))
          / ((this.x - o.getX()*View.ratioX) * (this.x - o.getX()*View.ratioX) + (this.y - o.getY()*View.ratioY) * (this.y - o.getY()*View.ratioY)))
          * (this.y - o.getY()*View.ratioY);
    }
  }
}
