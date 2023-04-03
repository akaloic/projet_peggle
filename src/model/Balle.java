package model;

public class Balle {

  public double x;
  public double vY;
  public double y;
  public double vX;
  public double v0;
  public final double rayon = 50;
  public final double g = 400;

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

  public void rebondMur(){
    this.vX = this.vX *-1;
  }

}
