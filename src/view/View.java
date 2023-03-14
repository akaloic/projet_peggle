package view;

import controller.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import model.*;
import model.sousObstacle.ObstacleRebondissant;
import model.sousObstacle.ObstacleRectangulaire;
import model.sousObstacle.PegRond;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.ModuleLayer.Controller;
import java.nio.file.FileSystem;
import java.util.ArrayList;

import javax.sound.midi.Synthesizer;
import javax.sound.sampled.*;

public class View extends JFrame implements MouseInputListener{

    private JLabel puit = new JLabel();
    private JPanel fond = new JPanel();
    private JPanel munition = new JPanel();
    private JPanel fondGauche = new JPanel();
    private JPanel partie = new JPanel();

    private JButton leave;
    private boolean enJeu = true;
    private boolean balleEnJeu = false;
    private int angle;
    private String chemin = System.getProperty("user.dir") + "/ressources/";
    private Timer timer;
    private int directionX = 5;
    private Controleur controleur;
    //private int nbMunition;
    private double mouseX;
    private double mouseY;
    private static int colorX = 25;
    private static int colorY = 15;
    int seconde = 0;
    public double ratioX;
    public double ratioY;

    static Clip son;

    public int width;
    public int height;
    public int numNiveau;

    /* Pour la balle à effacer plus tard */
    int x = 0;
    int y = 0;
    int ballWidth = 20;
    int ballHeight = 20;
    int max_x, max_y;
    int speedX = 10;
    int speedY = 10;
            /*fin balle */


    public View(Controleur controleur) {
        this.controleur = controleur;
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) size.getWidth();
        height = (int) size.getHeight();
        this.setSize(width, height);
        this.setTitle("Hit the Peggles");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setUndecorated(true); // nécessaire sinon this.getHeight et this.getWidth renvoie 0
        this.setVisible(true);

        changerPanel(menuPrincipal());
    }

    public JPanel menuPrincipal(){

        String urlDuSon = "ressources/SonsWav/Accueil.wav";
        LancerMusic(urlDuSon);

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

        JButton edit = new JButton("edit");
        edit.setBounds(width/2-50,height - height/3+200,100,100);
        pane.add(edit);

        start.addActionListener(e->{
            son.stop();
            changerPanel(choixNiveauPane(controleur));
        });

        edit.addActionListener(e->{
            son.stop();
            changerPanel(choixEdit());
        });
        ratioX = (float)width/800;
        ratioY = (float)height/600;

        return pane;

    }

    public JPanel JeuPanel(Controleur controleur){
        Modele m = controleur.getModele();
        Balle b = m.getBalle();
        Obstacle[] o = m.getObstacles();
        Niveau n = m.getNiveau();
        //nbMunition = 4; // Pour le moment on met 10 munitions
        controleur.getModele().getPlayer().setMunitions(4);

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
        partie.setSize(new Dimension(800,600));
        partie.setLayout(new BorderLayout());
        partie.setBackground(Color.darkGray);

        puit = new JLabel(new ImageIcon(chemin + "puit.png"));

        JLabel scoreLabel=new JLabel("Score : " + controleur.getModele().getPlayer().getScore());
        scoreLabel.setBounds(300, 0, 100, 100);
        scoreLabel.setForeground(Color.RED);
        scoreLabel.setFont(new Font("Serif", Font.PLAIN, 20));

        JLabel pseudoLabel=new JLabel(controleur.getModele().getPlayer().getPseudo());
        pseudoLabel.setBounds(1200,0,100,100);
        pseudoLabel.setForeground(Color.RED);
        pseudoLabel.setFont(new Font("Serif", Font.PLAIN, 20));

        partie.add(pseudoLabel);
        partie.add(scoreLabel);


        partie.add(puit);
        JPanel bis = new JPanel(null);
        bis.add(partie);
        fond.add(bis, BorderLayout.CENTER);
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
        this.add(fond);
        this.setVisible(true);       

        partie.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                controleur.tirer(); 
            }
        });
        partie.setSize(new Dimension((int)(partie.getWidth()*ratioX),(int)(partie.getHeight()*ratioY)));
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
        JTextField name=new JTextField("");
        name.setBounds(600, 500, 100, 30);
        choixNiv.add(name);

        precedent.addActionListener(e->{
            this.invalidate();
            son.stop();
            changerPanel(menuPrincipal());
        });
        int xNiv = precedent.getWidth() * 2;
        int yNiv = precedent.getHeight() * 2;
        int wNiv = 100;
        int hNiv = 100;
        for (int i = 1; i < 6; i++) {
            ;
            JButton nameNiv = new JButton("Niveau " + i);
            nameNiv.setBounds(xNiv, yNiv, wNiv, yNiv);

            JButton editI = new JButton("Edit "+i);
            editI.setBounds(xNiv, yNiv+400, wNiv, yNiv);

            xNiv += 2*wNiv;
            choixNiv.add(nameNiv);
            nameNiv.setName("niveau"+i);
            choixNiv.add(editI);
            this.controleur.getModele().setPlayer(new Player(4, name.getText()));
            nameNiv.addActionListener(e->{
                char lettre = nameNiv.getName().charAt(nameNiv.getName().length()-1);
                numNiveau = Integer.parseInt(""+lettre);
                controleur.getModele().setNiveau(new Niveau(numNiveau));
                changerPanel(JeuPanel(this.controleur));
                son.stop();
            });
            int j = i-1;
            editI.addActionListener(e->{
                controleur.getModele().getNiveau().setList(Sauvegarde.charge(j));
                changerPanel(JeuPanel(this.controleur));
                son.stop();
            });
        }
        return choixNiv;
    }
    public void changerPanel(JPanel pane){
        this.invalidate();
        this.getContentPane().removeAll();
        this.setContentPane(pane);
        this.repaint();
        this.revalidate();
    }

    public JPanel choixEdit(){
        JPanel choix = new JPanel(new GridLayout(1,6));
        JButton acceuil = new JButton("acceuil");
        acceuil.addActionListener(
            (ActionEvent e) -> {
                this.invalidate();
                changerPanel(menuPrincipal());
        });
        choix.add(acceuil);
        for(int i= 0; i < 5; i++){
            JButton j = new JButton(i+"");
            int k = i;
            j.addActionListener(
                (ActionEvent e) -> {
                   changerPanel(new Edit(null, width, height,k,this)); 
            });
            choix.add(j);
        }
        return choix;
    }

    public void placePuit() {
        // avec la redimension de l'image plus grande

        puit.setLocation(puit.getX() + directionX, partie.getHeight() / 2);
        if (puit.getX() > partie.getWidth() / 2)
            directionX = -5;
        if (puit.getX() < -partie.getWidth() / 2)
            directionX = 5;
    }

    public void dessineObstacleRect(Graphics g,ObstacleRectangulaire oR){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.PINK);
        g2d.fillRect((int)oR.getX(),(int)oR.getY(), (int)oR.getWidth(),(int)oR.getHeight());
    }
    public void dessineObstacleRebond(Graphics g, ObstacleRebondissant oReb){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.PINK);
        g2d.fillRect((int)oReb.getX(),(int)oReb.getY(), (int)oReb.getWidth(),(int)oReb.getHeight());
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
        Balle fantome = new Balle((double)(partie.getWidth()/2-controleur.getModele().getBalle().getRayon()/2),0d,200d);
        GeneralPath genPath = new GeneralPath();
        for(int i = 0; i < 70; i++){
            fantome.update(180-controleur.getAngleTir(), 0.03*i);
            double a = fantome.getX()+fantome.getRayon()/2;double b = fantome.getY();
            genPath.moveTo(a,b);
            genPath.lineTo(a, b);
        }

        g2d.setStroke(new BasicStroke(5));
        GradientPaint gp = new GradientPaint(colorX, colorX, Color.yellow, colorY, colorX, Color.cyan, true);
        g2d.setPaint(gp);
        g2d.draw(genPath);
        g2d.setStroke(new BasicStroke(1));
        g2d.setPaint(null);
        g2d.setColor(Color.lightGray);

        g.fillOval((int)controleur.getModele().getBalle().getX(), (int)controleur.getModele().getBalle().getY(), 30, 30);

        for (int i = 0; i < controleur.getModele().getNiveau().list_peg.size(); i++) {
            controleur.getModele().getNiveau().list_peg.get(i).dessine(g);
        }

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
            if (i > controleur.getModele().getPlayer().getMunitions() + 1) { // il reste i + 1 munitio
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

    public static JPanel getPartie() {
        return partie;
    }

    public int getAngle() {
        return angle;
    }

    public void setColorX() {
        colorX -= 1 % 25;
    }

    public JPanel getMunition(){
        return this.munition;
    }

    public int getAngle(){
        return this.angle;
    }

    public void setColorX(){
        colorX -= 1 % 25;
    }

    public void setColorY(){
        colorY -= 1 % 25;
    }


    public static void LancerMusic(String url){
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

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    public int getNumNiveau() {
        return numNiveau;
    }

    public static float getRatioX() {
        return ratioX;
    }

    public static float getRatioY() {
        return ratioY;
    }
}
