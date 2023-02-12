package model;

public class Modele {

    private Balle balle;
    private Obstacle[] obstacles;
    private Niveau niveau;

    public Modele() {
        balle = new Balle(5d,5d,0d);
        obstacles = new Obstacle[0];
        niveau = new Niveau(5);
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

    public void setBalle(Balle balle) {
        this.balle = balle;
    }

    public Obstacle[] getObstacles() {
        return obstacles;
    }

    public void setObstacles(Obstacle[] obstacles) {
        this.obstacles = obstacles;
    }
    // ---------GETTER SETTER---------
}
