package view;

import controller.*;
import model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class View extends JFrame {

    // public boolean enJeu = true; // Pour mettre le jeu en pose si besoin
    private JLabel puit;
    private JPanel fond;
    private JPanel munition;
    private JPanel fondGauche;
    private JPanel partie;
    private JButton leave;
    private boolean enJeu = true;
    private boolean balleEnJeu = false;
    private int angle;
    private String chemin = System.getProperty("user.dir") + "/ressources/";
    private Timer timer;
    private int directionX = 5;
    private Controleur controleur;
    private int nbMunition;
    private double mouseX;
    private double mouseY;
    private static int colorX = 25;
    private static int colorY = 15;
    int seconde = 0;

    public View(Controleur controleur) {

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) size.getWidth();
        int height = (int) size.getHeight();

        this.setSize(width, height);
        this.setTitle("Hit the Peggles");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setUndecorated(true); // nécessaire sinon this.getHeight et this.getWidth renvoie 0
        this.controleur = controleur;

        Modele m = controleur.getModele();
        Balle b = m.getBalle();
        Obstacle[] o = m.getObstacles();
        Niveau n = m.getNiveau();
        // nbMunition = n.getNiveau();
        nbMunition = 4; // Pour le moment on met 10 munitions

        fond = new JPanel();
        fond.setLayout(new BorderLayout());

        // --------------DROITE---------------------
        partie = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                dessineCanon(g);
            }
        };
        partie.setLayout(new BorderLayout());
        partie.setBackground(Color.darkGray);

        puit = new JLabel(new ImageIcon(chemin + "puit.png"));

        partie.add(puit);
        fond.add(partie, BorderLayout.CENTER);
        // --------------DROITE---------------------

        // --------------GAUCHE---------------------
        fondGauche = new JPanel();
        fondGauche.setLayout(new BorderLayout());
        fondGauche.setBackground(Color.gray);
        fondGauche.setPreferredSize(new Dimension(this.getWidth() / 7, this.getHeight()));

        munition = new JPanel();
        munition.setLayout(new GridLayout(10, 1));
        afficheMunition();

        leave = new JButton("Fermer");
        leave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        fondGauche.add(munition, BorderLayout.CENTER);
        //fondGauche.add(leave, BorderLayout.SOUTH);

        fond.add(fondGauche, BorderLayout.WEST);
        // --------------GAUCHE---------------------

        this.add(fond);
        this.setVisible(true);

        // --------------ANIMATION----------------------
        timer = new Timer(30, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                seconde++;

                // canon
                colorX -= 1 % 25;
                colorY -= 1 % 25;
                calculeAngle();

                // puit
                placePuit();

                // munition
                /*
                 * if (CONDITION) { // si la balle atteri dans le puit
                 * nbMunition++;
                 * munition.removeAll();
                 * afficheMunition();
                 * munition.revalidate();
                 * }
                 */

                repaint();
            }
        });
        timer.start();
        // --------------ANIMATION----------------------
    }

    public void placePuit() {
        // avec la redimension de l'image plus grande

        puit.setLocation(puit.getX() + directionX, partie.getHeight() / 2);
        if (puit.getX() > partie.getWidth() / 2)
            directionX = -5;
        if (puit.getX() < -partie.getWidth() / 2)
            directionX = 5;
    }

    public void dessineCanon(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int widthBase = 150;
        int heightBase = 150;

        // Pour la ligne multicolore
        Path2D.Double ligne2 = new Path2D.Double();
        ligne2.moveTo(partie.getWidth() / 2, 0);
        ligne2.lineTo(mouseX - munition.getWidth(), mouseY);
        g2d.setStroke(new BasicStroke(5));
        GradientPaint gp = new GradientPaint(colorX, colorX, Color.yellow, colorY, colorX, Color.cyan, true);
        g2d.setPaint(gp);
        g2d.draw(ligne2);
        g2d.setStroke(new BasicStroke(1));
        g2d.setPaint(null);
        g2d.setColor(Color.lightGray);

        Arc2D.Double arc2 = new Arc2D.Double(partie.getWidth() / 2 - widthBase / 2, -heightBase / 2, widthBase,
                heightBase, 180, 180, Arc2D.OPEN);

        BufferedImage img = new BufferedImage(150,150,BufferedImage.TYPE_INT_RGB);
        try {
            img = ImageIO.read(new File("ressources/roue.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        g2d.setClip(arc2);
        g2d.drawImage(img, partie.getWidth()/2-85, -85,170,170, partie);
        //g2d.dispose();
        //g2d.draw(arc2);

        try {
            img = ImageIO.read(new File("ressources/canon.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Rectangle rect2 = (new Rectangle(partie.getWidth() / 2 - widthBase / 10, heightBase / 3, widthBase / 5,
                heightBase / 2));

        g2d.rotate(Math.toRadians(90 - angle), partie.getWidth() / 2, 0);
        g2d.setClip(null);
        g2d.drawImage(img, partie.getWidth()/2-85, -55,170,170, partie);
        //g2d.draw(rect2);
        g2d.rotate(Math.toRadians(angle - 90), partie.getWidth() / 2, 0);
        // On annule la rotation après avoir dessiner le rectangle pour que seule le
        // bout du partie rotate

        double theta = Math.toRadians(angle);
        double x = (partie.getWidth() / 2) - (5 * heightBase / 6) * Math.sin(theta) - 10/* Width balle */;
        double y = (5 * heightBase / 6) * Math.cos(theta) - 10/* Height balle */;
        // Pour calculer nouvelles coordonnées de la balle après rotaion

        for (int i = 0; i < controleur.getModele().getNiveau().list_peg.size(); i++) {
            g.fillOval((int) controleur.getModele().getNiveau().list_peg.get(i).getX(),
                    (int) controleur.getModele().getNiveau().list_peg.get(i).getY(),
                    (int) controleur.getModele().getNiveau().list_peg.get(i).rayon,
                    (int) controleur.getModele().getNiveau().list_peg.get(i).rayon);
        }

        g2d.dispose();
    }

    public void calculeAngle() {
        mouseX = MouseInfo.getPointerInfo().getLocation().getX();
        mouseY = MouseInfo.getPointerInfo().getLocation().getY();
        int pointX = munition.getWidth() + partie.getWidth() / 2;
        double angle1 = Math.atan2(mouseY - 0, mouseX - pointX);
        double angle2 = Math.atan2(0, -pointX);
        angle = (int) Math.toDegrees(angle2 - angle1);
    }

    public void afficheMunition() {
        for (int i = 0; i < 10; i++) {
            JPanel panel = new JPanel();
            if (i > nbMunition + 1) { // il reste i + 1 munition
                panel = new JPanel() {
                    @Override
                    public void paint(Graphics g) {
                        super.paint(g);
                        g.fillOval(50, 20, 50, 50);
                    }
                };
            }
            panel.setBackground(Color.white);
            panel.setLayout(new BorderLayout());
            panel.setBorder(BorderFactory.createLineBorder(Color.black));
            munition.add(panel);
        }
    }
}
