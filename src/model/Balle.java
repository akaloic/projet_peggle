package model;

public class Balle extends Corps{
    protected double vitesseX;
    protected double vitesseY;

    public Balle(double x, double y, double largeur, double hauteur, double vitesseX, double vitesseY){
        super(x, y, largeur, hauteur);
        this.vitesseX = vitesseX;
        this.vitesseY = vitesseY;
    }

    public void bouger(double temps) {
        x = x + vitesseX * temps;
        y = y + vitesseY * temps;
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
