package controller;

import view.*;
import model.*;
import model.Modele;

public class Controleur {

    protected View view;
    protected Modele modele;
    protected double angleTir;

    public Controleur() {
        modele = new Modele();
        view = new View(this);

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
        this.modele.setBalle(null);
        this.angleTir=this.view.getAngle();
        this.modele.setBalle(new Balle(600d,0d,200d));
    }

    public double getAngleTir(){
        return this.angleTir;
    }
}