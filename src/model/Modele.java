package model;

import model.sousObjet.Canon;
import model.sousObjet.Balle;
import model.sousObjet.Obstacle;

public class Modele {

    protected Balle balle;
    protected Obstacle[] obstacles;
    protected Canon canon;

    public Modele() {
        balle = new Balle(100, 100, 50, 50, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        obstacles = new Obstacle[0];
    }

    // ---------GETTER SETTER---------
    public Balle getBalle() {
        return balle;
    }

    
    public Obstacle[] getObstacles() {
        return obstacles;
    }
    
    public Canon getCanon() {
        return canon;
    }
    
    // ---------GETTER SETTER---------
    
    public void setBalle(Balle balle) {
        this.balle = balle;
    }
    public void setCanon(Canon canon) {
        this.canon = canon;
    }
    public void setObstacles(Obstacle[] obstacles) {
        this.obstacles = obstacles;
    }
}
