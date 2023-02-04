package view;

import model.*;
import controller.*;

import java.lang.ModuleLayer.Controller;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class View extends JFrame {

    // public boolean enJeu = true; // Pour mettre le jeu en pose si besoin

    protected Controleur controleur;

    public View(Controleur c) {

        this.setTitle("Hit the Peggles");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        this.controleur = c;
        Modele m = controleur.getModele();

        JPanel balle = new JPanel();
        balle.setBounds(
                (int) m.getBalle().getX(),
                (int) m.getBalle().getY(),
                (int) m.getBalle().getLargeur(),
                (int) m.getBalle().getHauteur());

        this.add(balle);
    }
}
