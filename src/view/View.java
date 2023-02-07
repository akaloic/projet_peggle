package view;

import model.*;
import model.sousObjet.*;
import controller.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.MouseInfo;


public class View extends JFrame {

    // public boolean enJeu = true; // Pour mettre le jeu en pose si besoin
    private JPanel balle;
    private JPanel canon;
    private JPanel[] obstacles;
    private JPanel fond;
    private JPanel munition;
    private JPanel partie;
    private JButton leave = new JButton("Fermer");
    private boolean enJeu = true;
    private int angle;

    protected Controleur controleur;
    double mouseX;
    double mouseY;

    public View(Controleur controleur) {

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)size.getWidth();
        int height = (int)size.getHeight();
        this.setSize(width,height);

        this.setTitle("Hit the Peggles");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setUndecorated(true);
        this.setVisible(true);//nécessaire sinon this.getHeight et this.getWidth renvoie 0

        this.controleur = controleur;
        Modele m = controleur.getModele();

        // -------Disposition du jeu-------
        fond = new JPanel(); // represente la fenetre
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
        canon = new JPanel(){
            @Override
            public void paint(Graphics g) {
                // TODO Auto-generated method stub
                /*BufferedImage img = new BufferedImage(20,50,BufferedImage.TYPE_INT_RGB);
                try {
                    img = ImageIO.read(new File("src/view/pomme.jfif"));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }*/
                super.paint(g);
                Graphics2D g2d = (Graphics2D) g;

                Path2D.Double ligne2 = new Path2D.Double();
                ligne2.moveTo(super.getWidth()/2+50, 0);
                ligne2.lineTo(mouseX-munition.getWidth()-canon.getX(), mouseY);
                g2d.draw(ligne2);
                Arc2D.Double arc2 = new Arc2D.Double(this.getWidth()/2, -50, 100, 100, 180, 180,Arc2D.OPEN);
                /*g2d.setClip(arc2);
                g2d.drawImage(img,this.getWidth()/2,-50,this);*/

                g2d.draw(arc2);

                Rectangle rect2 = (new Rectangle(this.getWidth()/2+(50-10)/*Rayon du cercle - largeur du rectangle divisé par 2 */, 30, 20, 50));
                //Rectangle rect2 = (new Rectangle(this.getWidth()/2+rayonCercle+(canon.largeur/2), canon.y, canon.largeur, canon.hauteur));

                g2d.rotate(Math.toRadians(angle),this.getWidth()/2+45,0);
                //g.fillOval(this.getWidth()/2+45, 70, 10, 10);
                //g2d.rotate(Math.toRadians(canon.angle),this.getWidth()/2+rayonCercle+canon.largeur/4 (reste à vérifer),0);

                /*try {
                    img = ImageIO.read(new File("src/view/banane.jfif"));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                int x = this.getWidth()/2+(50-10);
                int y = 30;*/
                /*g2d.setClip(rect2);
                g2d.drawImage(img, x, y,20,50, this);*/
                double theta = Math.toRadians(angle);
                double x = (this.getWidth()/2+45) - 70 * Math.sin(theta);
                double y = 70 * Math.cos(theta) ;
                /*Formule :
                 * newX = centerX + (point2x-centerX)*Math.cos(x) - (point2y-centerY)*Math.sin(x);
                   newY = centerY + (point2x-centerX)*Math.sin(x) + (point2y-centerY)*Math.cos(x)
                 */

                g.fillOval((int)x, (int)y, 10, 10);

                //System.out.println(x+"  "+y);
                g2d.draw(rect2);
                g2d.dispose();
            }
        };
        JLabel a = new JLabel();
        canon.add(a);

        c.anchor =  GridBagConstraints.NORTH;
        partie.add(canon,c);
        canon.setPreferredSize(new Dimension(500,500));

        fond.add(munition, c);
        c.anchor = GridBagConstraints.EAST;
        fond.add(partie, c);

        // -------Disposition du jeu-------

        // -------Elements du jeu-------
        /*
         * balle = getBallPanel(m.getBalle());
         * 
         * //this.add(balle);
         * this.add(new JLabel("test"), 0);
         */
        // -------Elements du jeu-------

        munition.add(leave,BorderLayout.SOUTH);
        leave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        this.add(fond);
        this.setVisible(true);
        //this.pack();

        while(enJeu){
            mouseX = MouseInfo.getPointerInfo().getLocation().getX();
            mouseY = MouseInfo.getPointerInfo().getLocation().getY();
            int pointX = munition.getWidth()+ canon.getX()+canon.getWidth()/2+45;
            double angle1 = Math.atan2(mouseY - 0, mouseX - pointX);
            double angle2 = Math.atan2(this.getHeight() - 0, pointX - pointX);
            a.setText(angle+"");
            angle = (int)Math.toDegrees(angle1-angle2);

            canon.repaint();
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

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

