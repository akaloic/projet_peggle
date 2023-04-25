package model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import view.Image;
public class Obstacle extends Objet{

    protected boolean estMort; 
    protected int vie;
    protected double rayon;
    public BufferedImage image;

    public Obstacle(double x, double y, double largeur, double hauteur, boolean estMort, int vie) {
        super(x, y, largeur, hauteur, 0);
        this.estMort = estMort;
        this.vie = vie;
    }

    public void perdDeLaVie(int degats) {
        this.vie -= degats;
        switch(this.vie){
            default: this.estMort=true;break;
            case 1: this.image=Image.pegRondRouge;break;
            case 2: this.image=Image.pegRondRose;break;
            case 3: this.image=Image.pegRondBleu;break;
        }
    }
    public boolean collision(Balle balle) {
        return false;
    }

    public boolean rebond(Balle balle) {return false;}

    // ---------GETTER GETTER---------
    public boolean isDead() {
        return this.vie <= 0;
    }
    public double getRayon(){
        return this.rayon;
    }
    public void setRayon(double i){
        this.rayon = i;
    }

    public Obstacle clone(double x, double y, int v, double largeur,double hauteur){
        return new Obstacle(0);
    }

    public Obstacle(int v) {
        super();
        this.vie = v;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }

    public int getVie() {
        return this.vie;
    }

    public boolean getEstMort() {
        return this.estMort;
    }
    public void setHP(int hp){//Pour set le total de vie
        this.vie = hp;
    }

    public void setEstMort(boolean mort) {
        this.estMort = mort;
    }

    public void dessine(Graphics g) {
    }

    public boolean utiliseRayon(){
        return false;
    }
    // ---------GETTER SETTER---------

}
