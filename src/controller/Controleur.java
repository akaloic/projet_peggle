package controller;

import view.*;
import javax.swing.*;
import model.*;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controleur {

    public View view;
    public Modele modele;
    public double angleTir;
    public Timer timer;
    public double t;
    public boolean balleEnJeu;
    public int facteur;

    public Controleur() {
        this.balleEnJeu = false;
        modele = new Modele();
        new Image();
        new Sauvegarde();
        view = new View(this);
        facteur = 1;
        // --------------ANIMATION----------------------
        timer = new Timer(30, new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // canon
                view.setColorX();
                view.setColorX();
                view.calculeAngle();
                view.repaint();

                // puit
                view.placePuit();
                if (modele.getBalle() != null) {
                    modele.getBalle().update();

                    // rebond
                    for (int i = 0; i < modele.niveau.list.size(); i++) {
                        if (modele.niveau.list.get(i) instanceof Pegs) {
                            modele.getBalle().rebond((Pegs) modele.niveau.list.get(i));
                            if (modele.getBalle().collision((Pegs) modele.niveau.list.get(i))) {
                                boolean detruit = modele.niveau.detruit(i);
                                if (detruit) {
                                    double x = modele.niveau.list.get(i).getX();
                                    double y = modele.niveau.list.get(i).getY();
                                    view.addExplosion(x, y);
                                    modele.niveau.list.remove(i);
                                }
                                modele.player.calculScore(detruit, facteur++);
                            }
                        }
                    }

                    if (modele.getBalle().getX() - modele.getBalle().rayon / 2 <= 0
                            || modele.getBalle().getX() + modele.getBalle().rayon / 2 >= view.getPartie().getWidth()) {
                        modele.balle.rebondMur();
                    }

                    // munition
                    Point p = view.puit.getLocationOnScreen();
                    if (modele.balle.getY() + (view.partie.getHeight() / 2) >= view.puit.getY()
                            + (view.partie.getHeight() / 2)
                            && ((modele.balle.getX() - 140) >= p.x
                                    && (modele.balle.getX() - 140) <= p.x + view.puit.getWidth())) {
                        if (balleEnJeu) {
                            view.nbMunition--;
                            view.munition.removeAll();
                            view.afficheMunition();
                            view.munition.revalidate();
                            balleHorsJeu();
                        }
                    }

                    // munition
                    /*
                     * if (modele.balle.getY() >= view.puit.getY() && (modele.balle.getX() >=
                     * view.puit.getX()
                     * && modele.balle.getX() <= view.puit.getWidth())) {
                     * view.nbMunition++;
                     * view.munition.removeAll();
                     * view.afficheMunition();
                     * view.munition.revalidate();
                     * // System.out.println("CA MARCHEEEEEEEEEEE");
                     * }
                     */

                    if (modele.getBalle().getY() > view.getPartie().getHeight()) {
                        modele.setBalle(null);
                        balleHorsJeu();
                    }

                }
            }
        });
        timer.start();
    }

    public void balleHorsJeu() {
        this.balleEnJeu = false;
        facteur = 1;
    }

    public void tirer() {
        if (!this.balleEnJeu) {
            view.nbMunition++;
            view.munition.removeAll();
            view.afficheMunition();
            view.munition.revalidate();

            this.balleEnJeu = true;
            this.modele.setBalle(null);
            t = 0;
            this.angleTir = this.view.getAngle();
            this.modele.setBalle(new Balle(view.getPartie().getWidth() / 2 - 25, 0d, 300, 180 - this.angleTir));
        }
    }

    public void finTour() {
        this.modele.setBalle(null);
        this.balleEnJeu = false;
        this.modele.niveau = new Niveau(this.modele.niveau.niveau + 1);
        this.view.partie.removeAll();
        this.view.changerPanel(view.choixNiveauPane(this));
        this.view.partie.revalidate();
    }

    // ---------GETTER SETTER---------
    public double getAngleTir() {
        return angleTir;
    }
    // ---------GETTER SETTER---------
}
