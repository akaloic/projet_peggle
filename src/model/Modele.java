package model;

public class Modele {

    public Balle balle;
    public Obstacle[] obstacles;
    public Niveau niveau;
    public Player player;

    public Modele() {
        balle = null;
        obstacles = new Obstacle[0];
        niveau = new Niveau(1);
        player = new Player(10, "Paul");
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

    public Player getPlayer(){
        return player;
    }

    public void setPlayer(Player p){
        player=p;
    }

    // ---------GETTER SETTER---------

}
