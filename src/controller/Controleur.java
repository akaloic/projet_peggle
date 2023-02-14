package controller;

import view.*;
import model.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controleur {

    protected View view;
    protected Modele modele;

    public Controleur() {
        modele = new Modele();
        view = new View(this);
        view.addKeyListener(new KeyAdapter() {
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
}