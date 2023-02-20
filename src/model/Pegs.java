package model;

public class Pegs extends Obstacle{
    private double rayon=50;
    public Pegs(int v,double r){
        super(v);
    }
    public Pegs(double x, double y, int v){
        super(x, y, 50,50, false, v);
    }
    public Pegs(double x, double y, int v,int rayon){
        super(x, y, rayon,rayon, false, v);
        this.rayon=rayon;
    }
    public double getRayon(){return this.rayon;}
}
