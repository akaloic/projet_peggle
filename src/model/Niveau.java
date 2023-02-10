package model;

import java.util.ArrayList;

public class Niveau {
    protected int nb_obstacles;
    protected int nb_cercles;
    protected int nb_carres;
    protected int nb_lignes;
    protected ArrayList<Pegs> list_peg;

    protected void cercle(double x, double y, ArrayList<Pegs> list, int pegUsed){

    }

    protected void carres(double x, double y, ArrayList<Pegs> list, int pegUsed){

    }

    protected void lignes(double x, double y, ArrayList<Pegs> list, int pegUsed){
        double nvx=x;
        for(int i=0; i<pegUsed;i++){
            Pegs p = new Pegs(nvx, y, 10, 10, 1, 5);
            list.set(i, p);
            nvx+=10;
        }
    }

    Niveau(int npegs, int nobstacles, int ncercles, int ncarres, int nlignes){
        for (int i=0;i<npegs;i++){
            list_peg.add(new Pegs(0, 0, 0, 0, 0, 0));
        }
        nb_obstacles=nobstacles;
        for (int i=0;i<ncercles;i++){
            //cercle(0,0,list_peg);
        }
        for (int i=0;i<ncarres;i++){
            //carres(0,0,list_peg);
        }
        for (int i=0;i<nlignes;i++){
            //lignes(0, 0, list_peg);
        }
    }
}
