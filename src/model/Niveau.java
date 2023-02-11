package model;

import java.util.ArrayList;

public class Niveau {
    public ArrayList<Pegs> list_peg = new ArrayList<Pegs>();

    public ArrayList<Pegs> getList(){
        return list_peg;
    }

    protected void cercle(double x, double y, ArrayList<Pegs> list, int pegUsed){

    }

    protected void carres(double x, double y, ArrayList<Pegs> list, int pegCote){
        lignes(x+50, y, list, pegCote, 50);
        colonne(x, y+50, list, pegCote, 50);
        colonne(x+250, y+50,list, pegCote,50);
        lignes(x+50,y+250,list,pegCote,50);
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
        carres(600, 200, list_peg, 5);
    }

    public Niveau(int i){
        switch (i){
            case 1 : niveau_1();break;
            case 2 : niveau_2();break;
        }
    }
}
