package view;

import model.*;
import model.sousObjet.*;
import controller.*;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {

    // public boolean enJeu = true; // Pour mettre le jeu en pose si besoin
    private JPanel balle;
    private JPanel canon;
    private JPanel[] obstacles;
    private JPanel fond;
    private JPanel munition;
    private JPanel partie;

    protected Controleur controleur;

    public View(Controleur controleur) {

        this.setTitle("Hit the Peggles");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.controleur = controleur;
        Modele m = controleur.getModele();

        // -------Disposition du jeu-------
        fond = new JPanel(); // represente la fenetre
        fond.setLayout(new GridLayout(1, 2));

        munition = new JPanel(); // Partie de gauche de la fenetre
        munition.setBackground(Color.BLUE);

        partie = new JPanel(); // Partie du jeu, a droite de la fenetre
        partie.setBackground(Color.RED);

        fond.add(munition, BorderLayout.WEST);
        fond.add(partie, BorderLayout.CENTER);

        System.out.println("test");
        // -------Disposition du jeu-------

        // -------Elements du jeu-------
        /*
         * balle = getBallPanel(m.getBalle());
         * 
         * //this.add(balle);
         * this.add(new JLabel("test"), 0);
         */
        // -------Elements du jeu-------

        this.add(fond);
        this.pack();
        this.setVisible(true);

    }

    private JPanel getBallPanel(Balle b) {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.RED);
                // g.fillOval((int) b.getX(), (int) b.getY(), (int) b.getLargeur(), (int)
                // b.getHauteur());
                g.fillOval(200, 200, 500, 500);
            }
        };
    }

}
