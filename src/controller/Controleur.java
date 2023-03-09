package controller;

import view.*;
import javax.swing.*;
import model.*;
import model.Modele;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controleur {

    public View view;
    public Modele modele;
    public double angleTir;
    private Timer timer;

    public Controleur() {
        modele = new Modele();
        view = new View(this);
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

                // retire LE pegs touché par la balle
                if (modele.niveau.list_peg.size() != 0) {
                    Pegs p = collisionPeg();
                    if (p != null) {
                        modele.niveau.list_peg.remove(p);
                    }
                }

                if (modele.balle != null) {
                    modele.balle.update(180 - angleTir, t);
                    if (modele.balle.y > View.getPartie().getHeight()) {
                        t = 0;
                    }
                    t += 0.3;
                }

                view.repaint();
            }
        });
        timer.start();

    }

    public Pegs collisionPeg() {

        Balle b = modele.balle;
        for (int i = 0; i < modele.niveau.list_peg.size(); i++) {
            Pegs p = (Pegs) modele.niveau.list_peg.get(i);
            double distance = Math.sqrt(Math.pow(b.getX() - p.getX(), 2) +
                    Math.pow(b.getY() - p.getY(), 2));

            if (distance <= b.rayon + p.rayon) {
                System.out.println("x de balle : " + b.getX() + '\n' + "y de balle : " + b.getY() + '\n' + "x de peg : "
                        + p.getX() + '\n' + "y de peg : " + p.getY() + '\n' + "distance : " + distance + '\n'
                        + "rayon balle : " + b.rayon + '\n' + "rayon peg : " + p.rayon + '\n');
                return p; // Collision détectée
            }
        }
        return null;

    }

    public void tirer() {
        this.modele.setBalle(null);
        this.angleTir = this.view.getAngle();
        this.modele.setBalle(new Balle(600d, 0d, 200d));
    }

    // -----------------GETTERS----------------------
    public Modele getModele() {
        return modele;
    }

    public View getView() {
        return view;
    }

    public double getAngleTir() {
        return angleTir;
    }

    public Timer getTimer() {
        return timer;
    }

    // -----------------SETTERS----------------------

    public void setModele(Modele modele) {
        this.modele = modele;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setAngleTir(double angleTir) {
        this.angleTir = angleTir;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

}