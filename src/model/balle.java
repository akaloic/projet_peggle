package model;

import javax.swing.JPanel;

import view.Visuel;

import java.awt.Color;
import java.awt.Graphics;
public class balle extends JPanel{//la balle du joueur
    //attributs
    public int x,y;//ses coordonnées
    double speedX,speedY;//sa vitesse
    // TODO : ajouter les autres attributs

    public void deplacement(){
        if(Visuel.enJeu){
            this.x += 10;// (int)speedX;
            this.y = (int)speedY;
            //TODO : collisions bordures jeu, peg...
        }
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.fillOval(this.x,this.y,10,10);
    }
}

