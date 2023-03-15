package model;

import java.util.ArrayList;

import model.sousObstacle.ObstacleRebondissant;
import model.sousObstacle.ObstacleRectangulaire;
import view.View;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Niveau {

    public ArrayList<Obstacle> list_peg = new ArrayList<Obstacle>();
    protected int niveau;
    public BufferedImage image;

    public Niveau(int i) {
        BufferedImage img = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);
        try {
            img = ImageIO.read(new File("ressources/peg.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.image = img;
        switch (i) {
            case 1:
                niveau_1();//liste de lignes
                niveau = 1;
                break;
            case 2:
                niveau_2();//voiture et nuages
                niveau = 2;
                break;
            case 3:
                niveau_3();//plusieurs carrés 
                niveau = 3;
                break;
            case 4:
                niveau_4();
                niveau = 4;
                break;
        }
    }

    protected void diagonal(double x, double y, ArrayList<Obstacle> list, int pegUsed, boolean direction, boolean sens) {
        double nvx = x;
        double nvy = y;
        for (int i = 0; i < pegUsed; i++) {
            Pegs p = new Pegs(nvx, nvy, 1,image);
            list.add(p);
            nvx = direction? nvx + p.getDiametre() : nvx - p.getDiametre();//true pour droite, false pour gauche
            nvy = sens? nvy - p.getRayon() : nvy + p.getRayon();//true pour haut, false pour bas
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
            Pegs p = new Pegs(nvx, y, 1,image);
            list.add(p);
            nvx += p.getDiametre()/View.getRatioX()/2;
        }
    }

    protected void colonne(double x, double y, ArrayList<Obstacle> list, int pegUsed) {
        double nvy = y;
        for (int i = 0; i < pegUsed; i++) {
            Pegs p = new Pegs(x, nvy, 1,image);
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
                lignes(25*View.getRatioX(), (150+(getDiametre()*i))*View.ratioY, list_peg, 20);
            }
            else{
                ObstacleRectangulaire obr = new ObstacleRectangulaire(25, 200+(getDiametre()*i));
                ObstacleRectangulaire obr2 = new ObstacleRectangulaire(600, 200+(getDiametre()*i));
                lignes(175, 200+(getDiametre()*i), list_peg, 17);
                list_peg.add(obr);
                list_peg.add(obr2);
            }
        }
        ObstacleRectangulaire barriereGauche= new ObstacleRectangulaire(getDiametre()*View.ratioX*4, 525,100*View.ratioY,50);
        ObstacleRectangulaire barriereDroit= new ObstacleRectangulaire(getDiametre()*View.ratioX*9, 525 ,100*View.ratioY,50);
        list_peg.add(barriereGauche);
        list_peg.add(barriereDroit);
        
    }

    protected void niveau_2(){
       lignes(25, 1200, list_peg, 20);
       //roue de Gauche
       ObstacleRebondissant roueGauche= new ObstacleRebondissant(25+getRayon()*4.5, 1200-getRayon()*5, 200);
       colonne(roueGauche.x-getRayon()*2.5, roueGauche.y, list_peg, 2);
       colonne(roueGauche.x+getRayon()*5.5, roueGauche.y, list_peg, 2);
       lignes(roueGauche.x-getRayon()*0.5, roueGauche.y-getDiametre(), list_peg, 3);
       list_peg.add(roueGauche);       
       //roue de Droite
       ObstacleRebondissant roueDroite= new ObstacleRebondissant(25+getRayon()*24.5, 1200-getRayon()*5, 200);
       colonne(roueDroite.x-getRayon()*2.5, roueDroite.y, list_peg, 2);
       colonne(roueDroite.x+getRayon()*5.5, roueDroite.y, list_peg, 2);
       lignes(roueDroite.x-getRayon()*0.5, roueDroite.y-getDiametre(), list_peg, 3);
       list_peg.add(roueDroite);
       //capot 
       lignes(25+getDiametre()*6, roueGauche.y+getDiametre(), list_peg, 5);
       //fenetre
       for(int i=0; i<3;i++){
            lignes(25+getDiametre()*5, 550+100*i, list_peg, 7);
       }
       //nuages
       ObstacleRectangulaire nuageGauche1=new ObstacleRectangulaire(200, 201, getDiametre()*2, getRayon());
       ObstacleRectangulaire nuageGauche2=new ObstacleRectangulaire(150, 250, getDiametre()*3, getRayon());
       ObstacleRectangulaire nuageGauche3=new ObstacleRectangulaire(200, 299, getRayon()*3, getRayon());
       list_peg.add(nuageGauche1);list_peg.add(nuageGauche2);list_peg.add(nuageGauche3);
       ObstacleRectangulaire nuageDroit1=new ObstacleRectangulaire(200+getDiametre()*12, 301, getDiametre()*2, getRayon());
       ObstacleRectangulaire nuageDroit2=new ObstacleRectangulaire(150+getDiametre()*12, 350, getDiametre()*3, getRayon());
       ObstacleRectangulaire nuageDroit3=new ObstacleRectangulaire(200+getDiametre()*12, 399, getRayon()*3, getRayon());
       list_peg.add(nuageDroit1);list_peg.add(nuageDroit2);list_peg.add(nuageDroit3);
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
        list_peg.add(colonneGauche);list_peg.add(colonneDroit);
        list_peg.add(ligneGaucheHaut);list_peg.add(ligneGaucheBas);
        list_peg.add(ligneDroitHaut);list_peg.add(ligneDroitBas);
    }
    protected void niveau_4(){
        int x4=150; int y4=600;
        //bouche et tete
        diagonal(x4, y4, list_peg, 4, true, true);
        diagonal(x4+getDiametre(), y4+getRayon(), list_peg, 2, true, false);
        diagonal(x4+getDiametre(), y4+getRayon()*3, list_peg, 2, false, false);
        diagonal(x4+getDiametre(), y4+getRayon()*5, list_peg, 3, true, false);
        //aile haute
        lignes(x4+getDiametre()*4, y4-getRayon()*3, list_peg, 4);
        lignes(x4+getDiametre()*5, y4-getRayon()*5, list_peg, 3);
        lignes(x4+getDiametre()*6, y4-getRayon()*7, list_peg, 3);
        //aile basse
        lignes(x4+getDiametre()*4, y4+getRayon()*7, list_peg, 4);
        lignes(x4+getDiametre()*5, y4+getRayon()*9, list_peg, 3);
        lignes(x4+getDiametre()*6, y4+getRayon()*11, list_peg, 3);
        //arriere
        diagonal(x4+getDiametre()*8, y4-getRayon()*3, list_peg, 5, true, false);
        diagonal(x4+getDiametre()*8, y4+getRayon()*7, list_peg, 5, true, true);
        //queue
        diagonal(x4+getDiametre()*13, y4, list_peg, 4, true, true);
        diagonal(x4+getDiametre()*13, y4+getDiametre()*2, list_peg, 4, true, false);
        colonne(x4+getDiametre()*17, y4+getDiametre()*2, list_peg, 2);
        colonne(x4+getDiametre()*17, y4-getDiametre(), list_peg, 2);
        colonne(x4+getDiametre()*16, y4+getDiametre(), list_peg, 1);
        //visage
        lignes(x4+getRayon()*7, y4-getRayon(), list_peg, 1);
        ObstacleRectangulaire ligne = new ObstacleRectangulaire(x4+getRayon()*9, y4-getRayon(), getRayon(), getRayon()*7);
        list_peg.add(ligne);
    }
    public ArrayList<Obstacle> getList() {return list_peg;}
    public int getNiveau() {return niveau;}
    public double getRayon(){
        Pegs p = new Pegs();
        return p.getRayon();
    }
    public double getDiametre(){
        Pegs p = new Pegs();
        return p.getDiametre();
    }

    public void setList(ArrayList<Obstacle> charge) {
        this.list_peg = charge;
    }
}
