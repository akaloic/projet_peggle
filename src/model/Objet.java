package model;

public abstract class Objet {

  protected double x;
  protected double y;
  protected double largeur;
  protected double hauteur;
  protected double angle;

  public Objet(double x, double y, double largeur, double hauteur, double angle) {
    this.x = x;
    this.y = y;
    this.largeur = largeur;
    this.hauteur = hauteur;
    this.angle = angle;
  }

  // ---------GETTER SETTER---------
  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public double getLargeur() {
    return largeur;
  }

  public double getHauteur() {
    return hauteur;
  }

  public double getAngle() {
    return angle;
  }

  public void setAngle(double angle) {
    this.angle = angle;
  }

  // ---------GETTER SETTER---------
}