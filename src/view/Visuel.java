package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

// import controller.main;

public class Visuel extends JFrame {
    public boolean enJeu = true;//Pour mettre le jeu en pose si besoin
    // balle b = new balle();
    public Visuel(){
        this.setTitle("Hit the Peggles");
        this.setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        initialisationDuJeu(this);
        // this.getContentPane().add(b);
        while(enJeu){
            // b.deplacement();
            // b.repaint();
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    public void initialisationDuJeu(JFrame frame){
        JPanel panel = new JPanel();
        panel.setSize(frame.getWidth(), frame.getHeight());
        panel.setLayout(null);
        JLabel label = new JLabel("Hit the peggles");
        JButton btn = new JButton("HIT IT!");
        label.setBounds(panel.getWidth()/3, panel.getHeight()/2, 100, 100);
        btn.setBounds(label.getWidth()+label.getWidth()/2, label.getHeight()-label.getHeight()/2, 50, 50);
        panel.add(btn);
        panel.add(label);
        frame.add(panel);
    }

}
