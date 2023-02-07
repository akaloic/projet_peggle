package controller;

import view.*;
import model.*;

public class Controleur {

    protected View view;
    protected Modele modele;

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

}