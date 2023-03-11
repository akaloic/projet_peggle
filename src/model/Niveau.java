package model;

import java.util.ArrayList;

public class Niveau {

    public ArrayList<Pegs> list_peg = new ArrayList<Pegs>();
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
            case 3:
                niveau_3();
                niveau = 3;
                break;
            case 4:
                niveau_4();
                niveau = 4;
                break;
            case 5:
                niveau_5();
                niveau = 5;
                break;
        }
    }

    protected void diagonal(double x, double y, ArrayList<Pegs> list, int pegUsed, int distance, String direction) {
        double nvx = x;
        double nvy = y;
        for (int i = 0; i < pegUsed; i++) {
            Pegs p = new Pegs(nvx, nvy, 1, 25);
            list.add(p);
            nvx = direction.equals("gauche") ? nvx - distance : nvx + distance;
            nvy += distance;
        }
    }

    protected void carres(double x, double y, ArrayList<Pegs> list, int pegCote) {
        lignes(x, y, list, pegCote + 2, 50);
        colonne(x, y + 50, list, pegCote, 50);
        colonne(x + ((pegCote + 1) * 50), y + 50, list, pegCote, 50);
        lignes(x, y + ((pegCote + 1) * 50), list, pegCote + 2, 50);
    }

    protected void lignes(double x, double y, ArrayList<Pegs> list, int pegUsed, int distance) {
        double nvx = x;
        for (int i = 0; i < pegUsed; i++) {
            Pegs p = new Pegs(nvx, y, 1, 25);
            list.add(p);
            nvx += distance;
        }
    }

    protected void colonne(double x, double y, ArrayList<Pegs> list, int pegUsed, int distance) {
        double nvy = y;
        for (int i = 0; i < pegUsed; i++) {
            Pegs p = new Pegs(x, nvy, 1, 25);
            list.add(p);
            nvy += distance;
        }
    }
    protected void triangle(double x, double y, ArrayList<Pegs> list, int lignes, int distance){
        double nvx=x;
        double nvy=y;
        for(int i=0;i<=lignes;i++){
            lignes(nvx, nvy, list, i, distance);
            nvx-=(distance/2);
            nvy+=distance;
        }
    }
    protected void triangleVide(double x, double y, ArrayList<Pegs> list, int lignes, int distance){
        list.add(new Pegs(x, y, 1, 25));
        diagonal(x-(distance/2), y+distance, list, lignes-1, distance/2, "gauche");
        diagonal(x+(distance/2), y+distance, list, lignes-1, distance/2, "droite");
        //TODO : a finir, essayer une autre approche que 2 diagonals et une ligne en bas
    }

    // Peut-être simplifié ou réutilisé pour le cercle
    protected void losange(double x, double y, ArrayList<Pegs> list, int pegUsed, int distance) {
        list.add(new Pegs(x, y, 1, 25));
        double nvx=x+distance;
        double nvy=y+distance;
        double ny=y-distance;
        for (int i=0;i<pegUsed/2;i++){
            list.add(new Pegs(nvx,nvy,1,25));
            list.add(new Pegs(nvx,ny,1,25));
            nvx+=distance;
            nvy+=distance;
            ny-=distance;
        }
        nvy-=distance*2;
        ny+=distance*2;
        for (int i=0;i<pegUsed/2;i++){
            list.add(new Pegs(nvx,nvy,1,25));
            list.add(new Pegs(nvx,ny,1,25));
            nvx+=distance;
            nvy-=distance;
            ny+=distance;
        }
    }

    protected void carresRemplis(double x, double y, ArrayList<Pegs> list, int pegCote, int distance){
        double nvy=y;
        for (int i=0;i<pegCote;i++){
            lignes(x, nvy, list, pegCote, distance);
            nvy+=distance;
        }
    }

    protected void niveau_1() {
        lignes(125, 300, list_peg, 20, 50);
        lignes(100, 350, list_peg, 20, 50);
        lignes(125, 400, list_peg, 20, 50);
        lignes(100, 450, list_peg, 20, 50);
        lignes(125, 500, list_peg, 20, 50);
        lignes(100, 550, list_peg, 20, 50);
        lignes(125, 600, list_peg, 20, 50);
        lignes(100, 650, list_peg, 20, 50);
    }

    protected void niveau_2() {
        //carres du haut
        carres(50, 200, list_peg, 5);
        carres(400, 200, list_peg, 4);
        carres(700, 200, list_peg, 3);
        carres(950, 200, list_peg, 2);
        carres(1150, 200, list_peg, 1);
        //carres du bas
        carres(950, 500, list_peg, 5);
        carres(650, 550, list_peg, 4);
        carres(400, 600, list_peg, 3);
        carres(200, 650, list_peg, 2);
        carres(50, 700, list_peg, 1);
    }

    protected void niveau_3() {
        diagonal(100, 200, list_peg, 5, 50, "droite");
        diagonal(600, 200, list_peg, 6, 50, "gauche");
        diagonal(600, 200, list_peg, 6, 50, "droite");
        diagonal(1100, 200, list_peg, 5, 50, "gauche");

        diagonal(100, 400, list_peg, 5, 50, "droite");
        diagonal(600, 400, list_peg, 6, 50, "gauche");
        diagonal(600, 400, list_peg, 6, 50, "droite");
        diagonal(1100, 400, list_peg, 5, 50, "gauche");

        lignes(400, 700, list_peg, 9, 50);
        lignes(100, 200, list_peg, 20, 50);

    }
    protected void niveau_4(){
        triangle(625, 300, list_peg, 5, 50);
        triangleVide(625, 600, list_peg, 5, 50);
        triangle(225, 600, list_peg, 5, -50);
        triangle(925, 600, list_peg, 5, -50);
    }

    protected void niveau_5(){
        carresRemplis(550, 400, list_peg, 4,100);
        losange(100, 300, list_peg, 6,50);
        losange(1000, 300, list_peg, 6,50);
        losange(1000, 800, list_peg, 6,50);
        losange(100, 800, list_peg, 6,50);
    }

    public ArrayList<Pegs> getList() {
        return list_peg;
    }

    public int getNiveau() {
        return niveau;
    }
}
