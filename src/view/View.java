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
    public JLabel scoreLabel = new JLabel();
    public JPanel fond = new JPanel();
    public JPanel munition = new JPanel();
    public JPanel fondGauche = new JPanel();
    public JPanel fondDroite = new JPanel();
    public JPanel partie = new JPanel();

    public JButton leave;
    public static boolean enJeu = true;
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

    public static int width;
    public static int height;
    public int numNiveau;
    public BufferedImage fondEcran;

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

        changerPanel(choixJoueur());
    }

    public JPanel menuPrincipal() {

        String urlDuSon = "ressources/SonsWav/Accueil.wav";
        LancerMusic(urlDuSon);

        JPanel pane = new JPanel();
        pane.setSize(width, height);
        pane.setLayout(null);
        pane.setBorder(BorderFactory.createTitledBorder("Bienvenue dans notre jeu"));
        pane.setBackground(Color.lightGray);
        add(pane);

        JLabel nameLabel = new JLabel("Pseudo : ");
        nameLabel.setBounds(width / 2 - 60, height - height / 2, 50, 30);
        pane.add(nameLabel);
        JTextField nameField = new JTextField(controleur.modele.getPlayer().getPseudo());
        nameField.setBounds(width / 2, height - height / 2, 50, 30);
        pane.add(nameField);

        JLabel titrePane = new JLabel("HIT THE PEGGLES");
        titrePane.setBounds(width / 2 - 65, height - height * 2 / 3, 400, 100);
        pane.add(titrePane);
        JButton start = new JButton("START");
        start.setFocusPainted(false);
        start.setBackground(new Color(59, 89, 182));
        start.setBounds(width / 2 - 50, height - height / 3, 100, 100);
        pane.add(start);

        JButton edit = new JButton("edit");
        edit.setBounds(width / 2 - 50, height - height / 3 + 200, 100, 100);
        pane.add(edit);
        

        JButton choixJoueur = new JButton("Retour Sélection");
        choixJoueur.setBounds(0,0,300,100);
        pane.add(choixJoueur);

        start.addActionListener(e -> {
            son.stop();
            controleur.modele.getPlayer().setPseudo(nameField.getText());
            Sauvegarde.save(controleur.modele.getPlayer());
            changerPanel(choixNiveauPane(controleur));
        });

        edit.addActionListener(e -> {
            son.stop();
            controleur.modele.getPlayer().setPseudo(nameField.getText());
            changerPanel(choixEdit());
        });

        choixJoueur.addActionListener(e -> {
            son.stop();
            controleur.modele.getPlayer().setPseudo(nameField.getText());
            Sauvegarde.save(controleur.modele.getPlayer());
            changerPanel(choixJoueur());
        });
        resetRatio();
        return pane;

    }

    public JPanel JeuPanel(Controleur controleur) {
        nbMunition = 4; // provisoire a remplacer par munition joueur

        resetRatio();

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

            @Override
            protected void paintComponent(Graphics g) {
                // TODO Auto-generated method stub
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D)g;
                if(fondEcran == null){
                    setBackground(Color.gray);
                }
                g2d.drawImage(fondEcran,0, 0,getWidth(),getHeight(),null);
            }   
        };
        partie.setSize(new Dimension(800, 600));
        partie.setLayout(null);


        ImageIcon icon = new ImageIcon(chemin + "puit.png");
        Image image = icon.getImage();
        Image nouvelleImage = image.getScaledInstance(icon.getIconWidth() * 2, icon.getIconHeight() * 2,
                Image.SCALE_SMOOTH);
        ImageIcon nouvelleIcone = new ImageIcon(nouvelleImage);
        puit = new JLabel(nouvelleIcone);
        puit.setSize(new Dimension(partie.getWidth() / 8, partie.getHeight() / 3));
        puit.setLocation(0, (int) (partie.getHeight() * ratioY) - partie.getHeight() / 8);

        partie.add(puit);
        fond.add(partie, BorderLayout.CENTER);
        // --------------CENTRE---------------------

        // --------------GAUCHE---------------------
        fondGauche = new JPanel();
        fondGauche.setLayout(new BorderLayout());
        fondGauche.setBackground(Color.gray);
        fondGauche.setPreferredSize(new Dimension(getWidth() / 9, getHeight()));

        munition = new JPanel();
        munition.setLayout(new GridLayout(10, 1));
        afficheMunition();

        leave = new JButton("Fermer");
        leave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { 
                if(Sauvegarde.numNiveau == -1){
                    controleur.modele.getPlayer().setScore(numNiveau-1);
                }
                else{
                    controleur.modele.getPlayer().setScore(Sauvegarde.numNiveau);
                }
                controleur.modele.getPlayer().score = 0;
                Sauvegarde.save(controleur.modele.getPlayer());
                System.exit(0);
            }
        });

        JButton retour = new JButton("Revenir menu");
        retour.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(Sauvegarde.numNiveau == -1){
                    controleur.modele.getPlayer().setScore(numNiveau-1);
                    changerPanel(choixNiveauPane(controleur));
                }
                else{
                    controleur.modele.getPlayer().setScore(Sauvegarde.numNiveau);
                    changerPanel(choixEdit());
                }
                controleur.modele.getPlayer().score = 0;
                Sauvegarde.save(controleur.modele.getPlayer());
            }
        });

        JPanel partieBas = new JPanel(new BorderLayout());
        partieBas.add(leave, BorderLayout.WEST);
        partieBas.add(retour, BorderLayout.EAST);
        fondGauche.add(munition, BorderLayout.CENTER);
        fondGauche.add(partieBas, BorderLayout.SOUTH);

        fond.add(fondGauche, BorderLayout.WEST);
        // --------------GAUCHE---------------------

        // --------------DROITE---------------------
        fondDroite = new JPanel();
        fondDroite.setLayout(new BorderLayout());
        fondDroite.setBackground(Color.gray);
        fondDroite.setPreferredSize(new Dimension(getWidth() / 11, getHeight()));

        JPanel info = new JPanel();
        info.setBackground(Color.gray);
        info.setPreferredSize(new Dimension(info.getWidth(), info.getHeight() + 100));
        scoreLabel.setText("Score : " + controleur.modele.getPlayer().score);
        scoreLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        JLabel pseudoLabel = new JLabel("Joueur : " + controleur.modele.player.pseudo);
        pseudoLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        info.add(pseudoLabel, BorderLayout.NORTH);
        info.add(scoreLabel, BorderLayout.SOUTH);
        fondDroite.add(info, BorderLayout.NORTH);

        fond.add(fondDroite, BorderLayout.EAST);
        // --------------DROITE---------------------

        add(fond);
        setVisible(true);
        resetRatio();

        partie.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                controleur.tirer();
            }
        });

        return fond;
    }

    public JPanel choixNiveauPane(Controleur controleur) {
        // controleur.modele.player.score=0;
        String url = "ressources/SonsWav/ChoixNiveau.wav";
        LancerMusic(url);

        JPanel choixNiv = new JPanel();
        choixNiv.setBackground(Color.lightGray);
        choixNiv.setLayout(null);
        choixNiv.setSize(width, height);

        JButton precedent = new JButton("Acceuil");
        precedent.setBounds(0, 0, 100, 100);
        choixNiv.add(precedent);
        ratioX = ratioX / 1;
        ratioY = ratioY / 1;

        JWindow window = new JWindow();
        JPanel panel = new JPanel();
        JTextArea textArea = new JTextArea(5, 20);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setText(
                " Le but de PEGS est de détruire tous les pegs.\n\n Pour cela, vous devez tirer des balles sur les pegs. \n Vous disposez d'un nombre limité de balles. \n Vous pourrez passer au niveau suivant si vous détruisez tous les pegs. \n\n Bonne chance !");
        JButton fermer = new JButton("Fermer");
        fermer.addActionListener(e -> window.dispose());
        panel.add(fermer);
        JButton suivant = new JButton("Suivant");
        suivant.addActionListener(e -> {
            textArea.setText(
                    " Pour tirer, il vous suffit d'appuyer sur la souris.\n\n Important, si vous tirez dans le puit, on vous rajoute une munition.\n Quant aux points, on les calcule ainsi, si vous touchez 3 pegs en un seul tir, vous avez 1+2+3 = 6 points, auquel on ajoute 5 points par peg detruit.\n\n Bonne chance !");
            JButton retour = new JButton("Précédent");
            panel.add(retour);
            panel.remove(suivant);
            panel.revalidate();
            panel.repaint();

            retour.addActionListener(e2 -> {
                textArea.setText(
                        " Le but de PEGS est de détruire tous les pegs.\n\n Pour cela, vous devez tirer des balles sur les pegs. \n Vous disposez d'un nombre limité de balles. \n Vous pourrez passer au niveau suivant si vous détruisez tous les pegs. \n\n Bonne chance !");
                panel.remove(retour);
                panel.add(fermer);
                panel.add(suivant);
                panel.revalidate();
                panel.repaint();
            });
        });
        panel.add(fermer);
        panel.add(suivant);

        window.getContentPane().add(new JLabel("", new ImageIcon(chemin + "loading.gif"), JLabel.CENTER));
        window.setBounds(0, 0, getWidth() / 3, getHeight() / 5);
        window.setLocationRelativeTo(null);
        window.add(textArea, BorderLayout.CENTER);
        window.add(panel, BorderLayout.SOUTH);
        window.setVisible(true);


        precedent.addActionListener(e -> {
            this.invalidate();
            son.stop();
            changerPanel(menuPrincipal());
        });
        afficheMiniature(1, choixNiv);
        //afficheMiniature(2, choixNiv, height / 2);

        return choixNiv;
    }

    public void changerPanel(Container pane) {
        invalidate();
        enJeu = pane.equals(fond);
        setContentPane(pane);
        repaint();
        revalidate();
    }

    public JPanel choixEdit() {
        JPanel choix = new JPanel(null);
        JButton acceuil = new JButton("acceuil");
        acceuil.addActionListener(
                (ActionEvent e) -> {
                    this.invalidate();
                    changerPanel(menuPrincipal());
                });
        acceuil.setBounds(0, 0, 100, 50);
        choix.add(acceuil);
        ratioX = ratioX / 1;
        ratioY = ratioY / 1;
        afficheMiniature(2, choix);
        return choix;
    }

    public void afficheMiniature(int mode, JPanel pane) {
        // 1 = Niveau imposé
        // 2 = Niveau créer soit même
        if(mode == 1){
            Sauvegarde.numNiveau = -1;
        }
        else{
            Sauvegarde.numNiveau = 0;
        }
        JPanel bis = new JPanel(null);
        int borne = mode == 1 ? 5 : Math.max(Sauvegarde.listeJoueurs.get(Sauvegarde.joueur).liste.size(), 1);
        bis.setBounds(width / 30, height/8, width, height / 6);
        for (int i = 0; i < borne; i++) {
            int k = i;
            JPanel panelPrincipal = new JPanel(new BorderLayout());
            JPanel miniature = new JPanel(null) {
                @Override
                public void paint(Graphics g) {
                    // TODO Auto-generated method stub
                    super.paint(g);
                    if (mode != 1) {
                        dessineNiveau(g, Sauvegarde.charge(k));
                    }
                    if (mode == 1) {
                        controleur.modele.setNiveau(new Niveau(k + 1));
                        dessineNiveau(g, controleur.modele.getNiveau().getList());
                    }
                    g.setFont(new Font("TimesRoman", Font.PLAIN, 30)); 
                    if(Sauvegarde.numNiveau == -1){
                        ((Graphics2D)g).drawString("Meilleur score : "+controleur.modele.getPlayer().listeScore[k], 0, 30);
                    }
                    else{
                        ((Graphics2D)g).drawString("Meilleur score : "+controleur.modele.getPlayer().listeScoreEdit.get(k), 0, 30);
                    }
                    
                }

                @Override
                protected void paintComponent(Graphics g) {
                    // TODO Auto-generated method stub
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D)g;
                    /*try {
                        fondEcran = ImageIO.read(new File("ressources/Niveau"+(k+1)+"Fond.png"));
                    } catch (IOException excep) {
                        // TODO Auto-generated catch block
                        excep.printStackTrace();
                    }*/
                    if(fondEcran == null){
                        setBackground(Color.gray);
                    }
                    /*g2d.drawImage(view.Image.boulet,0, 0,getWidth(),getHeight(),null);*/
                }
            };
            if (mode == 2) {
                JButton supprimer = new JButton("X");
                supprimer.addActionListener(
                        (ActionEvent e) -> {
                            Sauvegarde.listeJoueurs.get(Sauvegarde.joueur).liste.remove(k);
                            changerPanel(choixEdit());
                            Sauvegarde.save(null, 0);
                        });
                supprimer.setSize(new Dimension(50, 50));
                miniature.add(supprimer);

                JButton edit = new JButton("E");
                edit.addActionListener(
                        (ActionEvent e) -> {
                            changerPanel(new Edit(null, width, height, k, this));
                        });
                edit.setBounds(width - width/5-50, 0, 50, 50);
                miniature.add(edit);
            }
            JButton bouton = new JButton("Jouer");
            if (mode == 1) {
                bouton = new JButton("Niveau " + (k + 1));
            }
            panelPrincipal.add(miniature, BorderLayout.CENTER);
            panelPrincipal.add(bouton, BorderLayout.SOUTH);
            bouton.addActionListener(
                    (ActionEvent e) -> {
                        resetRatio();
                        if (mode == 1) {
                            numNiveau = k + 1;
                            fondEcran = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);
                            try {
                                fondEcran = ImageIO.read(new File("ressources/Niveau"+numNiveau+"Fond.png"));
                            } catch (IOException excep) {
                                // TODO Auto-generated catch block
                                excep.printStackTrace();
                            }
                            Sauvegarde.numNiveau = -1;
                            controleur.modele.setNiveau(new Niveau(k + 1));
                            changerPanel(JeuPanel(this.controleur));
                        }
                        if (mode == 2) {
                            controleur.modele.setNiveau(new Niveau(1));// Sinon le niveau est pas initialisé
                            controleur.modele.getNiveau().setList(Sauvegarde.charge(k));
                            changerPanel(JeuPanel(this.controleur));
                        }
                        son.stop();
                    });
            miniature.setBackground(Color.lightGray);
            miniature.setBorder(BorderFactory.createLineBorder(Color.black));
            panelPrincipal.setBounds(width / 20 , i * height, width - width/5, height );
            bis.add(panelPrincipal);
        }
        if (mode == 2) {
            JButton ajoute = new JButton("Nouveau");
            ajoute.addActionListener(
                    (ActionEvent e) -> {
                        ArrayList<Obstacle> a = new ArrayList<>();
                        Sauvegarde.listeJoueurs.get(Sauvegarde.joueur).liste.add(a);
                        Sauvegarde.listeJoueurs.get(Sauvegarde.joueur).listeScoreEdit.add(0);
                        Sauvegarde.save(a, borne);
                        changerPanel(choixEdit());
            });
            ajoute.setBounds(width / 20 , borne * height, width - width/5, height);
            bis.add(ajoute);
        }
        JScrollPane defile = new JScrollPane(bis, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        defile.getVerticalScrollBar().setUnitIncrement(30);
        bis.setPreferredSize(new Dimension(width / 30 + (borne + 1) * width / 6, height * (borne+2)));
        defile.setBounds(width / 30, height/8, width, height * 2);
        bis.setBackground(Color.lightGray);
        pane.add(defile);

    }

    public Container choixJoueur(){
        JPanel auxiliaire = new JPanel(null);
        JPanel bis = new JPanel(new GridLayout(Sauvegarde.listeJoueurs.size()+1,1));
        JScrollPane principal = new JScrollPane(bis, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        principal.setBounds(200,100, width - width / 4, height - height / 4);
        bis.setPreferredSize(new Dimension(principal.getWidth(),principal.getHeight()/2 * Sauvegarde.listeJoueurs.size()));

        for(int i = 0; i < Sauvegarde.listeJoueurs.size(); i++){
            JPanel pane = new JPanel(new BorderLayout());
            int k = i;
            JPanel info = new JPanel(null){
                @Override
                public void paint(Graphics g) {
                    // TODO Auto-generated method stub
                    super.paint(g);
                    g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
                    ((Graphics2D)g).drawString("Pseudo : "+Sauvegarde.listeJoueurs.get(k).pseudo, getWidth()/4, getHeight()/4);
                    ((Graphics2D)g).drawString("Nombre de niveau: "+Sauvegarde.listeJoueurs.get(k).liste.size(), getWidth()/4, getHeight()/4*2);
                    ((Graphics2D)g).drawString("Progressions: Niveau 5", getWidth()/4, getHeight()/4*3);
                }     
            };

            JButton choix = new JButton("Joueur "+(k+1));
            choix.addActionListener(
                (ActionEvent e) -> {
                    Sauvegarde.joueur = k;
                    controleur.modele.setPlayer(Sauvegarde.listeJoueurs.get(k));
                    changerPanel(menuPrincipal());
            });

            JButton supprimer = new JButton("X"){
                @Override
                public void paint(Graphics g) {
                    // TODO Auto-generated method stub
                    super.paint(g);
                    setBackground(Color.red);
                }
            };
            supprimer.addActionListener(
                (ActionEvent e) -> {
                    Sauvegarde.listeJoueurs.remove(k);
                    Sauvegarde.save(null);
                    changerPanel(choixJoueur());
            });

            pane.add(choix,BorderLayout.WEST);
            pane.add(info,BorderLayout.CENTER);
            info.add(supprimer);
            info.setBackground(Color.lightGray);

            bis.add(pane);
            supprimer.setBounds(principal.getWidth()-150,0,50,50);
        }

        principal.setBackground(Color.lightGray);

        JButton nouveau = new JButton("Nouvelle sauvegarde");
        nouveau.addActionListener(
            (ActionEvent e) -> {
                Sauvegarde.joueur = Sauvegarde.listeJoueurs.size();
                Sauvegarde.listeJoueurs.add(new Player("Nouveau", 4));
                controleur.modele.getPlayer().listeScoreEdit.add(0);
                Sauvegarde.save(controleur.modele.getPlayer());
                changerPanel(menuPrincipal());
        });
        bis.add(nouveau);


        principal.getVerticalScrollBar().setUnitIncrement(30);
        auxiliaire.add(principal);
        auxiliaire.setBackground(Color.lightGray);
        return auxiliaire;
    }

    public void placePuit() {
        if (versDroite) {
            ;
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

        //Rectangle rect2 = (new Rectangle(partie.getWidth() / 2 - widthBase / 10, heightBase / 3, widthBase / 5,heightBase / 2));

        g2d.rotate(Math.toRadians(90 - angle), partie.getWidth() / 2, 0);
        g2d.setClip(null);
        g2d.drawImage(img, partie.getWidth() / 2 - 85, -55, 170, 170, partie);
        // g2d.draw(rect2);
        g2d.rotate(Math.toRadians(angle - 90), partie.getWidth() / 2, 0);
        // On annule la rotation après avoir dessiner le rectangle pour que seule le
        // bout du partie rotate

        //double theta = Math.toRadians(angle);
        //double x = (partie.getWidth() / 2) - (5 * heightBase / 6) * Math.sin(theta) - 10/* Width balle */;
        //double y = (5 * heightBase / 6) * Math.cos(theta) - 10/* Height balle */;
        // Pour calculer nouvelles coordonnées de la balle après rotaion
        Balle fantome = new Balle(partie.getWidth() / 2 - 25, 0d, 300d, 180 - this.angle);
        GeneralPath genPath = new GeneralPath();
        boolean premierRebond = false;
        while (!premierRebond) {
            fantome.update();
            double a = fantome.getX();
            double b = fantome.getY();
            for (Obstacle o : controleur.modele.getNiveau().list) {
                if (o.collision(fantome)) {
                    o.rebond(fantome);
                    premierRebond = true;
                }
            }
            if (fantome.getY() > height) {
                premierRebond = true;
            }
            genPath.moveTo(a+12.5, b+12.5);
            genPath.lineTo(a+12.5, b+12.5);
        }
        for (int i = 0; i < 10; i++) {
            fantome.update();
            double a = fantome.getX() + fantome.rayon / 2;
            double b = fantome.getY();
            for (Obstacle o : controleur.modele.getNiveau().list) {
                o.rebond(fantome);
            }
            genPath.moveTo(a, b);
            genPath.lineTo(a, b);
        }

        g2d.setStroke(new BasicStroke(5));
        GradientPaint gp = new GradientPaint(colorX, colorX, Color.yellow, colorY, colorX, Color.cyan, true);
        g2d.setPaint(gp);
        g2d.draw(genPath);
        g2d.setStroke(new BasicStroke(1));
        g2d.setPaint(null);
        dessineNiveau(g, controleur.modele.getNiveau().list);

    }

    public void dessineNiveau(Graphics g, ArrayList<Obstacle> l) {
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
                        Graphics2D g2d = (Graphics2D)g;
                        g2d.drawImage(view.Image.boulet,50, 20,50,50,null);
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
        /*if (this.controleur.modele.balle != null) {
            g2d.fillOval((int) (controleur.modele.balle.getX()), (int) (controleur.modele.balle.getY()),
                    (int) (controleur.modele.balle.rayon / 2), (int) (controleur.modele.balle.rayon / 2));
        }*/
        if(this.controleur.modele.balle!=null){
            this.controleur.modele.balle.dessine(g2d);}
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

    public void setScore() {
        scoreLabel.setText("Score : " + controleur.modele.getPlayer().score);
    }

    public int getNumNiveau() {
        return numNiveau;
    }

    public static void resetRatio(){
        ratioX = (float) (width - width / 7 - width / 11) / 800;
        ratioY = (float) height / 600;
    }

    public static double getRatioX() {
        return ratioX;
    }

    public static double getRatioY() {
        return ratioY;
    }

    public static double getRatio() {
        return (ratioX + ratioX) / 2;
    }
}
