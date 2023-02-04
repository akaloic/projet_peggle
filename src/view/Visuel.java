package view;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

// import controller.main;

public class Visuel extends JFrame {
    public boolean enJeu = true;//Pour mettre le jeu en pose si besoin
    // balle b = new balle();
    public Visuel(){
        JFrame frame = new JFrame("Hit The peggle");
        frame.setSize(800,500);
        frame.setVisible( true );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        JPanel pageMenu = new JPanel();
        frame.add(pageMenu);
        pageMenu.setSize(frame.getWidth(),frame.getHeight());
        pageMenu.setLayout(null);
        initialisationDuJeu(pageMenu);
        while(enJeu){
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
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
