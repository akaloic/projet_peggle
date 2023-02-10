package model;

public class Pegs extends Obstacle{
    public double rayon;
    public Pegs(int v,double r){
        super(v);
        this.rayon=r;
    }
    public Pegs(double x, double y, int v, double rayon){
        super(x, y, rayon,rayon, rayon, false, v);
        this.rayon=rayon;
    }
}
