package view;

import model.*;
import model.sousObjet.*;
import controller.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;


public class View extends JFrame implements MouseInputListener{

    public boolean enJeu = Visuel.enJeu;
    private JPanel balle;
    private JPanel canon;
    private JPanel[] obstacles;
    private JPanel fond;
    private JPanel munition;
    private JPanel partie;
    private JButton leave = new JButton("Fermer");

    /* Variable balle */
    protected double xballSpeed = 10; // vitesse de la balle en abscisse
    protected double yballSpeed = 10; // vitesse de la balle en ordonnees
    protected double xPos,yPos; // et position de la balle 
    protected double max_x,max_y; //pour permettre a la balle de rebondir sur les bords

    /* Variable Canon */

    protected Controleur controleur;

    public View(Controleur controleur) {

        this.setTitle("Hit the Peggles");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setUndecorated(true);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)size.getWidth();
        int height = (int)size.getHeight();
        this.setSize(width,height);
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

        // partie = new JPanel(){
            partie = new viewPartieDroite(this);
        //     @Override
        //     public void paint(Graphics g) {
        //         // TODO Auto-generated method stub
        //         super.paint(g);
        //         try{
        //             Thread.sleep(20);
        //         }catch(Exception e){}
        //         repaint();
        //     }
        // }; // Partie du jeu, a droite de la fenetre
        // partie.setLayout(null);
        // partie.setBackground(Color.darkGray);
        // partie.setPreferredSize(new Dimension(this.getWidth()*4/5,this.getHeight()));

        // /*---- ELEMENTS DU JEU ----
        //  * Canon
        //  * Balle
        //  * Obstacle(s)
        // */
        // canon = new JPanel();
        // canon.setLayout(null);
        // canon.setBounds((this.getWidth()*4/5)/2-25,0,50,100);
        // canon.add(new JLabel("Canon"));
        // partie.add(canon);

        // /* Initialisation des variables de la balle */
        // this.xPos = this.getWidth()/2-25;
        // this.yPos = canon.getHeight()+20;
        // this.max_x = this.getWidth()-50;
        // this.max_y = this.getHeight()-50;
        
        // balle = getBallPanel(m.getBalle());
        // partie.add(balle);

        // obstacles = new JPanel[1];
        // JPanel obstacle1 = getObstaclesPanel(new Obstacle((this.getWidth()*4/5)/2-25,canon.getHeight()+100,75,75,0,true,100));
        // System.out.println(obstacle1.getWidth());
        // partie.add(obstacle1);

        /*---- FIN ELEMENTS DU JEU ----*/
        
        c.anchor = GridBagConstraints.EAST;
        fond.add(munition);
        fond.add(partie, c);

        munition.add(leave,BorderLayout.SOUTH);
        leave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        this.add(fond);
        this.setVisible(true);
        addMouseListener(this);
    }

    // private JPanel getBallPanel(Balle b) {
    //     return new JPanel() {
    //         @Override
    //         public void paint(Graphics g) {
    //             Graphics2D g2d = (Graphics2D) g;
    //             g2d.setColor(Color.PINK);
    //             g2d.fillOval((int)getxPos(), (int)getyPos(),(int) b.getWidth(), (int)b.getHeight());

    //             // setxPos(getxPos()+getXballSpeed());
    //             // setyPos(getyPos()+getYballSpeed());
    //             // System.out.println(getxPos() + " position x " + getyPos() + " position y");

    //             try {
    //                 Thread.sleep(30);
    //             } catch (Exception e) {
    //                 // TODO: handle exception
    //             }
    //             repaint();
    //         }
    //     };
    // }
    // private JPanel getObstaclesPanel(Obstacle obs){
    //     return new JPanel() {
    //         @Override
    //         public void paint(Graphics g) {
    //             Graphics2D g2d = (Graphics2D) g;
    //             g2d.setColor(Color.YELLOW);
    //             g2d.fillOval(0, 0, 75,75);
    //         }
    //     };
    // }
    public static void main(String[] args) {
        View view = new View(new Controleur());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        

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

}

