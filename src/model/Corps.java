package model;

public class Corps {
    protected double x;
    protected double y;
    protected double vitesseX;
    protected double vitesseY;
    
    public Corps (double x, double y, double vitesseX, double vitesseY) {
      this.x = x;
      this.y = y;
      this.vitesseX = vitesseX;
      this.vitesseY = vitesseY;
    }
    
    public void bouger(double temps) {
      x = x + vitesseX * temps;
      y = y + vitesseY * temps;
    }
}