package controller;

import view.*;
import javax.swing.*;
import model.*;
import model.Modele;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Controleur {

    protected View view;
    protected Modele modele;
    protected double angleTir;
    private Timer timer;

    public Controleur() {
        modele = new Modele();
        view = new View(this);
        modele.setBalle(new Balle(view.getPartie().getWidth() / 2, 0d, 200d));
        // --------------ANIMATION----------------------
        timer = new Timer(30, new ActionListener() {
            double t = 0;

            public void actionPerformed(ActionEvent e) {
                // seconde++;

                // canon
                view.setColorX();
                view.setColorX();
                view.calculeAngle();

                // puit
                view.placePuit();

                // munition
                /*
                 * if (CONDITION) { // si la balle atteri dans le puit
                 * nbMunition++;
                 * munition.removeAll();
                 * afficheMunition();
                 * munition.revalidate();
                 * }
                 */
                if (modele.getBalle() != null) {
                    modele.getBalle().update(180 - getAngleTir(), t);
                    if (modele.getBalle().getY() > view.getPartie().getHeight()) {
                        t = 0;
                    }
                    t += 0.3;
                    ArrayList<Obstacle> a = modele.getNiveau().getList();
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
        this.modele.setBalle(null);
        this.angleTir = this.view.getAngle();
        this.modele.setBalle(new Balle(view.getPartie().getWidth() / 2, 0d, 200d));
    }

    public double getAngleTir() {
        return this.angleTir;
    }
}