package model;

import java.util.ArrayList;

import model.sousObstacle.ObstacleRectangulaire;

public class Niveau {

    public ArrayList<Obstacle> list_peg = new ArrayList<Obstacle>();
    protected int niveau;

    public Niveau(int i) {
        switch (i) {
            case 1:
                niveau_1();
                niveau = 1;
                break;
            case 2:
                niveau_2();
                niveau = 2;
                break;
        }
    }

    protected void diagonal(double x, double y, ArrayList<Obstacle> list, int pegUsed, String direction) {
        double nvx = x;
        double nvy = y;
        for (int i = 0; i < pegUsed; i++) {
            Pegs p = new Pegs(nvx, nvy, 1);
            list.add(p);
            nvx = direction.equals("gauche") ? nvx - p.getRayon()*2 : nvx + p.getRayon()*2;
            nvy += p.getRayon()*2;
        }
    }

    protected void carres(double x, double y, ArrayList<Obstacle> list, int pegCote) {
        lignes(x, y, list, pegCote + 2);
        colonne(x, y + getRayon()*2, list, pegCote);
        colonne(x + ((pegCote + 1) * getRayon()*2), y + getRayon()*2, list, pegCote);
        lignes(x, y + ((pegCote + 1) * getRayon()*2), list, pegCote + 2);
    }

    protected void lignes(double x, double y, ArrayList<Obstacle> list, int pegUsed) {
        double nvx = x;
        for (int i = 0; i < pegUsed; i++) {
            Pegs p = new Pegs(nvx, y, 1);
            list.add(p);
            nvx += p.getRayon()*2;
        }
    }

    protected void colonne(double x, double y, ArrayList<Obstacle> list, int pegUsed) {
        double nvy = y;
        for (int i = 0; i < pegUsed; i++) {
            Pegs p = new Pegs(x, nvy, 1);
            list.add(p);
            nvy += p.getRayon()*2;
        }
    }
    protected void triangle(double x, double y, ArrayList<Obstacle> list, int lignes){
        double nvx=x;
        double nvy=y;
        for(int i=0;i<=lignes;i++){
            lignes(nvx, nvy, list, i);
            nvx-=25;
            nvy+=50;
        }
    }
    /*protected void triangleVide(double x, double y, ArrayList<Obstacle> list, int lignes){
        list.add(new Pegs(x, y, 1));
        diagonal(x-(p.getRayon()*2/2), y+p.getRayon()*2, list, lignes-1, p.getRayon()*2/2, "gauche");
        diagonal(x+(p.getRayon()*2/2), y+p.getRayon()*2, list, lignes-1, p.getRayon()*2/2, "droite");
        //TODO : a finir, essayer une autre approche que 2 diagonals et une ligne en bas
    }*/

    // Peut-être simplifié ou réutilisé pour le cercle
    /*protected void losange(double x, double y, ArrayList<Obstacle> list, int pegUsed) {
        list.add(new Pegs(x, y, 1));
        double nvx=x+p.getRayon()*2;
        double nvy=y+p.getRayon()*2;
        double ny=y-p.getRayon()*2;
        for (int i=0;i<pegUsed/2;i++){
            list.add(new Pegs(nvx,nvy,1));
            list.add(new Pegs(nvx,ny,1));
            nvx+=p.getRayon()*2;
            nvy+=p.getRayon()*2;
            ny-=p.getRayon()*2;
        }
        nvy-=p.getRayon()*2*2;
        ny+=p.getRayon()*2*2;
        for (int i=0;i<pegUsed/2;i++){
            list.add(new Pegs(nvx,nvy,1));
            list.add(new Pegs(nvx,ny,1));
            nvx+=p.getRayon()*2;
            nvy-=p.getRayon()*2;
            ny+=p.getRayon()*2;
        }
    }*/

    protected void carresRemplis(double x, double y, ArrayList<Obstacle> list, int pegCote){
        double nvy=y;
        for (int i=0;i<pegCote;i++){
            lignes(x, nvy, list, pegCote);
            nvy+=getRayon();
        }
    }

    protected void niveau_1() {
        for(int i=0;i<6;i++){
            int pegUsed= i%2==0?20:19;
            double l= Math.pow(-1, i);
            lignes(50-25*l, 400+(getRayon()*2*i), list_peg, pegUsed);
        }
        ObstacleRectangulaire obr = new ObstacleRectangulaire(25, 1100);
        list_peg.add(obr);
    }

    protected void niveau_2(){
        diagonal(25, 400, list_peg, 5, "droite");
        colonne(25+50*10, 400, list_peg, 5);
        carres(25+50*12, 400, list_peg, 5);
    }

    public ArrayList<Obstacle> getList() {
        return list_peg;
    }

    public int getNiveau() {
        return niveau;
    }
    public double getRayon(){
        Pegs p = new Pegs(niveau, niveau);
        return p.getRayon();
    }
}
