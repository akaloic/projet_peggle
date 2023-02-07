package view;

import java.awt.*;
import java.nio.channels.Pipe;

import javax.swing.*;

import model.sousObjet.Balle;
import model.sousObjet.Obstacle;

public class viewPartieDroite extends JPanel{ //partie droite de la fenetre et du jeu
    
    private JPanel canon;
    private JPanel balle;
    private JPanel[] obstacles;

    /* variables de balle */
    private double width , height, xPos,yPos, xSpeed,ySpeed;

    public viewPartieDroite(JFrame frame){
        this.setSize(frame.getWidth()*4/5,frame.getHeight());
        this.setPreferredSize(new Dimension(frame.getWidth()*4/5,frame.getHeight()));
        this.setBackground(Color.darkGray);
        this.setLayout(null);
         
        /* canon */
        canon = new JPanel();
        canon.setBounds(this.getWidth()/2-25,0,50,100);
        this.add(canon);

        /* balle */
        width = 50; height = 50; 
        xPos = this.getWidth()/2-(width/2); 
        yPos = canon.getHeight() + 20;
        xSpeed = 10;  ySpeed = 10;

        // balle = getBallPanel();
        // balle.setBounds((int)xPos,(int)yPos,(int)width,(int)height);
        // this.add(balle);
        // this.repaint();
        // /* Obstacles */

        // JPanel obs = getObstaclePanel(new Obstacle(this.getWidth()/2-37,canon.getHeight()+600,75,75,0,true,100));
        // obs.setBounds((this.getWidth())/2-37,canon.getHeight()+600,75,75);
        // this.add(obs);
        // if(yPos > 230){
        //     System.out.println(yPos);
        //     obs = null;
        //     System.out.println("jai finis");
        // }
        repaint();
    }
    @Override
    public void paint(Graphics g) {
        // TODO Auto-generated method stub
        super.paint(g);
        g.setColor(Color.pink);
        g.fillOval((int)xPos, (int)yPos, (int)width, (int)height);

        
        g.setColor(Color.yellow);
        g.fillOval((int)this.getWidth()/2-32, 600, 75, 75);
        
        if(yPos < 0 || yPos-height > this.getHeight()){
            ySpeed = -ySpeed;
        }

        if(xPos+width > this.getWidth() || xPos < 0 ){
            xSpeed = -xSpeed;
        }

        if(xPos > this.getWidth()/2-32-25 && xPos < this.getWidth()/2-32+25
            && yPos > 600-25 && yPos < 675+25){
            ySpeed = -ySpeed;
            xSpeed = -xSpeed;
        }
        xPos += xSpeed;
        yPos += ySpeed;

        try {
            Thread.sleep(10);
        } catch (Exception e) {
            // TODO: handle exception
        }
        repaint();
    }
    // public JPanel getBallPanel(){
    //     return new JPanel(){
    //         @Override
    //         public void paint(Graphics g) {
    //             super.paint(g);
    //             g.setColor(Color.red);
    //             g.fillOval(0,0,50,50);

    //             // xPos  = xPos + xSpeed;
    //             do{
    //                 yPos = yPos + ySpeed;
    //                 System.out.println(yPos + " yPos");
    //             }while(yPos > 300);
    //             yPos = 0;
    //             System.out.println("finis");
    //             repaint();
    //         }
    //     };
    // }
    // private JPanel getObstaclePanel(Obstacle obs){
    //     return new JPanel(){
    //         @Override
    //         public void paint(Graphics g) {
    //             // TODO Auto-generated method stub
    //             super.paint(g);
    //             g.setColor(Color.BLUE);
    //             g.fillOval((int)obs.getX(), (int)obs.getY(), (int)obs.getWidth(),(int)obs.getHeight());
    //             repaint();
    //         }
    //     };
    // }
}
