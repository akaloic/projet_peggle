package model;

import view.View;

public class Balle {

  public double x;
  public double vY;
  public double y;
  public double vX;
  public double v0;
  public final double diametre = 50;
  public final double rayon = 25;
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
    vY = vY + 0.03 * g;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public int collision(Obstacle o) {
    if (o instanceof Pegs
        && ((o.getRayon() + diametre) / 2) >= Math.sqrt((x - o.getX() * View.ratioX) * (x - o.getX() * View.ratioX)
            + (y - o.getY() * View.ratioY) * (y - o.getY() * View.ratioY)))
      return 1;
    if (o instanceof Quadrilatere) {
      if ((y >= o.y * View.ratioY && y <= (o.y + o.hauteur) * View.ratioY)
          && (x + rayon >= o.x * View.ratioX && x - rayon <= (o.x + o.largeur) * View.ratioX)) {
        System.out.println("Collision quadrilatere 2");
        return 2;
      }
      if ((x >= o.x * View.ratioX && x <= (o.x + o.largeur) * View.ratioX)
          && (y + rayon >= o.y * View.ratioY && y - rayon <= (o.y + o.hauteur) * View.ratioY)) {
        System.out.println("Collision quadrilatere 3");
        return 3;
      }
    }
    return 0;

  }

  public void rebond(Obstacle o) {
    int collision = collision(o);
    switch (collision) {
      case 1:
        double n = vX; // Variable auxiliaire pour garder vX avant qu'on modifie sa valeur
        vX = vX - (2 * (vX * (x - o.getX() * View.ratioX) + vY * ((y - o.getY() * View.ratioY)))
            / ((x - o.getX() * View.ratioX) * (x - o.getX() * View.ratioX)
                + (y - o.getY() * View.ratioY) * (y - o.getY() * View.ratioY)))
            * (x - o.getX() * View.ratioX);
        vY = vY - (2 * (n * (x - o.getX() * View.ratioX) + vY * ((y - o.getY() * View.ratioY)))
            / ((x - o.getX() * View.ratioX) * (x - o.getX() * View.ratioX)
                + (y - o.getY() * View.ratioY) * (y - o.getY() * View.ratioY)))
            * (y - o.getY() * View.ratioY);
        break;
      case 2:
        vX = vX * -1;
        break;
      case 3:
        vY = vY * -1;
        break;
    }
  }

  public void rebondMur() {
    vX = vX * -1;
  }

}
