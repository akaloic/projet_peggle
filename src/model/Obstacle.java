package model;

//pouvant potentiellement être utilisé pour des sous-classes à l'aide de l'heritage
public class Obstacle {
    private double x;
    private double y;
    private double largeur;
    private double hauteur;
    private double vitesseX;
    private double vitesseY;
    
    public Obstacle(double x, double y, double largeur, double hauteur, double vitesseX, double vitesseY) {
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