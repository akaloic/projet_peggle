package model;

import java.awt.Graphics;

public class Obstacle extends Objet {

    public boolean estMort;
    public int vie;

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

    // ---------GETTER GETTER---------
    public boolean isDead() {
        return this.vie <= 0;
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

    public void setVie(int pdv) {
        this.vie += pdv;
    }

    public void setEstMort(boolean mort) {
        this.estMort = mort;
    }

    public void dessine(Graphics g) {
    }
    // ---------GETTER SETTER---------
}