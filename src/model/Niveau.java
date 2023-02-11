package model;

import java.util.ArrayList;

public class Niveau {
    public ArrayList<Pegs> list_peg = new ArrayList<Pegs>();

    public ArrayList<Pegs> getList(){
        return list_peg;
    }

    protected void cercle(double x, double y, ArrayList<Pegs> list, int pegUsed){

    }
    protected void diagonal(double x, double y, ArrayList<Pegs> list, int pegUsed, int distance, String direction){
        double nvx=x;
        double nvy=y;
        for(int i=0; i<pegUsed;i++){
            Pegs p = new Pegs(nvx, nvy, 1, 25);
            list.add(p);
            nvx=direction.equals("gauche")?nvx-distance:nvx+distance;
            nvy+=distance;
        }
    }

    protected void carres(double x, double y, ArrayList<Pegs> list, int pegCote){
        lignes(x, y, list, pegCote+2, 50);
        colonne(x, y+50, list, pegCote, 50);
        colonne(x+((pegCote+1)*50), y+50,list, pegCote,50);
        lignes(x,y+((pegCote+1)*50),list,pegCote+2,50);
    }

    protected void lignes(double x, double y, ArrayList<Pegs> list, int pegUsed, int distance){
        double nvx=x;
        for(int i=0; i<pegUsed;i++){
            Pegs p = new Pegs(nvx, y, 1, 25);
            list.add(p);
            nvx+=distance;
        }
    }

    protected void colonne(double x, double y, ArrayList<Pegs> list, int pegUsed, int distance){
        double nvy=y;
        for(int i=0; i<pegUsed;i++){
            Pegs p = new Pegs(x, nvy, 1, 25);
            list.add(p);
            nvy+=distance;
        }
    }

    protected void niveau_1(){
        lignes(125, 300, list_peg, 20, 50);
        lignes(100, 350, list_peg, 20, 50);
        lignes(125, 400, list_peg, 20, 50);
        lignes(100, 450, list_peg, 20, 50);
        lignes(125, 500, list_peg, 20, 50);
        lignes(100, 550, list_peg, 20, 50);
        lignes(125, 600, list_peg, 20, 50);
        lignes(100, 650, list_peg, 20, 50);
    }
    protected void niveau_2(){
        carres(125, 300, list_peg, 4);
        carres(600, 200, list_peg, 3);
        carres(600, 600, list_peg, 1);
        carres(125, 700, list_peg, 0);
    }
    protected void niveau_3(){
        diagonal(100, 200, list_peg, 5, 50,"droite");
        diagonal(600, 200, list_peg, 6, 50,"gauche");
        diagonal(600, 200, list_peg, 6, 50,"droite");
        diagonal(1100, 200, list_peg, 5, 50,"gauche");

        diagonal(100, 400, list_peg, 5, 50,"droite");
        diagonal(600, 400, list_peg, 6, 50,"gauche");
        diagonal(600, 400, list_peg, 6, 50,"droite");
        diagonal(1100, 400, list_peg, 5, 50,"gauche");

        lignes(400, 700, list_peg, 9, 50);
        lignes(100, 200, list_peg, 20, 50);

    }

    public Niveau(int i){
        switch (i){
            case 1 : niveau_1();break;
            case 2 : niveau_2();break;
            case 3 : niveau_3();break;
        }
    }
}
