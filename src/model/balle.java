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
    protected final double rayon = 1;
    protected Timer clock;
    protected final double masse = 1;
    protected final double g = 9.81;
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
    public void addForce(){

    }
    
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
    public boolean collision(Obstacle o){
      return o.getRayon() + this.rayon == (this.x-o.getX())*(this.x-o.getX()) + (this.y - o.getY())*(this.y - o.getY());
    }
  }
