package model;

import java.util.Timer;

public class Balle {
    
    protected double x;
    protected double x0;
    protected double y;
    protected double y0;
    protected double v0;
    protected final double rayon = 25;
    protected final double g = 150;

    
    public Balle(double x0, double y0, double v0) {
      this.x0 = x0;
      this.x=x0;
      this.y0 = y0;
      this.y=y0;
      this.v0=v0;
    }
    
    public void update(double angle, double t) {
      x =  Math.cos(Math.toRadians(angle))*v0*t+this.x0;
      y = (g*t*t)/2 +(v0)*(Math.sin(Math.toRadians(angle)))*t + this.y0;
    }
    
    public double getX() {
      return x;
    }
    
    public double getY() {
      return y;
    }

    public double getRayon(){
      return this.rayon;
    }
    
    public boolean collision(Pegs o,double ratioX, double ratioY){
      return Math.hypot(x-(o.getX()*ratioX), y-(o.getY())*ratioY) <= (rayon + 50);
    }

  }
