package model;

import java.util.ArrayList;

import view.Image;
import view.View;


public class Niveau {

    public ArrayList<Obstacle> list = new ArrayList<Obstacle>();
    public int niveau;

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
                niveau_4();//sardine
                niveau = 4;
                break;
            case 5:
                niveau_5();//triforce de zelda
                niveau = 5;
                break;
        }
    }

    protected void diagonal(double x, double y, int pegUsed, boolean direction, boolean sens) {
        double nvx = x;
        double nvy = y;
        for (int i = 0; i < pegUsed; i++) {
            Pegs p = new Pegs(nvx, nvy, 1,Image.pegRondRose);
            list.add(p);
            nvx = direction ? nvx - p.getDiametre() : nvx + p.getDiametre();
            nvy += p.getDiametre();
        }
    }

    protected void carres(double x, double y, int pegCote) {
        lignes(x, y, pegCote + 2);
        colonne(x, y + getDiametre(), pegCote);
        colonne(x + ((pegCote + 1) * getDiametre()), y + getDiametre(), pegCote);
        lignes(x, y + ((pegCote + 1) * getDiametre()), pegCote + 2);
    }

    protected void lignes(double x, double y, int pegUsed) {
        double nvx = x;
        for (int i = 0; i < pegUsed; i++) {
            Pegs p = new Pegs(nvx, y, 1,Image.pegRondRose);
            list.add(p);
            nvx += p.getDiametre();
        }
    }

    protected void colonne(double x, double y, int pegUsed) {
        double nvy = y;
        for (int i = 0; i < pegUsed; i++) {
            Pegs p = new Pegs(x, nvy, 1,Image.pegRondRose);
            list.add(p);
            nvy += p.getDiametre();
        }
    }
    protected void triangle(double x, double y, int lignes, Boolean alenvers){
        double nvx=x;
        double nvy=y;
        for(int i=0;i<=lignes;i++){
            lignes(nvx, nvy, i);
            nvx-=getRayon();
            nvy=alenvers==false?nvy+getDiametre():nvy-getDiametre();
        }
    }

    protected void carresRemplis(double x, double y, int pegCote){
        double nvy=y;
        for (int i=0;i<pegCote;i++){
            lignes(x, nvy, pegCote);
            nvy+=getDiametre();
        }
    }

    protected void niveau_1() {//liste de lignes
        double y1=200; double x1=getRayon()/2;
        for(int i=0;i<6;i++){
            if(i%2==0){
                lignes(x1+getRayon(), y1+(getDiametre()*i), 15);
            }
            else{
                Quadrilatere obr = new Quadrilatere(x1, y1+(getDiametre()*i),getRayon()*5,getRayon());
                Quadrilatere obr2 = new Quadrilatere(x1+getDiametre()*13, y1+(getDiametre()*i),getRayon()*5,getRayon());
                lignes(x1+getRayon()*6, y1+(getDiametre()*i), 10);
                list.add(obr);
                list.add(obr2);
            }
            Quadrilatere barriereGauche= new Quadrilatere(x1+getDiametre()*3, y1+getDiametre()*6+getRayon(),getDiametre()*3,getRayon());
            Quadrilatere barriereDroit= new Quadrilatere(x1+getDiametre()*9+getRayon(), y1+getDiametre()*6+getRayon(),getDiametre()*3,getRayon());
            list.add(barriereGauche);
            list.add(barriereDroit);
            
        }
    }

    protected void niveau_2(){
        double x2=(View.getRatioX()*800)*2;double y2=300;
        colonne(x2, y2, 6);
        //coté droit du dé
        diagonal(x2+getDiametre(), y2-getRayon(),4, true, true);
        colonne(x2+getDiametre()*4, y2-getDiametre(), 4);
        diagonal(x2+getDiametre(), y2+getRayon()*9, 4, true, true);
        lignes(x2+getDiametre(), y2+getRayon()*7, 1);
        lignes(x2+getDiametre()*3, y2-getRayon(), 1);
        //coté gauche du dé
        diagonal(x2-getDiametre(), y2-getRayon(), 4, false, true);
        colonne(x2-getDiametre()*4, y2-getDiametre(), 4);
        diagonal(x2-getDiametre(), y2+getRayon()*9, 4, false, true);
        lignes(x2-getDiametre()*2, y2+getRayon()*3, 1);
        //face du dé
        diagonal(x2+getDiametre(), y2-getRayon()*7, 3, true, false);
        diagonal(x2-getDiametre(), y2-getRayon()*7, 3, false, false);
        colonne(x2, y2-getDiametre()*4, 4);
    }
    protected void niveau_3(){//series de carrés
        double x3=getDiametre()+getRayon()/2; double y3=getDiametre()*3;
        int m1=0;int m2=0;int j=0;
        for(int i=3; i>-1;i--){
            carres(x3+getDiametre()*m1, y3, i);
            carres(x3+getDiametre()*m2, y3+getDiametre()*(6-j), j);
            m1+=i+2;m2+=j+2;j++;
        }
        Quadrilatere colonneGauche = new Quadrilatere(x3-getDiametre(), y3-getRayon(), getRayon(), getRayon()*15+1);
        Quadrilatere colonneDroit = new Quadrilatere(x3+getDiametre()*14+0.5, y3-getRayon(), getRayon(), getRayon()*15+1);
        Quadrilatere ligneGaucheHaut = new Quadrilatere(x3-getDiametre(), y3-getDiametre(), getDiametre()*6, getRayon());
        Quadrilatere ligneGaucheBas = new Quadrilatere(x3-getDiametre(), y3+getRayon()*16, getDiametre()*6, getRayon());
        Quadrilatere ligneDroitHaut = new Quadrilatere(x3+getDiametre()*8+getRayon(), y3-getDiametre(), getDiametre()*6, getRayon());
        Quadrilatere ligneDroitBas = new Quadrilatere(x3+getDiametre()*8+getRayon(), y3+getRayon()*16, getDiametre()*6, getRayon());
        list.add(colonneGauche);list.add(colonneDroit);
        list.add(ligneGaucheHaut);list.add(ligneGaucheBas);
        list.add(ligneDroitHaut);list.add(ligneDroitBas);
    }
    protected void niveau_4(){//sardine
        double x4=getRayon()/2; double y4=getDiametre()*5;
        //bouche et tete
        diagonal(x4, y4, 4, true, true);
        diagonal(x4+getDiametre(), y4+getRayon(), 2, true, false);
        diagonal(x4+getDiametre(), y4+getRayon()*3, 2, false, false);
        diagonal(x4+getDiametre(), y4+getRayon()*5, 3, true, false);
        //aile haute
        lignes(x4+getDiametre()*4, y4-getRayon()*3, 3);
        lignes(x4+getDiametre()*5, y4-getRayon()*5, 3);
        lignes(x4+getDiametre()*6, y4-getRayon()*7, 3);
        //aile basse
        lignes(x4+getDiametre()*4, y4+getRayon()*7, 3);
        lignes(x4+getDiametre()*5, y4+getRayon()*9, 3);
        lignes(x4+getDiametre()*6, y4+getRayon()*11, 3);
        //arriere
        diagonal(x4+getDiametre()*7, y4-getRayon()*3, 5, true, false);
        diagonal(x4+getDiametre()*7, y4+getRayon()*7, 5, true, true);
        //queue
        diagonal(x4+getDiametre()*12, y4, 3, true, true);
        diagonal(x4+getDiametre()*12, y4+getDiametre()*2, 3, true, false);
        colonne(x4+getDiametre()*15, y4+getDiametre()*2, 2);
        colonne(x4+getDiametre()*15, y4-getDiametre(), 2);
        colonne(x4+getDiametre()*14, y4+getDiametre(), 1);
        //visage
        lignes(x4+getRayon()*7, y4-getRayon(), 1);
        Quadrilatere ligne = new Quadrilatere(x4+getRayon()*9, y4-getRayon(), getRayon(), getRayon()*6);
        list.add(ligne);
    }
    protected void niveau_5(){//triforce de zelda
        double x5=(View.getRatioX()*800)*2; double y5=100;
        triangle(x5, y5, 4, false);
        for(int i=1;i<9;i++){
            Quadrilatere ligneGauche = new Quadrilatere(0, y5+getDiametre()*i, getDiametre()*8-(getRayon()*i), getRayon());
            Quadrilatere ligneDroite = new Quadrilatere(getDiametre()*8+getRayon()*i, y5+getDiametre()*i, getDiametre()*8-(getRayon()*i), getRayon());
            list.add(ligneGauche);
            list.add(ligneDroite);
        }
        triangle(x5-getDiametre()*2, y5+getDiametre()*4, 4, false);
        triangle(x5+getDiametre()*2, y5+getDiametre()*4, 4, false);
        for(int i=0;i<3;i++){
            Quadrilatere ligneMilieu = new Quadrilatere(x5-getRayon()*3.5+getRayon()*i, y5+getDiametre()*5+getDiametre()*i, getDiametre()*3-getDiametre()*i, getRayon());
            list.add(ligneMilieu);
        }
    }
    public ArrayList<Obstacle> getList() {return list;}
    public int getNiveau() {return niveau;}
    public double getRayon(){
        Pegs p = new Pegs();
        return p.getRayon();
    }

    public double getDiametre() {
        Pegs p = new Pegs();
        return p.getDiametre();
    }

    public void setList(ArrayList<Obstacle> charge) {
        this.list = charge;
    }

    public boolean detruit(int i) {
        if (list.get(i).vie - 1 == 0) {
            return true;
        } else {
            list.get(i).vie--;
            return false;
        }
    }

}
