package view;

import model.*;
import model.sousObjet.*;
import controller.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.*;


public class View extends JFrame {

    public boolean enJeu = Visuel.enJeu;
    private JPanel balle;
    private JPanel canon;
    private JPanel[] obstacles;
    private JPanel fond;
    private JPanel munition;
    private JPanel partie;
    private JButton leave = new JButton("Fermer");

    protected Controleur controleur;

    public View(Controleur controleur) {

        this.setTitle("Hit the Peggles");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setUndecorated(true);
        this.setVisible(true);//nécessaire sinon this.getHeight et this.getWidth renvoie 0

        this.controleur = controleur;
        Modele m = controleur.getModele();

        // -------Disposition du jeu-------
        fond = new JPanel(); // represente la fenetre
        fond.setBackground(Color.BLUE);
        fond.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;
        c.weightx = 1;
        c.weighty = 1;

        munition = new JPanel(); // Partie de gauche de la fenetre
        munition.setLayout(new BorderLayout());
        munition.setBackground(Color.gray);
        munition.setPreferredSize(new Dimension(this.getWidth()/5,this.getHeight()));

        partie = new JPanel(); // Partie du jeu, a droite de la fenetre
        partie.setLayout(new GridBagLayout());
        partie.setBackground(Color.darkGray);
        partie.setPreferredSize(new Dimension(this.getWidth()*4/5,this.getHeight()));

        /*---- ELEMENTS DU JEU ----
         * Canon
         * Balle
         * Obstacle(s)
        */
        canon = new JPanel();
        c.anchor =  GridBagConstraints.NORTH;
        partie.add(canon,c);
        canon.setPreferredSize(new Dimension(50,100));
        canon.add(new JLabel("Canon"));

        balle = getBallPanel(m.getBalle());
        partie.add(balle,c);

        obstacles = new JPanel[1];
        JPanel obstacle1 = getObstaclesPanel(new Obstacle(200,200,75,75,0,true,100));
        /*---- FIN ELEMENTS DU JEU ----*/
        fond.add(munition, c);
        c.anchor = GridBagConstraints.EAST;
        fond.add(partie, c);

        munition.add(leave,BorderLayout.SOUTH);
        leave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        this.add(fond);
        this.setVisible(true);
    }

    private JPanel getBallPanel(Balle b) {
        return new JPanel() {
            @Override
            public void paint(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(Color.BLACK);
                g2d.fillOval(0, 0,(int) b.getWidth(), (int)b.getWidth());
            }
        };
    }
    private JPanel getObstaclesPanel(Obstacle obs){
        return new JPanel() {
            @Override
            public void paint(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(Color.YELLOW);
                g2d.fillOval(0, 0, (int)obs.getWidth(),(int) obs.getHeight());
            }
        };
    }
    public static void main(String[] args) {
        View view = new View(new Controleur());
    }
}

