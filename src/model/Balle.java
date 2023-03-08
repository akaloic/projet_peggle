package model;

import java.util.Timer;

public class Balle {

  public double x;
  public double x0;
  public double y;
  public double y0;
  public double v0;
  public final double rayon = 25;
  public final double g = 150;

  public Balle(double x0, double y0, double v0) {
    this.x0 = x0;
    this.x = x0;
    this.y0 = y0;
    this.y = y0;
    this.v0 = v0;
  }

  public void update(double angle, double t) {
    x = Math.cos(Math.toRadians(angle)) * v0 * t + this.x0;
    y = (g * t * t) / 2 + (v0) * (Math.sin(Math.toRadians(angle))) * t + this.y0;
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

  public boolean collision(Pegs o) {
    return (o.getRayon() + this.rayon) * (o.getRayon() + this.rayon) == (this.x - o.getX()) * (this.x - o.getX())
        + (this.y - o.getY()) * (this.y - o.getY());
  }

}
