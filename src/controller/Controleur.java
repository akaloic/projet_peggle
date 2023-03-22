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
    public static int facteur;

    public Controleur() {
        this.balleEnJeu = false;
        modele = new Modele();
        view = new View(this);
        facteur = 1;
        // --------------ANIMATION----------------------
        timer = new Timer(30, new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                // canon
                view.setColorX();
                view.setColorX();
                view.calculeAngle();

                // puit
                view.placePuit();

                if (modele.getBalle() != null) {
                    modele.getBalle().update();

                    // rebond
                    for (int i = 0; i < modele.getNiveau().getList().size(); i++) {
                        if (modele.getNiveau().getList().get(i) instanceof Pegs) {
                            modele.getBalle().rebond((Pegs) modele.getNiveau().getList().get(i));
                            if (modele.getBalle().collision((Pegs) modele.getNiveau().getList().get(i))) {
                                boolean detruit = modele.niveau.retirePeg(i);
                                modele.player.calculScore(detruit, i++);
                            }
                        }
                    }

                    // munition
                    Point p = view.puit.getLocationOnScreen();
                    if (modele.balle.getY() + (view.partie.HEIGHT / 2) >= view.puit.getY() + (view.partie.HEIGHT / 2)
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

                    if (modele.getBalle().getY() * View.ratioY > view.getPartie().getHeight()) {
                        modele.setBalle(null);
                        balleHorsJeu();
                    }

                }
                view.repaint();
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
            this.modele.setBalle(new Balle(2000 / 2, 0d, 300d, 180 - this.angleTir));
        }
    }

    public void finTour() {
        this.modele.setBalle(null);
        this.balleEnJeu = false;
        this.modele.niveau = new Niveau(this.modele.niveau.getNiveau() + 1);
        this.view.partie.removeAll();
        this.view.changerPanel(view.choixNiveauPane(this));
        this.view.partie.revalidate();
    }

    // ---------GETTER SETTER---------
    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Modele getModele() {
        return modele;
    }

    public void setModele(Modele modele) {
        this.modele = modele;
    }

    public double getAngleTir() {
        return this.angleTir;
    }

    public double getT() {
        return this.t;
    }
    // ---------GETTER SETTER---------

}