package model;

import java.util.Timer;

public class Balle {
    
    protected double x;
    protected double x0;
    protected double y;
    protected double y0;
    protected double vitesseX;
    protected double vitesseX0;
    protected double vitesseY;
    protected double vitesseY0;
    protected double accelerationX;
    protected double accelerationY;
    protected double rayon;
    protected Timer clock;
    protected double masse;
    protected double g;
    protected double forceAdded;

    
    public Balle(double x0, double y0, double vitesseX0, double vitesseY0, Timer clock) {
      this.x0 = x0;
      this.y0 = y0;
      this.vitesseX0 = vitesseX0;
      this.vitesseY0 = vitesseY0;
    }
    
    public void update() {
      x += 0; //A REMPLIR
      y += 0; //A REMPLIR
    }
    public void addForce(double )
    
    public double getX() {
      return x;
    }
    
    public double getY() {
      return y;
    }
    
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
  }
