package view;

import java.awt.*;
import javax.swing.*;
import model.balle;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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

        JPanel pageMenu = new JPanel();
        this.add(pageMenu);
        pageMenu.setSize(this.getWidth(),this.getHeight());
        pageMenu.setLayout(null);
        initialisationDuJeu(pageMenu);

        while(enJeu){
            b.deplacement();
            // paneJeu.repaint();
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
    public void initialisationDuJeu(JPanel pane){
        JLabel titre = new JLabel("Hit the peggles");
        JButton btn = new JButton("Start");
        titre.setBounds(pane.getWidth()/3,pane.getHeight()/5,100,75);
        btn.setBounds(pane.getWidth()/2-pane.getWidth()/10,titre.getHeight()+pane.getHeight()/5,50,50);
        pane.add(titre);
        pane.add(btn);
    }

}
