package view;

import controller.Controleur;
import controller.Edit;
import controller.Sauvegarde;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Timer;
import java.util.ArrayList;
import java.awt.Image;

import javax.sound.sampled.*;

public class View extends JFrame {

    public JLabel puit = new JLabel();
    public JPanel fond = new JPanel();
    public JPanel munition = new JPanel();
    public JPanel fondGauche = new JPanel();
    public JPanel fondDroite = new JPanel();
    public JPanel partie = new JPanel();

    public JButton leave;
    public boolean enJeu = true;
    public boolean balleEnJeu = false;
    public double angle;
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

        JLabel nameLabel=new JLabel("Pseudo : ");
        nameLabel.setBounds(width/2-60, height-height/2, 50, 30);
        pane.add(nameLabel);
        JTextField nameField=new JTextField("test");
        nameField.setBounds(width/2, height - height/2, 50, 30);
        pane.add(nameField);


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
            controleur.modele.setPlayer(new Player(nameField.getText(),4));
            changerPanel(choixNiveauPane(controleur));
        });

        edit.addActionListener(e->{
            son.stop();
            changerPanel(choixEdit());
        });
        ratioX = (float)(width-width/7*2)/800;
        ratioY = (float)height/600;
        return pane;

    }


    public JPanel JeuPanel(Controleur controleur) {
        nbMunition = 4; // provisoire a remplacer par munition joueur


        ratioX = (float)(width-width/7*2)/800;
        ratioY = (float)height/600;

        fond = new JPanel();
        fond.setLayout(new BorderLayout());
        // --------------CENTRE---------------------
        partie = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                dessineCanon(g);
                drawBall(g);
                for (int i = 0; i < controleur.modele.niveau.list.size(); i++) {
                    controleur.modele.niveau.list.get(i).dessine(g);
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
        puit.setLocation(0, (int)(partie.getHeight()*ratioY)-partie.getHeight()/8);


        partie.add(puit);
        fond.add(partie, BorderLayout.CENTER);
        // --------------CENTRE---------------------

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

        JButton retour = new JButton("Revenir menu");
        retour.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changerPanel(choixNiveauPane(controleur));
            }
        });

        JPanel partieBas = new JPanel(new BorderLayout());
        partieBas.add(leave,BorderLayout.WEST);
        partieBas.add(retour,BorderLayout.EAST);
        fondGauche.add(munition, BorderLayout.CENTER);
        fondGauche.add(partieBas, BorderLayout.SOUTH);

        fond.add(fondGauche, BorderLayout.WEST);
        // --------------GAUCHE---------------------

        // --------------DROITE---------------------
        fondDroite = new JPanel();
        fondDroite.setLayout(new BorderLayout());
        fondDroite.setBackground(Color.gray);
        fondDroite.setPreferredSize(new Dimension(getWidth() / 11, getHeight()));

        fond.add(fondDroite, BorderLayout.EAST);
        // --------------DROITE---------------------

        add(fond);
        setVisible(true);
        ratioX = (float)(width-width/7*2)/800;
        ratioY = (float)height/600;

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
        choixNiv.setBackground(Color.lightGray);
        choixNiv.setLayout(null);
        choixNiv.setSize(width, height);
        JButton precedent = new JButton("Acceuil");
        precedent.setBounds(0, 0, 100, 100);
        choixNiv.add(precedent);
        ratioX = ratioX/8;
        ratioY = ratioY/8;

        precedent.addActionListener(e->{
            this.invalidate();
            son.stop();
            changerPanel(menuPrincipal());
        });
        int xNiv = precedent.getWidth() * 2;
        int yNiv = precedent.getHeight() * 2;
        int wNiv = width/9;
        int hNiv = height/6;
        afficheMiniature(1, choixNiv, height/2-200);
        afficheMiniature(2, choixNiv, height/2);
        
        return choixNiv;
    }


    public void changerPanel(JPanel pane) {
        invalidate();
        setContentPane(pane);
        repaint();
        revalidate();
    }

    public JPanel choixEdit(){
        JPanel choix = new JPanel(null);
        JButton acceuil = new JButton("acceuil");
        acceuil.addActionListener(
            (ActionEvent e) -> {
                this.invalidate();
                changerPanel(menuPrincipal());
        });
        acceuil.setBounds(0,0,100,50);
        choix.add(acceuil);
        ratioX = ratioX/8;
        ratioY = ratioY/8;
        afficheMiniature(3, choix,height/2);
        return choix;
    }

    public void afficheMiniature(int mode,JPanel pane,int hauteur){
        //1 = Niveau imposé
        //2 = Niveau créer soit même
        //3 = menu d'editing
        JPanel bis = new JPanel(null);
        int borne = mode == 1? 5: Math.max(Sauvegarde.liste.size(),1);
        bis.setBounds(width/30, hauteur, width, height/6);
        for(int i= 0; i < borne; i++){
            int k = i;
            JPanel panelPrincipal = new JPanel(new BorderLayout());
            JPanel miniature = new JPanel(null){
                @Override
                public void paint(Graphics g) {
                    // TODO Auto-generated method stub
                    super.paint(g);
                    if(mode != 1){
                        dessineNiveau(g,Sauvegarde.charge(k));
                    }
                    if(mode == 1){
                        controleur.modele.setNiveau(new Niveau(k+1));
                        dessineNiveau(g,controleur.modele.getNiveau().getList());
                    }
                }   
            };
            if(mode == 3){
                JButton supprimer = new JButton("x");
                supprimer.addActionListener(
                    (ActionEvent e) -> {
                        Sauvegarde.liste.remove(k);
                        changerPanel(choixEdit());
                        Sauvegarde.save(null, 0);
                });
                supprimer.setSize(new Dimension(20,20));
                miniature.add(supprimer);
            }
            JButton bouton = new JButton("Edit "+(k+1));
            if(mode == 1){
                bouton = new JButton("Niveau "+(k+1));
            }
            panelPrincipal.add(miniature,BorderLayout.CENTER);
            panelPrincipal.add(bouton,BorderLayout.SOUTH);
            bouton.addActionListener(
                (ActionEvent e) -> {
                    ratioX = (float)(width-width/7*2)/800;
                    ratioY = (float)height/600;
                    if(mode == 1){
                        controleur.modele.setNiveau(new Niveau(k+1));
                        changerPanel(JeuPanel(this.controleur));
                    }
                    if(mode == 2){
                        controleur.modele.setNiveau(new Niveau(1));//Sinon le niveau est pas initialisé
                        controleur.modele.getNiveau().setList(Sauvegarde.charge(k));
                        changerPanel(JeuPanel(this.controleur));    
                    }
                    if(mode == 3){
                        changerPanel(new Edit(null, width, height,k,this)); 
                    }
                    son.stop();
            });
            miniature.setBackground(Color.lightGray);
            miniature.setBorder(BorderFactory.createLineBorder(Color.black));
            panelPrincipal.setBounds(width/30+i*width/6,0,width/8, height/6);
            bis.add(panelPrincipal);
        }
        if(mode == 3){
            JButton ajoute = new JButton("Nouveau");
            ajoute.addActionListener(
                (ActionEvent e) -> {
                    ArrayList<Obstacle> a = new ArrayList<>();
                    Sauvegarde.liste.add(a);
                    Sauvegarde.save(a, borne);
                    changerPanel(choixEdit());
            });
            ajoute.setBounds(width/30+borne*width/6,0,width/8, height/6);
            bis.add(ajoute);
        }
        JScrollPane defile = new JScrollPane(bis, JScrollPane.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        bis.setPreferredSize(new Dimension(width/30+(borne+1)*width/6,height*5));
        defile.setBounds(width/30, hauteur, width, height/5);
        bis.setBackground(Color.lightGray);
        pane.add(defile);
            
    }

    public void placePuit() {
        if (versDroite) {;
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
        Balle fantome = new Balle(partie.getWidth()/2-25, 0d, 500d, 180 - this.angle);
        GeneralPath genPath = new GeneralPath();
        boolean premierRebond = false;
        while(!premierRebond){
            fantome.update();
            double a = fantome.getX()+fantome.diametre/2;double b = fantome.getY();
            for (Obstacle o : controleur.modele.getNiveau().list) {
                if(fantome.collision(o)!=0){
                    fantome.rebond(o);
                    premierRebond = true;
                }
            }
            if(fantome.getY() > height){
                premierRebond = true;
            }
            genPath.moveTo(a,b);
            genPath.lineTo(a, b);
        }
        for(int i = 0; i < 10; i++){
            fantome.update();
            double a = fantome.getX()+fantome.diametre/2;
            double b = fantome.getY();
            for (Obstacle o : controleur.modele.getNiveau().list) {
                fantome.rebond(o);
            }
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
        dessineNiveau(g,controleur.modele.getNiveau().list);

    }

    public void dessineNiveau(Graphics g,ArrayList<Obstacle> l){
        for (int i = 0; i < l.size(); i++) {
            l.get(i).dessine(g);
        }
    }

    public void calculeAngle() {
        mouseX = MouseInfo.getPointerInfo().getLocation().getX();
        mouseY = MouseInfo.getPointerInfo().getLocation().getY();
        int pointX = munition.getWidth() + partie.getWidth() / 2;
        double angle1 = Math.atan2(mouseY - 0, mouseX - pointX);
        double angle2 = Math.atan2(0, -pointX);
        angle = Math.toDegrees(angle2 - angle1);
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
        if (this.controleur.modele.balle != null) {
            g2d.fillOval((int) (controleur.modele.balle.getX()),(int) (controleur.modele.balle.getY()),(int) (controleur.modele.balle.diametre/2),(int) (controleur.modele.balle.diametre/2));
        }
    }

    public JPanel getPartie() {
        return this.partie;
    }

    public double getAngle() {
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

    public void addExplosion(double x, double y) {
        partie.add(new Explosion(x * ratioX, y * ratioY));
    }
    public int getNumNiveau() {return numNiveau;}
    public static double getRatioX() {return ratioX;}
    public static double getRatioY() {return ratioY;}
    public static double getRatio(){return (ratioX+ratioX)/2;}
}
