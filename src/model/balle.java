package model;

public class balle {
    private double x;
    private double y;
    private double vitesseX;
    private double vitesseY;
    
    public balle(double x, double y, double vitesseX, double vitesseY) {
      this.x = x;
      this.y = y;
      this.vitesseX = vitesseX;
      this.vitesseY = vitesseY;
    }
    
    public void bouger(double temps) {
      x = x + vitesseX * temps;
      y = y + vitesseY * temps;
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
  }
