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
                view.repaint();

                // puit
                view.placePuit();
                if (modele.getBalle() != null) {
                    modele.getBalle().update();

                    // rebond
                    for (int i = 0; i < modele.getNiveau().getList().size(); i++) {
                        if (modele.getNiveau().getList().get(i) instanceof Pegs) {
                            modele.getBalle().rebond( modele.getNiveau().getList().get(i));
                            if (modele.getBalle().collision( modele.getNiveau().getList().get(i))) {
                                modele.niveau.retirePeg(modele.getNiveau().getList().get(i));
                            }
                        }
                    }

                    if(modele.getBalle().getX()-modele.getBalle().getRayon()/2 <= 0 || modele.getBalle().getX()+modele.getBalle().getRayon()/2 >= 2000){
                        modele.balle.rebondMur();
                    }    
                
                    // munition
                    Point p = view.puit.getLocationOnScreen();
                    //System.out.println("balle x : " + (modele.balle.getX() - 140) + " balle y : " + modele.balle.getY());
                    //System.out.println(p.x + view.puit.getWidth());
                    //System.out.println("puit xonScreen : " + p.x + " puit yonScreen : " + p.y);
                    if (modele.balle.getY() >= view.puit.getY() && ((modele.balle.getX() - 140) >= p.x
                            && (modele.balle.getX() - 140) <= p.x + view.puit.getWidth())) {
                        if (balleEnJeu) {
                            view.nbMunition--;
                            view.munition.removeAll();
                            view.afficheMunition();
                            view.munition.revalidate();
                            balleEnJeu = false;
                        }
                    }
                }
            }
        });
        timer.start();

    }


    public void tirer() {
        if (!this.balleEnJeu) {
            view.nbMunition++;
            view.munition.removeAll();
            view.afficheMunition();
            view.munition.revalidate();

            this.balleEnJeu = true;
            this.modele.setBalle(null);
            t=0;
            this.angleTir=this.view.getAngle();
            this.modele.setBalle(new Balle(2000/2,0d,600d, 180-this.angleTir));
        }
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

    // -----------------GETTERS----------------------
    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public double getT() {
        return this.t;
    }

    public void balleHorsJeu() {
        this.balleEnJeu = false;
    }
}