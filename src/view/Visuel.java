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
    balle c = new balle();

    JPanel premierePage = new JPanel();
    JPanel pageActuelle = new JPanel();//Page qui est affiché
    JButton boutton = new JButton("Jouer");

    public Visuel(){
        this.setTitle("Hit the Peggles");
        this.setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        JPanel pageActuelle = premierePage;
        this.getContentPane().add(pageActuelle);
        JPanel secondePage = b;//Page avec la balle
        JPanel troisiemePage = new JPanel();
        troisiemePage.setBackground(Color.BLUE);
        troisiemePage.add(new JPanel());

        secondePage.setLayout(new GridBagLayout());
        //secondePage.add(b);
        premierePage.add(boutton);
        premierePage.setBackground(Color.black);

        b.setBackground(Color.gray);
        
        boutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changePage(secondePage);
            }
        });
        secondePage.setName("a");
        pageActuelle.setName("a");
        while(enJeu){
            if(!secondePage.getName().equals(pageActuelle.getName())){
                b.deplacement();
                b.repaint();
            }
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if(b.x >= 800){
                b.x = 0;
                changePage(troisiemePage);
            }
        }
    }

    public void changePage(JPanel nouvellePage){
        this.getContentPane().remove(pageActuelle);
        this.getContentPane().add(nouvellePage);
        pageActuelle = nouvellePage;
        nouvellePage.setName("P");
        revalidate();
        repaint();
        enJeu = true;
    }

    public static void main(String[] args) {
        new Visuel();
    }
}
