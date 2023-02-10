package model;

public class Pegs extends Obstacle{
    double rayon;
    public Pegs(int v,double r){
        super(v);
        this.rayon=r;
    }
    public Pegs(double x, double y, double largeur, double hauteur, int v, double rayon){
        super(x, y, largeur, hauteur, rayon, false, v);
        this.rayon=rayon;
    }
}
