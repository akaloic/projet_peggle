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
        fond.setLayout(new GridLayout(1,2));

        munition = new JPanel(); // Partie de gauche de la fenetre
        munition.setBackground(Color.BLUE);

        partie = new JPanel(); // Partie du jeu, a droite de la fenetre
        partie.setBackground(Color.RED);
        partie.setLayout(null);

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
        balle = getBallPanel(m.getBalle());
        fond.add(balle);

        // Obstacle obs = new Obstacle(0, 0, 100, 100, 0, false, 100);
        JPanel obstacle = getObstaclesPanel(new Obstacle(0, 0, 100, 100, 0, false, 100));
        fond.add(obstacle);
        this.add(fond);
        this.pack();
        this.setVisible(true);
    }

    private JPanel getBallPanel(Balle b) {
        return new JPanel() {
            @Override
            public void paint(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(Color.BLACK);
                // g.fillOval((int) b.getX(), (int) b.getY(), (int) b.getLargeur(), (int)
                // b.getHauteur());
                g2d.fillOval(0, 0, 50, 50);
            }
        };
    }
    private JPanel getObstaclesPanel(Obstacle obs){
        return new JPanel() {
            @Override
            public void paint(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(Color.YELLOW);
                g2d.fillOval(0, 0, 100, 100);
            }
        };
    }
    public static void main(String[] args) {
        Balle balle = new Balle(100, 100, 100, 100, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        View view = new View(new Controleur());
        // view.getBallPanel(balle);    
        // view.getObstaclePanel(new Obstacle(0, 0, 100, 100, 0, false, 100));
    }
}
