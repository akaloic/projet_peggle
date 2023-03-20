package model;

public class Modele {

    public Balle balle;
    public Obstacle[] obstacles;
    public Niveau niveau;

    public Modele() {
        balle = null;
        obstacles = new Obstacle[0];
        niveau = new Niveau(4);
    }

    // ---------GETTER SETTER---------
    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public Balle getBalle() {
        return balle;
    }

    public void setBalle(Balle b) {
        this.balle = b;
    }

    public Obstacle[] getObstacles() {
        return obstacles;
    }

    public void setObstacles(Obstacle[] obstacles) {
        this.obstacles = obstacles;
    }
    // ---------GETTER SETTER---------

}
