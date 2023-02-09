package view;

import controller.*;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class View extends JFrame {

    // public boolean enJeu = true; // Pour mettre le jeu en pose si besoin
    private JPanel balle;
    private JPanel canon;
    private JPanel[] obstacles;
    private JPanel puit;
    private JPanel fond;
    private JPanel munition;
    private JPanel fondGauche;
    private JPanel partie;
    private JButton leave;
    private boolean enJeu = true;
    private boolean balleEnJeu = false;
    private int angle;
    private String chemin = System.getProperty("user.dir") + "/ressources/";

    protected Controleur controleur;
    double mouseX;
    double mouseY;

    int colorX = 25;
    int colorY = 15;

    public View(Controleur controleur) {

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) size.getWidth();
        int height = (int) size.getHeight();

        this.setSize(width, height);
        this.setTitle("Hit the Peggles");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setUndecorated(true); // nécessaire sinon this.getHeight et this.getWidth renvoie 0
        this.controleur = controleur;
        // Modele m = controleur.getModele();

        fond = new JPanel();
        fond.setLayout(new BorderLayout());

        // --------------DROITE---------------------
        partie = new JPanel();
        partie.setLayout(new GridBagLayout());
        partie.setBackground(Color.darkGray);
        GridBagConstraints c = new GridBagConstraints();

        canon = new JPanel();
        canon.setLayout(new BorderLayout());
        canon.setBackground(Color.black);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.fill = GridBagConstraints.BOTH;
        partie.add(canon, c);

        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.white);
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        partie.add(panel2, c);

        puit = new JPanel();
        puit.setLayout(new BorderLayout());
        puit.setBackground(Color.darkGray);
        ImageIcon image = new ImageIcon(chemin + "puit.png");
        JLabel label = new JLabel(image);
        label.setPreferredSize(new Dimension(puit.getWidth(), puit.getHeight()));
        puit.add(label);
        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 0.2;
        partie.add(puit, c);

        fond.add(partie, BorderLayout.CENTER);
        // --------------DROITE---------------------

        // --------------GAUCHE---------------------
        fondGauche = new JPanel();
        fondGauche.setLayout(new BorderLayout());
        fondGauche.setBackground(Color.gray);
        fondGauche.setPreferredSize(new Dimension(this.getWidth() / 5, this.getHeight()));

        munition = new JPanel();
        munition.setLayout(new GridLayout(10, 1));
        fondGauche.add(munition, BorderLayout.CENTER);

        leave = new JButton("Fermer");
        leave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fondGauche.add(leave, BorderLayout.SOUTH);

        fond.add(fondGauche, BorderLayout.WEST);
        // --------------GAUCHE---------------------

        this.add(fond);
        this.setVisible(true);
    }

    public void calcLigne(double x1, double y1, double x2, double y2) {
        double a = (y2 - y1) / (x2 - x1);
        double b = a * x1 - y1;
        System.out.println(a + "x+" + b);
    }

    private void calculeAngle() {
        mouseX = MouseInfo.getPointerInfo().getLocation().getX();
        mouseY = MouseInfo.getPointerInfo().getLocation().getY();
        int pointX = munition.getWidth() + canon.getX() + canon.getWidth() / 2 + 45;
        double angle1 = Math.atan2(mouseY - 0, mouseX - pointX);
        double angle2 = Math.atan2(this.getHeight() - 0, pointX - pointX);
        angle = (int) Math.toDegrees(angle1 - angle2);
    }
}
