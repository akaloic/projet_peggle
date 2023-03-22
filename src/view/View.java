package view;

import controller.Controleur;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import model.*;
import model.sousObstacle.ObstacleRebondissant;
import model.sousObstacle.ObstacleRectangulaire;
import model.sousObstacle.PegRond;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.sound.sampled.*;

public class View extends JFrame {

    public JLabel puit;
    public JPanel fond;
    public JPanel munition;
    public JPanel fondGauche;
    public JPanel partie;

    public JButton leave;
    public boolean enJeu = true;
    public boolean balleEnJeu = false;
    public int angle;
    public String chemin = System.getProperty("user.dir") + "/ressources/";
    public Timer timer;
    public int directionX = 5;
    public Controleur controleur;
    public int nbMunition;
    public double mouseX;
    public double mouseY;
    public static int colorX = 25;
    public static int colorY = 15;
    int seconde = 0;
    public static float ratioX;
    public static float ratioY;

    static Clip son;

    public int width;
    public int height;
    public int numNiveau;

    public boolean versDroite = true;

    public View(Controleur c) {
        String urlDuSon = "ressources/SonsWav/Accueil.wav";
        LancerMusic(urlDuSon);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) size.getWidth();
        height = (int) size.getHeight();

        setSize(width, height);
        setTitle("Hit the Peggles");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true); // nécessaire sinon getHeight et getWidth renvoie 0
        setVisible(true);
        controleur = c;

        JPanel pane = new JPanel();
        pane.setSize(width, height);
        pane.setLayout(null);
        pane.setBorder(BorderFactory.createTitledBorder("Bienvenue dans notre jeu"));
        add(pane);

        JLabel titrePane = new JLabel("HIT THE PEGGLES");
        titrePane.setBounds(width / 2 - 65, height - height * 2 / 3, 400, 100);
        pane.add(titrePane);
        JButton start = new JButton("START");
        start.setBounds(width / 2 - 50, height - height / 3, 100, 100);
        pane.add(start);
        start.addActionListener(e -> {
            son.stop();
            changerPanel(choixNiveauPane(controleur));
        });
        JeuPanel(controleur);
    }

    public JPanel JeuPanel(Controleur controleur) {
        nbMunition = 4; // provisoire
        controleur.modele.niveau = new Niveau(numNiveau);

        fond = new JPanel();
        fond.setLayout(new BorderLayout());
        // --------------DROITE---------------------
        partie = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                dessineCanon(g);
                drawBall(g);

                PegRond pRond = new PegRond(0, 0);
                // dessinePegRond(g, pRond); // ca marche
                ObstacleRebondissant oRebond = new ObstacleRebondissant(100, 100);
                // dessineObstacleRebond(g, oRebond); // ca marche
                ObstacleRectangulaire oR = new ObstacleRectangulaire(50, 50);
                // dessineObstacleRect(g, oR); // ca marche

                for (int i = 0; i < controleur.getModele().getNiveau().list_peg.size(); i++) {
                    controleur.getModele().getNiveau().list_peg.get(i).dessine(g);
                }
            }
        };
        partie.setSize(new Dimension(800, 600));
        partie.setLayout(null);
        partie.setBackground(Color.darkGray);

        ImageIcon icon = new ImageIcon(chemin + "puit.png");
        Image image = icon.getImage();
        Image nouvelleImage = image.getScaledInstance(icon.getIconWidth() * 2, icon.getIconHeight() * 2,
                Image.SCALE_SMOOTH);
        ImageIcon nouvelleIcone = new ImageIcon(nouvelleImage);
        puit = new JLabel(nouvelleIcone);
        puit.setSize(new Dimension(partie.getWidth() / 8, partie.getHeight() / 3));
        puit.setLocation(0, partie.getHeight() + partie.getHeight() / 3);

        partie.add(puit);
        fond.add(partie, BorderLayout.CENTER);
        // --------------DROITE---------------------

        // --------------GAUCHE---------------------
        fondGauche = new JPanel();
        fondGauche.setLayout(new BorderLayout());
        fondGauche.setBackground(Color.gray);
        fondGauche.setPreferredSize(new Dimension(getWidth() / 7, getHeight()));

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
        fondGauche.add(leave, BorderLayout.SOUTH);

        fond.add(fondGauche, BorderLayout.WEST);
        // --------------GAUCHE---------------------

        add(fond);
        setVisible(true);
        ratioX = (width - munition.getWidth()) / 2000f;
        ratioY = height / 1325f;

        partie.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                controleur.tirer();
            }
        });

        return fond;
    }

    public JPanel choixNiveauPane(Controleur controleur) {
        String url = "ressources/SonsWav/ChoixNiveau.wav";
        LancerMusic(url);
        JPanel choixNiv = new JPanel();
        choixNiv.setBackground(Color.BLUE);
        choixNiv.setLayout(null);
        choixNiv.setSize(width, height);
        JButton precedent = new JButton("Acceuil");
        precedent.setBounds(0, 0, 100, 100);
        choixNiv.add(precedent);
        precedent.addActionListener(e -> {
            invalidate();
            son.stop();
            new View(controleur);
        });
        int xNiv = precedent.getWidth() * 2;
        int yNiv = precedent.getHeight() * 2;
        int wNiv = 100;
        int hNiv = 100;
        for (int i = 1; i < 6; i++) {
            ;
            JButton nameNiv = new JButton("Niveau " + i);
            nameNiv.setBounds(xNiv, yNiv, wNiv, yNiv);
            xNiv += 2 * wNiv;
            choixNiv.add(nameNiv);
            nameNiv.setName("niveau" + i);
            nameNiv.addActionListener(e -> {
                char lettre = nameNiv.getName().charAt(nameNiv.getName().length() - 1);
                numNiveau = Integer.parseInt("" + lettre);
                changerPanel(JeuPanel(controleur));
                son.stop();
            });
        }
        return choixNiv;
    }

    public void changerPanel(JPanel pane) {
        invalidate();
        setContentPane(pane);
        repaint();
        revalidate();
    }

    public void placePuit() {
        if (versDroite) {
            if (puit.getX() + puit.getWidth() >= partie.getWidth()) {
                puit.setLocation(puit.getX() - 5, puit.getY());
                versDroite = false;
            } else {
                puit.setLocation(puit.getX() + 5, puit.getY());
            }
        } else {
            if (puit.getX() <= 0) {
                puit.setLocation(puit.getX() + 5, puit.getY());
                versDroite = true;
            } else {
                puit.setLocation(puit.getX() - 5, puit.getY());
            }
        }
    }

    public void dessinePegRond(Graphics g, PegRond peg) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.PINK);
        g2d.fillOval((int) peg.getX(), (int) peg.getY(), (int) peg.getWidth(), (int) peg.getHeight());
    }

    public void dessineObstacleRect(Graphics g, ObstacleRectangulaire oR) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.PINK);
        g2d.fillRect((int) oR.getX(), (int) oR.getY(), (int) oR.getWidth(), (int) oR.getHeight());
    }

    public void dessineObstacleRebond(Graphics g, ObstacleRebondissant oReb) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.PINK);
        g2d.fillRect((int) oReb.getX(), (int) oReb.getY(), (int) oReb.getWidth(), (int) oReb.getHeight());
    }

    public void dessineCanon(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int widthBase = 150;
        int heightBase = 150;

        // Pour la ligne multicolore
        Path2D.Double ligne2 = new Path2D.Double();
        ligne2.moveTo(partie.getWidth() / 2, 0);
        ligne2.lineTo(mouseX - munition.getWidth(), mouseY);

        Arc2D.Double arc2 = new Arc2D.Double(partie.getWidth() / 2 - widthBase / 2, -heightBase / 2, widthBase,
                heightBase, 180, 180, Arc2D.OPEN);

        BufferedImage img = new BufferedImage(150, 150, BufferedImage.TYPE_INT_RGB);
        try {
            img = ImageIO.read(new File("ressources/roue.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        g2d.setClip(arc2);
        g2d.drawImage(img, partie.getWidth() / 2 - 85, -85, 170, 170, partie);
        // g2d.draw(arc2);

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
        g2d.drawImage(img, partie.getWidth() / 2 - 85, -55, 170, 170, partie);
        // g2d.draw(rect2);
        g2d.rotate(Math.toRadians(angle - 90), partie.getWidth() / 2, 0);
        // On annule la rotation après avoir dessiner le rectangle pour que seule le
        // bout du partie rotate

        double theta = Math.toRadians(angle);
        double x = (partie.getWidth() / 2) - (5 * heightBase / 6) * Math.sin(theta) - 10/* Width balle */;
        double y = (5 * heightBase / 6) * Math.cos(theta) - 10/* Height balle */;
        // Pour calculer nouvelles coordonnées de la balle après rotaion
        Balle fantome = new Balle(600d, 0d, 200d, 180 - controleur.getAngleTir());
        GeneralPath genPath = new GeneralPath();
        for (int i = 0; i < 80; i++) {
            fantome.update();
            double a = fantome.getX() + fantome.getRayon() / 2;
            double b = fantome.getY();
            genPath.moveTo(a, b);
            genPath.lineTo(a, b);
        }

        g2d.setStroke(new BasicStroke(5));
        GradientPaint gp = new GradientPaint(colorX, colorX, Color.yellow, colorY, colorX, Color.cyan, true);
        g2d.setPaint(gp);
        g2d.draw(genPath);
        g2d.setStroke(new BasicStroke(1));
        g2d.setPaint(null);
        g2d.setColor(Color.lightGray);
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
            if (i > nbMunition) { // il reste i + 1 munition
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

    public void drawBall(Graphics g) {
        Graphics g2d = g;
        if (this.controleur.getModele().getBalle() != null) {
            g2d.fillOval((int) (controleur.getModele().getBalle().getX() * ratioX),
                    (int) (controleur.getModele().getBalle().getY() * ratioY),
                    (int) (controleur.getModele().getBalle().getRayon() * ratioX),
                    (int) (controleur.getModele().getBalle().getRayon() * ratioY));
        }
    }

    public JPanel getPartie() {
        return this.partie;
    }

    public int getAngle() {
        return angle;
    }

    public void setColorX() {
        colorX -= 1 % 25;
    }

    public void setColorY() {
        colorY -= 1 % 25;
    }

    public static void LancerMusic(String url) {
        try {

            File ficSon = new File(url);

            if (ficSon.exists()) {
                AudioInputStream audio = AudioSystem.getAudioInputStream(ficSon);
                son = AudioSystem.getClip();
                son.open(audio);
                son.start();
                son.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                System.out.println("fichier introuvable");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getNumNiveau() {
        return this.numNiveau;
    }
}
