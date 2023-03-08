package controller;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.MouseInputListener;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.UndoManager;

import java.awt.event.MouseAdapter;
import model.Niveau;
import model.Pegs;
import model.PegsRect;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
public class Edit extends JPanel{
    public ArrayList<Pegs> niveau;
    public ArrayList<objetMobile> listPanel = new ArrayList<objetMobile>();
    JPanel principal;
    objetMobile p;
    objetMobile objetSelec = new objetMobile(null, 0);
    int forme;
    JButton save = new JButton("Sauvegarder");
    JButton leave = new JButton("Quitter");
    JButton cancel = new JButton("Annuler");
    JButton redo = new JButton("Redo");
    JButton delete = new JButton("Tout supprimer");
    JPanel espaceCoords = new JPanel(new BorderLayout());
    int width;
    int height;
    Selection selection = new Selection();
    UndoManager undoManager = new UndoManager();
    JTextField xSaisie = new JTextField(objetSelec.getX()+"");
    JTextField ySaisie = new JTextField(objetSelec.getY()+"");

    public Edit(Niveau n,int widht, int height){
        new Sauvegarde();
        this.setBackground(Color.gray);
        niveau = Sauvegarde.charge(0);

        this.width = widht;
        this.height = height;

        JPanel partieBouton = new JPanel();
        partieBouton.setLayout(new GridLayout(6,1));
        partieBouton.add(save);
        partieBouton.add(leave);
        partieBouton.add(cancel);
        partieBouton.add(redo);
        partieBouton.add(delete);
        partieBouton.add(espaceCoords);

        JPanel espaceX = new JPanel(new BorderLayout());
        JPanel espaceY = new JPanel(new BorderLayout());
        JButton xPlus = new JButton("+");
        JButton xMoins = new JButton("-");
        JPanel xCase = new JPanel(new GridLayout(2,1));
        JButton yPlus = new JButton("+");
        JButton yMoins = new JButton("-");
        JPanel yCase = new JPanel(new GridLayout(2,1));
        xSaisie.setHorizontalAlignment(JTextField.CENTER);
        ySaisie.setHorizontalAlignment(JTextField.CENTER);
        espaceX.add(xSaisie,BorderLayout.WEST);
        espaceX.add(xCase,BorderLayout.EAST);
        xCase.add(xPlus);
        xCase.add(xMoins);
        espaceY.add(ySaisie,BorderLayout.WEST);
        espaceY.add(yCase,BorderLayout.EAST);
        yCase.add(yPlus);
        yCase.add(yMoins);

        JLabel xtext = new JLabel("Coordonnées X");
        JLabel ytext = new JLabel("Coordonnées Y");

        espaceX.setPreferredSize(new Dimension(50,50));
        espaceY.setPreferredSize(new Dimension(50,50));
        espaceCoords.add(espaceX,BorderLayout.NORTH);
        espaceX.add(xtext,BorderLayout.WEST);
        espaceY.add(ytext,BorderLayout.WEST);
        espaceX.add(xSaisie,BorderLayout.CENTER);
        espaceY.add(ySaisie,BorderLayout.CENTER);
        espaceCoords.add(espaceY,BorderLayout.SOUTH);
        
        xPlus.addActionListener(
            (ActionEvent e) -> {
                objetSelec.setLocation(objetSelec.getX()+1, objetSelec.getY());
        });
        xMoins.addActionListener(
            (ActionEvent e) -> {
                objetSelec.setLocation(objetSelec.getX()-1, objetSelec.getY());
        });
        yPlus.addActionListener(
            (ActionEvent e) -> {
                objetSelec.setLocation(objetSelec.getX(), objetSelec.getY()+1);
   
        });
        yMoins.addActionListener(
            (ActionEvent e) -> {
                objetSelec.setLocation(objetSelec.getX(), objetSelec.getY()-1);
   
        });
        xSaisie.addActionListener(
            (ActionEvent e) -> {
                objetSelec.setLocation(Integer.parseInt(xSaisie.getText()), objetSelec.getY());
   
        });

        ySaisie.addActionListener(
            (ActionEvent e) -> {
                objetSelec.setLocation(objetSelec.getX(), Integer.parseInt(ySaisie.getText()));
   
        });

        save.addActionListener(
            (ActionEvent e) -> {
                Sauvegarde.save(niveau);
        });




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
                        niveau.remove(aSupprimer.get(j).pegs);
                        saveChange(aSupprimer);

                    }
                }

            }
        });
        cancel.addActionListener(
            (ActionEvent e) -> {
                undoManager.undo();
                repaint();
                principal.repaint();
            
        });
        redo.addActionListener(
            (ActionEvent e) -> {
                undoManager.redo();
                repaint();
                principal.repaint();
        });
        this.setLayout(new BorderLayout());
        this.add(partieBouton,BorderLayout.WEST);

        JPanel partieDroite = new JPanel();
        partieDroite.setLayout(new BorderLayout());
        this.add(partieDroite,BorderLayout.CENTER);
        partieBouton.setPreferredSize(new Dimension(widht/5,height));

        principal = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                // TODO Auto-generated method stub
                super.paintComponent(g);
                ((Graphics2D) g).draw(selection.getRectangle());
            }
        };
        for(int i = 0; i < niveau.size();i++){
            int j =i;
            p = new objetMobile(niveau.get(i),0){
                @Override
                public void paint(Graphics g) {
                    // TODO Auto-generated method stub
                    super.paint(g);
                    Pegs.dessine(g, 20, 20);
                }
                @Override
                public void setLocation(int x, int y) {
                    // TODO Auto-generated method stub
                    super.setLocation(x, y);
                    xSaisie.setText(this.getX()+"");
                    ySaisie.setText(this.getY()+"");
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

        objetMobile o = new objetMobile(null,1){
            @Override
            public void paint(Graphics g) {
                // TODO Auto-generated method stub
                super.paint(g);
                g.setColor(Color.yellow);
                Pegs.dessine(g, 20, 20);
            }
            @Override
            public void setLocation(int x, int y) {
                // TODO Auto-generated method stub
                super.setLocation(x, y);
                xSaisie.setText(this.getX()+"");
                ySaisie.setText(this.getY()+"");
            }
        };
        o.setBounds(50,750,20,20);
        o.setOpaque(false);
        o.decoration = true;
        principal.add(o);
        principal.addMouseListener(o);
        principal.addMouseMotionListener(o);
        principal.addMouseListener(selection);
        principal.addMouseMotionListener(selection);

        objetMobile rect = new objetMobile(null,2){
            @Override
            public void paint(Graphics g) {
                // TODO Auto-generated method stub
                super.paint(g);
                g.setColor(Color.yellow);
                PegsRect.dessine(g, 20, 20);
            }
        };
        rect.setBounds(80,750,20,20);
        rect.setOpaque(false);
        rect.decoration = true;
        principal.add(rect);
        principal.addMouseListener(rect);
        principal.addMouseMotionListener(rect);
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
        int id;
        boolean deplacement = false;
        boolean decoration = false;
        int xClick;
        int yClick;

        public objetMobile(Pegs p,int n){
            this.pegs = p;
            this.id = n;

        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getX() >= this.getX() && e.getX() <= this.getX()+this.getWidth()
                && e.getY() >= this.getY() && e.getY() <= this.getY()+this.getHeight()){
                if(!deplacement){
                    deplacement = true;
                    xClick = e.getX()-this.getX();
                    yClick = e.getY()-this.getY();
                    if(!decoration){
                        objetSelec = this;
                    }    
                    forme = id;
                    /*xSaisie.setText(this.getX()+"");
                    ySaisie.setText(this.getY()+"");*/
                }else{
                    deplacement = false;
                }
            }
            if(deplacement && e.getY() < height-150 && decoration){
                System.out.println(forme+"   "+id);
                Pegs p = new Pegs(e.getX()-xClick, e.getY()-yClick, 20, 20);
                this.id = forme;
                objetMobile om = new objetMobile(p,id){
                    @Override
                    public void paint(Graphics g) {
                        // TODO Auto-generated method stub
                        super.paint(g);
                        if (id == 1){
                            Pegs.dessine(g, 20, 20);
                        }
                        else{
                            PegsRect.dessine(g, 20, 20);
                        }
                    }
                    @Override
                    public void setLocation(int x, int y) {
                        // TODO Auto-generated method stub
                        super.setLocation(x, y);
                        xSaisie.setText(this.getX()+"");
                        ySaisie.setText(this.getY()+"");
                    }
                };
                om.setOpaque(false);
                om.setBounds(e.getX()-xClick, e.getY()-yClick, 20, 20);
                principal.add(om);
                listPanel.add(om);
                principal.addMouseListener(om);
                principal.addMouseMotionListener(om);
                niveau.add(p);
            }

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

    public void saveChange(ArrayList<objetMobile> l){
        Coupe c = new Coupe(l);
        c.doit();
        CutEdit cE = new CutEdit(c);
        undoManager.addEdit(cE);
    }

    public class Coupe{
        ArrayList<objetMobile> liste;

        public Coupe(ArrayList<objetMobile> l){
            this.liste = l;
        }

        public void undo(){
            for(int i = 0; i < liste.size();i++){
                listPanel.add(liste.get(i));
                principal.add(liste.get(i));
            }
        }

        public void doit(){
            for(int i = 0; i < liste.size();i++){
                listPanel.remove(liste.get(i));
                principal.remove(liste.get(i));
            }

        }
    }
    
    public class CutEdit extends AbstractUndoableEdit{
        Coupe c;

        public CutEdit(Coupe coupe){
            this.c = coupe;
        }
        public void undo(){c.undo();}

        public void redo(){c.doit();}
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

 