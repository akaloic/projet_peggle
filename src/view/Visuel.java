package view;

import model.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
public class Visuel extends JFrame {
    public static boolean enJeu = true;//Pour mettre le jeu en pause si besoin
    balle b = new balle();


    JPanel premierePage = new JPanel();

    public Visuel(){
        this.setTitle("Hit the Peggles");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        this.setUndecorated(true);
        this.setVisible(true);
        this.setLayout(new BorderLayout());
        this.getContentPane().add(premierePage,BorderLayout.CENTER);
        premierePage.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;
        c.weightx = 1;
        c.weighty = 1;
        JPanel overlayGauche = new JPanel(){
            @Override
            public void setBackground(Color bg) {
                // TODO Auto-generated method stub
                super.setBackground(Color.gray);
            }
        };
        System.out.println(this.getWidth()+"  "+this.getHeight());
        overlayGauche.setPreferredSize(new Dimension(this.getWidth()/5,this.getHeight()));//temporaire
        premierePage.add(overlayGauche,c);
        c.anchor = GridBagConstraints.EAST;
        JPanel paneJeu = new JPanel(){
            @Override
            public void paint(Graphics g){
                super.paint(g);
                g.fillOval(b.x,b.y,10,10);
            }
        };
        paneJeu.setPreferredSize(new Dimension(this.getWidth()*4/5,this.getHeight()));//temporaire
        paneJeu.setBackground(Color.lightGray);
        premierePage.add(paneJeu,c);

        JButton leave = new JButton("Fermer la fenêtre");
        leave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        overlayGauche.setLayout(new BorderLayout());
        overlayGauche.add(leave,BorderLayout.SOUTH);
        paneJeu.setLayout(new GridBagLayout());
        while(enJeu){
            b.deplacement();
            paneJeu.repaint();
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if(b.x >= 800){
                b.x = 0;
                //changePage(p);
            }
        }
    }

    public void changePage(JPanel nouvellePage){
        this.getContentPane().removeAll();
        this.getContentPane().add(nouvellePage);
        revalidate();
        repaint();
        enJeu = true;
    }

    public static void main(String[] args) {
        new Visuel();
    }
}
