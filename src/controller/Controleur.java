package controller;

import view.*;
import model.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controleur {

    protected View view;
    protected Modele modele;
    protected double angleTir;
    protected double t;
    protected boolean balleEnJeu;

    public Controleur() {
        this.balleEnJeu = false;
        modele = new Modele();
        view = new View(this);
        view.getPartie().requestFocus();//Pour empecher que le bouton leave le prenne
        view.getPartie().addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
              int keyCode = e.getKeyCode();
              if (keyCode == KeyEvent.VK_ENTER) {
                System.out.println("kaboom");
              }
            }
        });

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

    public void tirer(){
        if(!this.balleEnJeu){
            this.balleEnJeu=true;
            this.modele.setBalle(null);
            t=0;
            this.angleTir=this.view.getAngle();
            this.modele.setBalle(new Balle(600d,0d,200d, 180-this.angleTir));
        }
    }

    public double getAngleTir(){
        return this.angleTir;
    }


    public double getT(){
        return this.t;
    }

    public void balleHorsJeu(){
        this.balleEnJeu=false;
    }
}