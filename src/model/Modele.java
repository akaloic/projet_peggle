package model;

public class Modele {

    protected Balle balle;
    protected Obstacle[] obstacles;
    protected Canon canon;

    public Modele() {
        balle = new Balle(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        obstacles = new Obstacle[0];
    }

    // ---------GETTER SETTER---------
    public Balle getBalle() {
        return balle;
    }

    public void setBalle(Balle balle) {
        this.balle = balle;
    }

    public Obstacle[] getObstacles() {
        return obstacles;
    }

    public void setObstacles(Obstacle[] obstacles) {
        this.obstacles = obstacles;
    }

    public Canon getCanon() {
        return canon;
    }

    public void setCanon(Canon canon) {
        this.canon = canon;
    }
    // ---------GETTER SETTER---------
}
