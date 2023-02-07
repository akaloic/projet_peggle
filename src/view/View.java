package view;

import model.*;
import model.sousObjet.*;
import controller.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class View extends JFrame implements MouseInputListener {

    public boolean enJeu = Visuel.enJeu;
    private JPanel balle;
    private JPanel canon;
    private JPanel[] obstacles;
    private JPanel fond;
    private JPanel munition;
    private JPanel partie;
    private JButton leave = new JButton("Fermer");
    private boolean enJeu = true;
    private int angle = 0;
    private Controleur controleur;

    /* Variable balle */
    protected double xballSpeed = 10; // vitesse de la balle en abscisse
    protected double yballSpeed = 10; // vitesse de la balle en ordonnees
    protected double xPos, yPos; // et position de la balle
    protected double max_x, max_y; // pour permettre a la balle de rebondir sur les bords

    /* Variable Canon */

    public View(Controleur controleur) {

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) size.getWidth();
        int height = (int) size.getHeight();
        this.setSize(width, height);

        this.setTitle("Hit the Peggles");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setUndecorated(true);
        this.setVisible(true);// nécessaire sinon this.getHeight et this.getWidth renvoie 0

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
        munition.setPreferredSize(new Dimension(this.getWidth() / 5, this.getHeight()));

        partie = new JPanel(); // Partie du jeu, a droite de la fenetre
        partie.setLayout(new GridBagLayout());
        partie.setBackground(Color.darkGray);
        partie.setPreferredSize(new Dimension(this.getWidth() * 4 / 5, this.getHeight()));
        canon = new JPanel() {
            @Override
            public void paint(Graphics g) {
                // TODO Auto-generated method stub
                /*
                 * BufferedImage img = new BufferedImage(20,50,BufferedImage.TYPE_INT_RGB);
                 * try {
                 * img = ImageIO.read(new File("src/view/pomme.jfif"));
                 * } catch (IOException e) {
                 * // TODO Auto-generated catch block
                 * e.printStackTrace();
                 * }
                 */
                super.paint(g);
                Graphics2D g2d = (Graphics2D) g;

                Arc2D.Double arc2 = new Arc2D.Double(this.getWidth() / 2, -50, 100, 100, 180, 180, Arc2D.OPEN);
                g2d.setClip(arc2);
                // g2d.drawImage(img,this.getWidth()/2,-50,this);
                g2d.draw(arc2);

                Rectangle rect2 = (new Rectangle(this.getWidth() / 2 + (50 - 10)/*
                                                                                 * Rayon du cercle - largeur du
                                                                                 * rectangle divisé par 2
                                                                                 */, 30, 20, 50));
                // Rectangle rect2 = (new
                // Rectangle(this.getWidth()/2+rayonCercle+(canon.largeur/2), canon.y,
                // canon.largeur, canon.hauteur));

                g2d.rotate(Math.toRadians(angle), this.getWidth() / 2 + 45, 0);
                // g2d.rotate(Math.toRadians(canon.angle),this.getWidth()/2+rayonCercle+canon.largeur/4
                // (reste à vérifer),0);

                /*
                 * try {
                 * img = ImageIO.read(new File("src/view/banane.jfif"));
                 * } catch (IOException e) {
                 * // TODO Auto-generated catch block
                 * e.printStackTrace();
                 * }
                 * int x = this.getWidth()/2+(50-10);
                 * int y = 30;
                 */
                g2d.setClip(rect2);
                // g2d.drawImage(img, x, y,20,50, this);
                g2d.draw(rect2);
                g2d.dispose();
            }
        };

        c.anchor = GridBagConstraints.NORTH;
        partie.add(canon, c);
        canon.setPreferredSize(new Dimension(250, 100));

        // /* Initialisation des variables de la balle */
        // this.xPos = this.getWidth()/2-25;
        // this.yPos = canon.getHeight()+20;
        // this.max_x = this.getWidth()-50;
        // this.max_y = this.getHeight()-50;

        // balle = getBallPanel(m.getBalle());
        // partie.add(balle);

        // obstacles = new JPanel[1];
        // JPanel obstacle1 = getObstaclesPanel(new
        // Obstacle((this.getWidth()*4/5)/2-25,canon.getHeight()+100,75,75,0,true,100));
        // System.out.println(obstacle1.getWidth());
        // partie.add(obstacle1);

        /*---- FIN ELEMENTS DU JEU ----*/

        c.anchor = GridBagConstraints.EAST;
        fond.add(munition);
        fond.add(partie, c);

        munition.add(leave, BorderLayout.SOUTH);
        leave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        this.add(fond);
        this.setVisible(true);
        // this.pack();

        while (enJeu) {
            angle += 5;
            canon.repaint();
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        addMouseListener(this);
    }

    // private JPanel getBallPanel(Balle b) {
    // return new JPanel() {
    // @Override
    // public void paint(Graphics g) {
    // Graphics2D g2d = (Graphics2D) g;
    // g2d.setColor(Color.PINK);
    // g2d.fillOval((int)getxPos(), (int)getyPos(),(int) b.getWidth(),
    // (int)b.getHeight());

    // // setxPos(getxPos()+getXballSpeed());
    // // setyPos(getyPos()+getYballSpeed());
    // // System.out.println(getxPos() + " position x " + getyPos() + " position
    // y");

    // try {
    // Thread.sleep(30);
    // } catch (Exception e) {
    // // TODO: handle exception
    // }
    // repaint();
    // }
    // };
    // }
    // private JPanel getObstaclesPanel(Obstacle obs){
    // return new JPanel() {
    // @Override
    // public void paint(Graphics g) {
    // Graphics2D g2d = (Graphics2D) g;
    // g2d.setColor(Color.YELLOW);
    // g2d.fillOval(0, 0, 75,75);
    // }
    // };
    // }

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

    public boolean isEnJeu() {
        return enJeu;
    }

    public void setEnJeu(boolean enJeu) {
        this.enJeu = enJeu;
    }

    public JPanel getBalle() {
        return balle;
    }

    public void setBalle(JPanel balle) {
        this.balle = balle;
    }

    public JPanel getCanon() {
        return canon;
    }

    public void setCanon(JPanel canon) {
        this.canon = canon;
    }

    public JPanel[] getObstacles() {
        return obstacles;
    }

    public void setObstacles(JPanel[] obstacles) {
        this.obstacles = obstacles;
    }

    public JPanel getFond() {
        return fond;
    }

    public void setFond(JPanel fond) {
        this.fond = fond;
    }

    public JPanel getMunition() {
        return munition;
    }

    public void setMunition(JPanel munition) {
        this.munition = munition;
    }

    public JPanel getPartie() {
        return partie;
    }

    public void setPartie(JPanel partie) {
        this.partie = partie;
    }

    public JButton getLeave() {
        return leave;
    }

    public void setLeave(JButton leave) {
        this.leave = leave;
    }

    public double getXballSpeed() {
        return xballSpeed;
    }

    public void setXballSpeed(double xballSpeed) {
        this.xballSpeed = xballSpeed;
    }

    public double getYballSpeed() {
        return yballSpeed;
    }

    public void setYballSpeed(double yballSpeed) {
        this.yballSpeed = yballSpeed;
    }

    public double getxPos() {
        return xPos;
    }

    public void setxPos(double xPos) {
        this.xPos = xPos;
    }

    public double getyPos() {
        return yPos;
    }

    public void setyPos(double yPos) {
        this.yPos = yPos;
    }

    public double getMax_x() {
        return max_x;
    }

    public void setMax_x(double max_x) {
        this.max_x = max_x;
    }

    public double getMax_y() {
        return max_y;
    }

    public void setMax_y(double max_y) {
        this.max_y = max_y;
    }

    public Controleur getControleur() {
        return controleur;
    }

    public void setControleur(Controleur controleur) {
        this.controleur = controleur;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }
}
