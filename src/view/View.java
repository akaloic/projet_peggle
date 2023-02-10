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
    private JPanel[] obstacles;
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
    private Niveau niveau = new Niveau(1);

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
        partie = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                dessineCanon(g);
            }
        };


        
        puit = new JLabel(new ImageIcon(chemin + "puit.png"));
        puit.setLocation(partie.getWidth() / 2 - puit.getWidth() / 2, partie.getHeight() - puit.getHeight());

        partie.setLayout(new BorderLayout());
        partie.setBackground(Color.darkGray);
        partie.add(puit, BorderLayout.SOUTH);
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

        while (enJeu) {
            colorX -= 1 % 25;
            colorY -= 1 % 25;

            partie.repaint();
            calculeAngle();

            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
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
        g2d.setStroke(new BasicStroke(5));
        GradientPaint gp = new GradientPaint(colorX, colorX, Color.yellow, colorY, colorX, Color.cyan, true);
        g2d.setPaint(gp);
        g2d.draw(ligne2);
        g2d.setStroke(new BasicStroke(1));
        g2d.setPaint(null);
        g2d.setColor(Color.lightGray);

        Arc2D.Double arc2 = new Arc2D.Double(partie.getWidth() / 2 - widthBase / 2, -heightBase / 2, widthBase,
                heightBase, 180, 180, Arc2D.OPEN);
        g2d.draw(arc2);

        Rectangle rect2 = (new Rectangle(partie.getWidth() / 2 - widthBase / 10, heightBase / 3, widthBase / 5,
                heightBase / 2));

        g2d.rotate(Math.toRadians(angle), partie.getWidth() / 2, 0);
        g2d.draw(rect2);
        g2d.rotate(Math.toRadians(-angle), partie.getWidth() / 2, 0);
        // On annule la rotation après avoir dessiner le rectangle pour que seule le
        // bout du partie rotate

        double theta = Math.toRadians(angle);
        double x = (partie.getWidth() / 2) - (5 * heightBase / 6) * Math.sin(theta) - 10/* Width balle */;
        double y = (5 * heightBase / 6) * Math.cos(theta) - 10/* Height balle */;
        // Pour calculer nouvelles coordonnées de la balle après rotaion


        for(int i = 0; i <niveau.list_peg.size();i++){
            g.fillOval((int)niveau.list_peg.get(i).getX(), (int)niveau.list_peg.get(i).getY(), (int)niveau.list_peg.get(i).rayon, (int)niveau.list_peg.get(i).rayon);
        }


        g2d.dispose();
    }

    public void calculeAngle() {
        mouseX = MouseInfo.getPointerInfo().getLocation().getX();
        mouseY = MouseInfo.getPointerInfo().getLocation().getY();
        int pointX = munition.getWidth() + partie.getWidth() / 2;
        double angle1 = Math.atan2(mouseY - 0, mouseX - pointX);
        double angle2 = Math.atan2(this.getHeight() - 0, pointX - pointX);
        angle = (int) Math.toDegrees(angle1 - angle2);
    }
}
