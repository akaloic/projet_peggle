package model;

import view.Visuel;
public class Balle{//la balle du joueur
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

}

