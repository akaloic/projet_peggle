package model;

import java.util.ArrayList;

import model.sousObstacle.ObstacleRectangulaire;
import view.View;

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
            case 3:
                niveau_3();
                niveau = 3;
                break;
        }
    }

    protected void diagonal(double x, double y, ArrayList<Obstacle> list, int pegUsed, String direction) {
        double nvx = x;
        double nvy = y;
        for (int i = 0; i < pegUsed; i++) {
            Pegs p = new Pegs(nvx, nvy, 1);
            list.add(p);
            nvx = direction.equals("gauche") ? nvx - p.getDiametre() : nvx + p.getDiametre();
            nvy += p.getDiametre();
        }
    }

    protected void carres(double x, double y, ArrayList<Obstacle> list, int pegCote) {
        lignes(x, y, list, pegCote + 2);
        colonne(x, y + getDiametre(), list, pegCote);
        colonne(x + ((pegCote + 1) * getDiametre()), y + getDiametre(), list, pegCote);
        lignes(x, y + ((pegCote + 1) * getDiametre()), list, pegCote + 2);
    }

    protected void lignes(double x, double y, ArrayList<Obstacle> list, int pegUsed) {
        double nvx = x;
        for (int i = 0; i < pegUsed; i++) {
            Pegs p = new Pegs(nvx, y, 1);
            list.add(p);
            nvx += p.getDiametre();
        }
    }

    protected void colonne(double x, double y, ArrayList<Obstacle> list, int pegUsed) {
        double nvy = y;
        for (int i = 0; i < pegUsed; i++) {
            Pegs p = new Pegs(x, nvy, 1);
            list.add(p);
            nvy += p.getDiametre();
        }
    }
    protected void triangle(double x, double y, ArrayList<Obstacle> list, int lignes, Boolean alenvers){
        double nvx=x;
        double nvy=y;
        for(int i=0;i<=lignes;i++){
            lignes(nvx, nvy, list, i);
            nvx-=getRayon();
            nvy=alenvers==false?nvy+getDiametre():nvy-getDiametre();
        }
    }
    /*protected void triangleVide(double x, double y, ArrayList<Obstacle> list, int lignes){
        list.add(new Pegs(x, y, 1));
        diagonal(x-(p.getDiametre()/2), y+p.getDiametre(), list, lignes-1, p.getDiametre()/2, "gauche");
        diagonal(x+(p.getDiametre()/2), y+p.getDiametre(), list, lignes-1, p.getDiametre()/2, "droite");
        //TODO : a finir, essayer une autre approche que 2 diagonals et une ligne en bas
    }*/

    // Peut-être simplifié ou réutilisé pour le cercle
    /*protected void losange(double x, double y, ArrayList<Obstacle> list, int pegUsed) {
        list.add(new Pegs(x, y, 1));
        double nvx=x+p.getDiametre();
        double nvy=y+p.getDiametre();
        double ny=y-p.getDiametre();
        for (int i=0;i<pegUsed/2;i++){
            list.add(new Pegs(nvx,nvy,1));
            list.add(new Pegs(nvx,ny,1));
            nvx+=p.getDiametre();
            nvy+=p.getDiametre();
            ny-=p.getDiametre();
        }
        nvy-=p.getDiametre()*2;
        ny+=p.getDiametre()*2;
        for (int i=0;i<pegUsed/2;i++){
            list.add(new Pegs(nvx,nvy,1));
            list.add(new Pegs(nvx,ny,1));
            nvx+=p.getDiametre();
            nvy-=p.getDiametre();
            ny+=p.getDiametre();
        }
    }*/

    protected void carresRemplis(double x, double y, ArrayList<Obstacle> list, int pegCote){
        double nvy=y;
        for (int i=0;i<pegCote;i++){
            lignes(x, nvy, list, pegCote);
            nvy+=getDiametre();
        }
    }

    protected void niveau_1() {
        for(int i=0;i<6;i++){
            if(i%2==0){
                lignes(25, 400+(getDiametre()*i), list_peg, 20);
            }
            else{
                ObstacleRectangulaire obr = new ObstacleRectangulaire(25, 400+(getDiametre()*i));
                ObstacleRectangulaire obr2 = new ObstacleRectangulaire(150+(50*17*2), 400+(getDiametre()*i));
                lignes(175, 400+(getDiametre()*i), list_peg, 17);
                list_peg.add(obr);
                list_peg.add(obr2);
            }
            ObstacleRectangulaire barriereGauche= new ObstacleRectangulaire(getDiametre()*3 - 25, 1025,400,50);
            ObstacleRectangulaire barriereDroit= new ObstacleRectangulaire(25 + getDiametre()*17 - getDiametre()*4, 1025 ,400,50);
            list_peg.add(barriereGauche);
            list_peg.add(barriereDroit);
            
        }
        
    }

    protected void niveau_2(){
        /*diagonal(25, 400, list_peg, 5, "droite");
        colonne(25+getRayon()*10, 400, list_peg, 5);
        carres(25+getRayon()*12, 400, list_peg, 5);*/
        triangle((View.getPartie().getWidth()+getRayon()*(View.getRatioX()*10))/2, 800, list_peg, 5,true);
        carresRemplis(50, 400, list_peg, 3);
    }
    protected void niveau_3(){
        int m1=0;int m2=0;int j=0;
        for(int i=3; i>-1;i--){
            carres(325+getDiametre()*m1, 300, list_peg, i);
            carres(325+getDiametre()*m2, 300+getDiametre()*(6-j), list_peg, j);
            m1+=i+2;m2+=j+2;j++;
        }
        ObstacleRectangulaire colonneGauche = new ObstacleRectangulaire(325-getDiametre(), 300-getRayon(), getRayon(), getRayon()*17+1);
        ObstacleRectangulaire colonneDroit = new ObstacleRectangulaire(325+getDiametre()*14, 300-getRayon(), getRayon(), getRayon()*17+1);
        ObstacleRectangulaire ligneGaucheHaut = new ObstacleRectangulaire(325-getDiametre(), 300-getDiametre(), getDiametre()*6, getRayon());
        ObstacleRectangulaire ligneGaucheBas = new ObstacleRectangulaire(325-getDiametre(), 300+getRayon()*16, getDiametre()*6, getRayon());
        ObstacleRectangulaire ligneDroitHaut = new ObstacleRectangulaire(325+getDiametre()*8+getRayon(), 300-getDiametre(), getDiametre()*6, getRayon());
        ObstacleRectangulaire ligneDroitBas = new ObstacleRectangulaire(325+getDiametre()*8+getRayon(), 300+getRayon()*16, getDiametre()*6, getRayon());
        list_peg.add(colonneGauche);
        list_peg.add(colonneDroit);
        list_peg.add(ligneGaucheHaut);
        list_peg.add(ligneGaucheBas);
        list_peg.add(ligneDroitHaut);
        list_peg.add(ligneDroitBas);
    }

    public ArrayList<Obstacle> getList() {
        return list_peg;
    }

    public int getNiveau() {
        return niveau;
    }
    public double getRayon(){
        Pegs p = new Pegs();
        return p.getRayon();
    }
    public double getDiametre(){
        Pegs p = new Pegs();
        return p.getDiametre();
    }
}
