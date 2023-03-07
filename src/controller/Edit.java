package controller;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.event.MouseInputListener;
import java.awt.event.MouseAdapter;
import model.Niveau;
import model.Pegs;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
public class Edit extends JPanel{
    public ArrayList<Pegs> niveau;
    public ArrayList<objetMobile> listPanel = new ArrayList<objetMobile>();
    JPanel principal;
    objetMobile p;
    objetMobile objetSelec;
    JButton save = new JButton("Sauvegarder");
    JButton leave = new JButton("Quitter");
    JButton cancel = new JButton("Annuler");
    JButton redo = new JButton("Redo");
    JButton delete = new JButton("Tout supprimer");
    int width;
    int height;
    Selection selection = new Selection();

    public Edit(Niveau n,int widht, int height){
        this.setBackground(Color.gray);
        niveau = n.list_peg;

        this.width = widht;
        this.height = height;

        JPanel partieBouton = new JPanel();
        partieBouton.setLayout(new GridLayout(5,1));
        partieBouton.add(save);
        partieBouton.add(leave);
        partieBouton.add(cancel);
        partieBouton.add(redo);
        partieBouton.add(delete);
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selection != null){
                    ArrayList<objetMobile> aSupprimer = new ArrayList<objetMobile>();
                    for(int i = 0; i < listPanel.size();i++){
                        if(appartient(listPanel.get(i))){
                            aSupprimer.add(listPanel.get(i));
                        }
                    }
                    for(int j = 0; j < aSupprimer.size();j++){
                        listPanel.remove(aSupprimer.get(j));
                        niveau.remove(aSupprimer.get(j).pegs);
                        principal.remove(aSupprimer.get(j));

                    }
                }

            }
        });
        this.setLayout(new BorderLayout());
        this.add(partieBouton,BorderLayout.WEST);

        JPanel partieDroite = new JPanel();
        partieDroite.setLayout(new BorderLayout());
        this.add(partieDroite,BorderLayout.CENTER);
        partieBouton.setPreferredSize(new Dimension(widht/5,height));


        /*principal = new JPanel(){
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                for(int i = 0; i < niveau.size();i++){
                    g.fillRect((int)niveau.get(i).getX(), (int)niveau.get(i).getY(), 20, 20);
                }
            }
        };*/
        principal = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                // TODO Auto-generated method stub
                super.paintComponent(g);
                ((Graphics2D) g).draw(selection.getRectangle());
            }
        };
        for(int i = 0; i < niveau.size();i++){
            p = new objetMobile(niveau.get(i)){
                @Override
                public void paint(Graphics g) {
                    // TODO Auto-generated method stub
                    super.paint(g);
                    g.fillOval(0, 0, 20, 20);
                    g.setColor(Color.red);
                }
            };
            p.setOpaque(false);
            p.setBounds((int)niveau.get(i).getX(), (int)niveau.get(i).getY(), 20, 20);
            principal.add(p);
            listPanel.add(p);
            principal.addMouseListener(p);
            principal.addMouseMotionListener(p);
        }
        principal.setLayout(null);
        principal.setBackground(Color.lightGray);
        partieDroite.add(principal,BorderLayout.CENTER);

        objetMobile o = new objetMobile(null);
        principal.add(o);
        principal.addMouseListener(o);
        principal.addMouseMotionListener(o);
        principal.addMouseListener(selection);
        principal.addMouseMotionListener(selection);
    }

    public boolean appartient(Component p){
        //System.out.println(selection.x1+"  "+selection.y1+"  "+selection.x2+"   "+selection.y2);
        Rectangle r = selection.getRectangle();
        return p.getX() > r.getX() && p.getX() < r.getX()+r.getWidth() && p.getY() > r.getY() && p.getY() < r.getY()+r.getHeight();
    }

    public class objetMobile extends JPanel implements MouseInputListener{
        Pegs pegs;
        boolean deplacement = false;
        boolean nouveau = false;
        int xClick;
        int yClick;

        public objetMobile(Pegs p){
            setBounds(50,750,50,50);
            this.pegs = p;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getX() >= this.getX() && e.getX() <= this.getX()+this.getWidth()
                && e.getY() >= this.getY() && e.getY() <= this.getY()+this.getHeight()){
                if(!deplacement){
                    deplacement = true;
                    xClick = e.getX()-this.getX();
                    yClick = e.getY()-this.getY();    
                    objetSelec = this; 
                }else{
                    deplacement = false;
                }
            }
            /*if(deplacement && e.getY() < height-100){
                niveau.add(new Pegs(e.getX()-xClick, e.getY()-yClick, 20, 20));
            }*/

        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mouseDragged(MouseEvent e) {
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            if(deplacement){
                this.setLocation(e.getXOnScreen()-Edit.this.getInsets().left-Edit.this.getX()-xClick-width/5,e.getYOnScreen()- Edit.this.getInsets().top-Edit.this.getY()-yClick);
            }
            principal.repaint();
            p.repaint();
        }
    }
    public class Selection extends MouseAdapter{
        int x1,y1,x2,y2;

        public Rectangle getRectangle(){
            return new Rectangle(Math.min(x1,x2),Math.min(y1,y2),Math.abs(x1-x2),Math.abs(y1-y2));
        }

        public void mousePressed(MouseEvent event){
            this.x1 = this.x2 = event.getX();
            this.y1 = this.y2 = event.getY();
            //cutButton.setEnabled(false);
            repaint();
        }

        public void mouseDragged(MouseEvent event){
            this.x2 = event.getX();
            this.y2 = event.getY();
            //cutButton.setEnabled(true);
            repaint();
            
        }

        public void mouseMouved(MouseEvent event){
        }
    }
}



/*
 * Zone rectangulaire pour sélectionner
 * Undo
 * Redo
 * Choix plusieurs niveaux
 * Finir sauvegarde proprement
 * 
 * Autre:
 * -Alignement
 * ??
 */

 