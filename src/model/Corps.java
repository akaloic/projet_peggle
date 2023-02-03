package model;

public class Corps {
    protected double x;
    protected double y;
    protected double largeur;
    protected double hauteur;
    protected double vitesseX;
    protected double vitesseY;
    
    public Corps (double x, double y, double largeur, double hauteur, double vitesseX, double vitesseY) {
      this.x = x;
      this.y = y;
      this.largeur = largeur;
      this.hauteur = hauteur;
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
    
    public double getLargeur() {
      return largeur;
    }
    
    public double getHauteur() {
      return hauteur;
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