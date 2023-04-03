package model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

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
        if (isDead()) {
            this.estMort = false;
        }
    }
    public boolean collision(Balle balle) {
        return false;
    }

    public void rebond(Balle balle) {}

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

    public Obstacle clone(double x, double y, int v, double rayon){
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

    public int getVie() {
        return this.vie;
    }

    public boolean getEstMort() {
        return this.estMort;
    }

    public double getWidth() {
        return super.getLargeur();
    }

    public double getHeight() {
        return super.getHauteur();
    }

    public void setX(double n){
        this.x = n;
    }

    public void setY(double n){
        this.y = n;
    }

    public void setVie(int pdv) {
        this.vie += pdv;
    }

    public void setHP(int hp){//Pour set le total de vie
        this.vie = hp;
    }

    public void setEstMort(boolean mort) {
        this.estMort = mort;
    }

    public void dessine(Graphics g) {
    }
    // ---------GETTER SETTER---------

}