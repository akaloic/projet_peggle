package model;

public class Corps {
    protected double x;
    protected double y;
    protected double largeur;
    protected double hauteur;
    
    public Corps (double x, double y, double largeur, double hauteur) {
      this.x = x;
      this.y = y;
      this.largeur = largeur;
      this.hauteur = hauteur;
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
}