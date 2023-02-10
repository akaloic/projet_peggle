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
        lignes(x+10, y, list, pegCote, 5);
        colonne(x, y+10, list, pegCote, 5);
    }

    protected void lignes(double x, double y, ArrayList<Pegs> list, int pegUsed, int distance){
        double nvx=x;
        for(int i=0; i<pegUsed;i++){
            Pegs p = new Pegs(nvx, y, 1, 5);
            list.add(p);
            nvx+=distance;
        }
    }

    protected void colonne(double x, double y, ArrayList<Pegs> list, int pegUsed, int distance){
        double nvy=y;
        for(int i=0; i<pegUsed;i++){
            Pegs p = new Pegs(x, nvy, 1, 5);
            list.add(p);
            nvy+=distance;
        }
    }

    protected void niveau_1(){
        lignes(30, 20, list_peg, 10, 5);
        lignes(20, 40, list_peg, 10, 5);
        lignes(30, 60, list_peg, 10, 5);
    }

    public Niveau(int i){
        switch (i){
            case 1 : niveau_1();
        }
    }
}
