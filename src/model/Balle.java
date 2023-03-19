package model;

import view.View;
public class Balle {
    
    protected double x;
    protected double vY;
    protected double y;
    protected double vX;
    protected double v0;
    protected final double rayon = 50;
    protected final double g = 400;

    
    public Balle(double x0, double y0, double v0, double angle) {
      this.x=x0;
      this.y=y0;
      this.vX=Math.cos(Math.toRadians(angle))*v0;
      this.vY=Math.sin(Math.toRadians(angle))*v0;
    }
    
    public void update() {
      x =  x + 0.03*vX;
      y = y + 0.03*vY;
      this.vX = vX;
      this.vY = vY + 0.03*g;
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
  
    public boolean collision(Pegs o){
      return (o.getRayon() + this.rayon) >= Math.sqrt((this.x-o.getX())* (this.x-o.getX()) + (this.y - o.getY())*(this.y - o.getY()));
    }

    public void rebond(Pegs o){
      if(collision(o)){
        this.vX = this.vX - (2*(this.vX*(this.x-o.getX()) + this.vY*((this.y - o.getY())))/((this.x-o.getX())*(this.x-o.getX())+(this.y - o.getY())*(this.y - o.getY())))*(this.x-o.getX());
        this.vY = this.vY - (2*(this.vX*(this.x-o.getX()) + this.vY*((this.y - o.getY())))/((this.x-o.getX())*(this.x-o.getX())+(this.y - o.getY())*(this.y - o.getY())))*(this.y-o.getY());
      }
    }
  }
