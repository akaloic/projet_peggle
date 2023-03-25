package model;

import java.util.ArrayList;


public class Niveau {

    public ArrayList<Obstacle> list_peg = new ArrayList<Obstacle>();
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
        }
    }

    public void diagonal(double x, double y, ArrayList<Obstacle> list, int pegUsed, String direction) {
        double nvx = x;
        double nvy = y;
        for (int i = 0; i < pegUsed; i++) {
            Pegs p = new Pegs(nvx, nvy, 1);
            list.add(p);
            nvx = direction.equals("gauche") ? nvx - p.getDiametre() : nvx + p.getDiametre();
            nvy += p.getDiametre();
        }
    }

    public void carres(double x, double y, ArrayList<Obstacle> list, int pegCote) {
        lignes(x, y, list, pegCote + 2);
        colonne(x, y + getDiametre(), list, pegCote);
        colonne(x + ((pegCote + 1) * getDiametre()), y + getDiametre(), list, pegCote);
        lignes(x, y + ((pegCote + 1) * getDiametre()), list, pegCote + 2);
    }

    public void lignes(double x, double y, ArrayList<Obstacle> list, int pegUsed) {
        double nvx = x;
        for (int i = 0; i < pegUsed; i++) {
            Pegs p = new Pegs(nvx, y, 1);
            list.add(p);
            nvx += p.getDiametre();
        }
    }

    public void colonne(double x, double y, ArrayList<Obstacle> list, int pegUsed) {
        double nvy = y;
        for (int i = 0; i < pegUsed; i++) {
            Pegs p = new Pegs(x, nvy, 1);
            list.add(p);
            nvy += p.getDiametre();
        }
    }

    public void triangle(double x, double y, ArrayList<Obstacle> list, int lignes, Boolean alenvers) {
        double nvx = x;
        double nvy = y;
        for (int i = 0; i <= lignes; i++) {
            lignes(nvx, nvy, list, i);
            nvx -= getRayon();
            nvy = alenvers == false ? nvy + getDiametre() : nvy - getDiametre();
        }
    }

    public void carresRemplis(double x, double y, ArrayList<Obstacle> list, int pegCote) {
        double nvy = y;
        for (int i = 0; i < pegCote; i++) {
            lignes(x, nvy, list, pegCote);
            nvy += getDiametre();
        }
    }

    public void niveau_1() {
        for (int i = 0; i < 6; i++) {
            if (i % 2 == 0) {
                lignes(25, 400 + (getDiametre() * i), list_peg, 20);
            } else {
                Pegs obr = new Pegs(25, 400 + (getDiametre() * i), i);
                Pegs obr2 = new Pegs(150 + (50 * 17 * 2), 400 + (getDiametre() * i), i);
                lignes(175, 400 + (getDiametre() * i), list_peg, 17);
                list_peg.add(obr);
                list_peg.add(obr2);
            }
            Pegs barriereGauche = new Pegs(getDiametre() * 3 - 25, 1025, 400, 50);
            Pegs barriereDroit = new Pegs(25 + getDiametre() * 17 - getDiametre() * 4,
                    1025, 400, 50);
            list_peg.add(barriereGauche);
            list_peg.add(barriereDroit);
        }
    }

    protected void niveau_2() {
        /*lignes(25, 1200, list_peg, 20);
        // roue de Gauche
        ObstacleRebondissant roueGauche = new ObstacleRebondissant(25 + getRayon() * 4.5, 1200 - getRayon() * 5, 200);
        colonne(roueGauche.x - getRayon() * 2.5, roueGauche.y, list_peg, 2);
        colonne(roueGauche.x + getRayon() * 5.5, roueGauche.y, list_peg, 2);
        lignes(roueGauche.x - getRayon() * 0.5, roueGauche.y - getDiametre(), list_peg, 3);
        list_peg.add(roueGauche);
        // roue de Droite
        ObstacleRebondissant roueDroite = new ObstacleRebondissant(25 + getRayon() * 24.5, 1200 - getRayon() * 5, 200);
        colonne(roueDroite.x - getRayon() * 2.5, roueDroite.y, list_peg, 2);
        colonne(roueDroite.x + getRayon() * 5.5, roueDroite.y, list_peg, 2);
        lignes(roueDroite.x - getRayon() * 0.5, roueDroite.y - getDiametre(), list_peg, 3);
        list_peg.add(roueDroite);
        // capot
        lignes(25 + getDiametre() * 6, roueGauche.y + getDiametre(), list_peg, 5);
        // fenetre
        for (int i = 0; i < 3; i++) {
            lignes(25 + getDiametre() * 5, 550 + 100 * i, list_peg, 7);
        }
        // nuages
        ObstacleRectangulaire nuageGauche1 = new ObstacleRectangulaire(200, 201, getDiametre() * 2, getRayon());
        ObstacleRectangulaire nuageGauche2 = new ObstacleRectangulaire(150, 250, getDiametre() * 3, getRayon());
        ObstacleRectangulaire nuageGauche3 = new ObstacleRectangulaire(200, 299, getRayon() * 3, getRayon());
        list_peg.add(nuageGauche1);
        list_peg.add(nuageGauche2);
        list_peg.add(nuageGauche3);
        ObstacleRectangulaire nuageDroit1 = new ObstacleRectangulaire(200 + getDiametre() * 12, 301, getDiametre() * 2,
                getRayon());
        ObstacleRectangulaire nuageDroit2 = new ObstacleRectangulaire(150 + getDiametre() * 12, 350, getDiametre() * 3,
                getRayon());
        ObstacleRectangulaire nuageDroit3 = new ObstacleRectangulaire(200 + getDiametre() * 12, 399, getRayon() * 3,
                getRayon());
        list_peg.add(nuageDroit1);
        list_peg.add(nuageDroit2);
        list_peg.add(nuageDroit3);*/
    }

    protected void niveau_3() {
        /*int m1 = 0;
        int m2 = 0;
        int j = 0;
        for (int i = 3; i > -1; i--) {
            carres(325 + getDiametre() * m1, 300, list_peg, i);
            carres(325 + getDiametre() * m2, 300 + getDiametre() * (6 - j), list_peg, j);
            m1 += i + 2;
            m2 += j + 2;
            j++;
        }
        ObstacleRectangulaire colonneGauche = new ObstacleRectangulaire(325 - getDiametre(), 300 - getRayon(),
                getRayon(), getRayon() * 17 + 1);
        ObstacleRectangulaire colonneDroit = new ObstacleRectangulaire(325 + getDiametre() * 14, 300 - getRayon(),
                getRayon(), getRayon() * 17 + 1);
        ObstacleRectangulaire ligneGaucheHaut = new ObstacleRectangulaire(325 - getDiametre(), 300 - getDiametre(),
                getDiametre() * 6, getRayon());
        ObstacleRectangulaire ligneGaucheBas = new ObstacleRectangulaire(325 - getDiametre(), 300 + getRayon() * 16,
                getDiametre() * 6, getRayon());
        ObstacleRectangulaire ligneDroitHaut = new ObstacleRectangulaire(325 + getDiametre() * 8 + getRayon(),
                300 - getDiametre(), getDiametre() * 6, getRayon());
        ObstacleRectangulaire ligneDroitBas = new ObstacleRectangulaire(325 + getDiametre() * 8 + getRayon(),
                300 + getRayon() * 16, getDiametre() * 6, getRayon());
        list_peg.add(colonneGauche);
        list_peg.add(colonneDroit);
        list_peg.add(ligneGaucheHaut);
        list_peg.add(ligneGaucheBas);
        list_peg.add(ligneDroitHaut);
        list_peg.add(ligneDroitBas);*/
    }

    public boolean detruit(int i) {
        if (list_peg.get(i).vie - 1 == 0) {
            return true;
        } else {
            list_peg.get(i).vie--;
            return false;
        }
    }

    public double getRayon() {
        Pegs p = new Pegs();
        return p.getRayon();
    }

    public double getDiametre() {
        Pegs p = new Pegs();
        return p.getDiametre();
    }
}
