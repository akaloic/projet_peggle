package controller;

import view.*;
import javax.swing.*;
import model.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controleur {

    public View view;
    public Modele modele;
    public double angleTir;
    private Timer timer;
    protected double t;
    protected boolean balleEnJeu;

    public Controleur() {
        this.balleEnJeu = false;
        modele = new Modele();
        view = new View(this);
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
                                modele.getNiveau().getList().remove(i);
                            }
                        }
                    }

                    // munition
                    if (modele.balle.getY() >= view.puit.getY() && (modele.balle.getX() >= view.puit.getX()
                            && modele.balle.getX() <= view.puit.getWidth())) {
                        view.nbMunition++;
                        view.munition.removeAll();
                        view.afficheMunition();
                        view.munition.revalidate();
                        // System.out.println("CA MARCHEEEEEEEEEEE");
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
    // ---------GETTER SETTER---------

    public void tirer() {
        if (!this.balleEnJeu) {
            this.balleEnJeu = true;
            this.modele.setBalle(null);
            t = 0;
            this.angleTir = this.view.getAngle();
            this.modele.setBalle(new Balle(2000 / 2, 0d, 300d, 180 - this.angleTir));
        }
    }

    public double getAngleTir() {
        return this.angleTir;
    }

    public double getT() {
        return this.t;
    }

    public void balleHorsJeu() {
        this.balleEnJeu = false;
    }
}