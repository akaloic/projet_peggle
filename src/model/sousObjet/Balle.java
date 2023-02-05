package model.sousObjet;

import java.util.Timer;

import model.Objet;

public class Balle extends Objet {

  protected double vitesseX;
  protected double vitesseY;

  protected double x0;
  protected double y0;
  protected double vitesseX0;
  protected double vitesseY0;

  protected double accelerationX;
  protected double accelerationY;

  protected final double rayon;
  protected final double masse;
  protected final double g = 9.81; // L'accélération de la pesanteur standard

  protected double forceAdded;
  protected Timer clock;

  protected double degatsCommis = 50; //temporaire 
  
  public Balle(double x, double y, double largeur, double hauteur, double angle, double vitesseX, double vitesseY,
      double x0, double y0, double vitesseX0, double vitesseY0, double accelerationX, double accelerationY,
      double masse, double forceAdded) {

    super(x, y, largeur, hauteur, angle);

    this.vitesseX = vitesseX;
    this.vitesseY = vitesseY;
    this.x0 = x0;
    this.y0 = y0;
    this.vitesseX0 = vitesseX0;
    this.vitesseY0 = vitesseY0;
    this.accelerationX = accelerationX;
    this.accelerationY = accelerationY;
    this.rayon = largeur / 2;
    this.masse = masse;
    this.forceAdded = forceAdded;
  }
  public double getWidth(){
    return super.getLargeur();
  }
  public double getHeight(){
    return super.getHauteur();
  }
  // ---------GETTER SETTER---------
  public double getVitesseX() {
    return vitesseX;
  }

  public double getVitesseY() {
    return vitesseY;
  }

  public void setVitesseX(double vitesseX) {
    this.vitesseX = vitesseX;
  }

  public void setVitesseY(double vitesseY) {
    this.vitesseY = vitesseY;
  }

  public double getAccelerationX() {
    return accelerationX;
  }

  public void setAccelerationX(double accelerationX) {
    this.accelerationX = accelerationX;
  }

  public double getAccelerationY() {
    return accelerationY;
  }

  public void setAccelerationY(double accelerationY) {
    this.accelerationY = accelerationY;
  }

  public double getRayon() {
    return rayon;
  }

  public double getMasse() {
    return masse;
  }

  public double getG() {
    return g;
  }

  public double getForceAdded() {
    return forceAdded;
  }

  public void setForceAdded(double forceAdded) {
    this.forceAdded = forceAdded;
  }
  // ---------GETTER SETTER---------
  
}
