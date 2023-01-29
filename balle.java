import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
public class balle extends JPanel{//la balle du joueur
    //attributs
    int x,y;//ses coordonnées
    double speedX,speedY;//sa vitesse
    // TODO : ajouter les autres attributs

    public void deplacement(){
        this.x += 10;// (int)speedX;
        this.y = (int)speedY;
        //TODO : collisions bordures jeu, peg...
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.setColor(Color.red);
        g.fillOval(this.x,this.y,30,30);
    }
}

