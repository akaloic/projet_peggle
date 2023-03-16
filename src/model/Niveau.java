package model;

import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Niveau {

    public ArrayList<Obstacle> list_peg = new ArrayList<Obstacle>();
    protected int niveau;
    public static BufferedImage image;


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
                niveau_4();//sardine
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
            nvx += p.getDiametre();
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
        double y1=200; double x1=getRayon()/2;
        for(int i=0;i<6;i++){
            if(i%2==0){
                lignes(x1+getRayon(), y1+(getDiametre()*i), list_peg, 15);
            }
            else{
                Quadrilatere obr = new Quadrilatere(x1, y1+(getDiametre()*i));
                Quadrilatere obr2 = new Quadrilatere(x1+getDiametre()*13, y1+(getDiametre()*i));
                lignes(x1+getRayon()*6, y1+(getDiametre()*i), list_peg, 10);
                list_peg.add(obr);
                list_peg.add(obr2);
            }
            Quadrilatere barriereGauche= new Quadrilatere(x1+getDiametre()*3, y1+getDiametre()*6+getRayon(),getDiametre()*3,getRayon());
            Quadrilatere barriereDroit= new Quadrilatere(x1+getDiametre()*9+getRayon(), y1+getDiametre()*6+getRayon(),getDiametre()*3,getRayon());
            list_peg.add(barriereGauche);
            list_peg.add(barriereDroit);
            
        }
        
    }

    protected void niveau_2(){//voiture
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
       Quadrilatere nuageGauche1=new Quadrilatere(200, 201, getDiametre()*2, getRayon());
       Quadrilatere nuageGauche2=new Quadrilatere(150, 250, getDiametre()*3, getRayon());
       Quadrilatere nuageGauche3=new Quadrilatere(200, 299, getRayon()*3, getRayon());
       list_peg.add(nuageGauche1);list_peg.add(nuageGauche2);list_peg.add(nuageGauche3);
       Quadrilatere nuageDroit1=new Quadrilatere(200+getDiametre()*12, 301, getDiametre()*2, getRayon());
       Quadrilatere nuageDroit2=new Quadrilatere(150+getDiametre()*12, 350, getDiametre()*3, getRayon());
       Quadrilatere nuageDroit3=new Quadrilatere(200+getDiametre()*12, 399, getRayon()*3, getRayon());
       list_peg.add(nuageDroit1);list_peg.add(nuageDroit2);list_peg.add(nuageDroit3);
    }
    protected void niveau_3(){//series de carrés
        double x3=getDiametre()+getRayon()/2; double y3=getDiametre()*3;
        int m1=0;int m2=0;int j=0;
        for(int i=3; i>-1;i--){
            carres(x3+getDiametre()*m1, y3, list_peg, i);
            carres(x3+getDiametre()*m2, y3+getDiametre()*(6-j), list_peg, j);
            m1+=i+2;m2+=j+2;j++;
        }
        Quadrilatere colonneGauche = new Quadrilatere(x3-getDiametre(), y3-getRayon(), getRayon(), getRayon()*15+1);
        Quadrilatere colonneDroit = new Quadrilatere(x3+getDiametre()*14+0.5, y3-getRayon(), getRayon(), getRayon()*15+1);
        Quadrilatere ligneGaucheHaut = new Quadrilatere(x3-getDiametre(), y3-getDiametre(), getDiametre()*6, getRayon());
        Quadrilatere ligneGaucheBas = new Quadrilatere(x3-getDiametre(), y3+getRayon()*16, getDiametre()*6, getRayon());
        Quadrilatere ligneDroitHaut = new Quadrilatere(x3+getDiametre()*8+getRayon(), y3-getDiametre(), getDiametre()*6, getRayon());
        Quadrilatere ligneDroitBas = new Quadrilatere(x3+getDiametre()*8+getRayon(), y3+getRayon()*16, getDiametre()*6, getRayon());
        list_peg.add(colonneGauche);list_peg.add(colonneDroit);
        list_peg.add(ligneGaucheHaut);list_peg.add(ligneGaucheBas);
        list_peg.add(ligneDroitHaut);list_peg.add(ligneDroitBas);
    }
    protected void niveau_4(){//sardine
        double x4=getRayon()/2; double y4=getDiametre()*5;
        //bouche et tete
        diagonal(x4, y4, list_peg, 4, true, true);
        diagonal(x4+getDiametre(), y4+getRayon(), list_peg, 2, true, false);
        diagonal(x4+getDiametre(), y4+getRayon()*3, list_peg, 2, false, false);
        diagonal(x4+getDiametre(), y4+getRayon()*5, list_peg, 3, true, false);
        //aile haute
        lignes(x4+getDiametre()*4, y4-getRayon()*3, list_peg, 3);
        lignes(x4+getDiametre()*5, y4-getRayon()*5, list_peg, 3);
        lignes(x4+getDiametre()*6, y4-getRayon()*7, list_peg, 3);
        //aile basse
        lignes(x4+getDiametre()*4, y4+getRayon()*7, list_peg, 3);
        lignes(x4+getDiametre()*5, y4+getRayon()*9, list_peg, 3);
        lignes(x4+getDiametre()*6, y4+getRayon()*11, list_peg, 3);
        //arriere
        diagonal(x4+getDiametre()*7, y4-getRayon()*3, list_peg, 5, true, false);
        diagonal(x4+getDiametre()*7, y4+getRayon()*7, list_peg, 5, true, true);
        //queue
        diagonal(x4+getDiametre()*12, y4, list_peg, 3, true, true);
        diagonal(x4+getDiametre()*12, y4+getDiametre()*2, list_peg, 3, true, false);
        colonne(x4+getDiametre()*15, y4+getDiametre()*2, list_peg, 2);
        colonne(x4+getDiametre()*15, y4-getDiametre(), list_peg, 2);
        colonne(x4+getDiametre()*14, y4+getDiametre(), list_peg, 1);
        //visage
        lignes(x4+getRayon()*7, y4-getRayon(), list_peg, 1);
        Quadrilatere ligne = new Quadrilatere(x4+getRayon()*9, y4-getRayon(), getRayon(), getRayon()*6);
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
