package controller;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.MouseInputListener;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.UndoManager;
import java.awt.event.MouseAdapter;
import model.Niveau;
import model.Pegs;
import model.PegsRect;
import view.View;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
public class Edit extends JPanel{
    public ArrayList<Pegs> niveau;
    public ArrayList<objetMobile> listPanel = new ArrayList<objetMobile>();//Liste pour garder compte des JPanel sur la page
    JPanel principal;
    objetMobile pegsEcran;
    objetMobile objetSelectionner = new objetMobile(null);//Le dernière objet sur lequel on a cliqué
    objetMobile suivant = new objetMobile(null);
    boolean peutBouger = false;
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
    JTextField xSaisie = new JTextField(objetSelectionner.getX()+"");
    JTextField ySaisie = new JTextField(objetSelectionner.getY()+"");
    View view;

    public Edit(Niveau n,int widht, int height,int idSauvegarde,View v){
        new Sauvegarde();
        this.setBackground(Color.gray);
        niveau = Sauvegarde.charge(idSauvegarde);
        this.view = v;
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
        JButton xPlus = new JButton("+");
        JButton xMoins = new JButton("-");
        JPanel xCase = new JPanel(new GridLayout(2,1));
        xSaisie.setHorizontalAlignment(JTextField.CENTER);
        espaceX.add(xSaisie,BorderLayout.WEST);
        espaceX.add(xCase,BorderLayout.EAST);
        xCase.add(xPlus);
        xCase.add(xMoins);
        JLabel xtext = new JLabel("Coordonnées X");
        espaceX.setPreferredSize(new Dimension(50,75));
        espaceX.add(xtext,BorderLayout.WEST);
        espaceX.add(xSaisie,BorderLayout.CENTER);
        espaceCoords.add(espaceX,BorderLayout.NORTH);


        JPanel espaceY = new JPanel(new BorderLayout());
        JButton yPlus = new JButton("+");
        JButton yMoins = new JButton("-");
        JPanel yCase = new JPanel(new GridLayout(2,1));
        ySaisie.setHorizontalAlignment(JTextField.CENTER);
        espaceY.add(ySaisie,BorderLayout.WEST);
        espaceY.add(yCase,BorderLayout.EAST);
        yCase.add(yPlus);
        yCase.add(yMoins);
        JLabel ytext = new JLabel("Coordonnées Y");
        espaceY.setPreferredSize(new Dimension(50,75));
        espaceY.add(ytext,BorderLayout.WEST);
        espaceY.add(ySaisie,BorderLayout.CENTER);
        espaceCoords.add(espaceY,BorderLayout.SOUTH);
        
        xPlus.addActionListener(
            (ActionEvent e) -> {
                objetSelectionner.setLocation(objetSelectionner.getX()+1, objetSelectionner.getY());
        });
        xMoins.addActionListener(
            (ActionEvent e) -> {
                objetSelectionner.setLocation(objetSelectionner.getX()-1, objetSelectionner.getY());
        });
        yPlus.addActionListener(
            (ActionEvent e) -> {
                objetSelectionner.setLocation(objetSelectionner.getX(), objetSelectionner.getY()+1);
        });
        yMoins.addActionListener(
            (ActionEvent e) -> {
                objetSelectionner.setLocation(objetSelectionner.getX(), objetSelectionner.getY()-1);
        });
        xSaisie.addActionListener(
            (ActionEvent e) -> {
                objetSelectionner.setLocation(Integer.parseInt(xSaisie.getText()), objetSelectionner.getY());
        });
        ySaisie.addActionListener(
            (ActionEvent e) -> {
                objetSelectionner.setLocation(objetSelectionner.getX(), Integer.parseInt(ySaisie.getText()));
        });
        save.addActionListener(
            (ActionEvent e) -> {
                Sauvegarde.save(niveau,idSauvegarde);
        });
        leave.addActionListener(
            (ActionEvent e) -> {
                Sauvegarde.save(niveau,idSauvegarde);
                view.changerPanel(view.choixEdit());
        });

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selection != null){
                    ArrayList<objetMobile> aSupprimer = new ArrayList<objetMobile>();
                    //Liste pour garder les objet a supprimer, car supprimer des éléments d'un liste qu'on est en train de parcourir est pas très opti
                    for(int i = 0; i < listPanel.size();i++){
                        if(appartient(listPanel.get(i))){
                            aSupprimer.add(listPanel.get(i));
                        }
                    }
                    for(int j = 0; j < aSupprimer.size();j++){
                        niveau.remove(aSupprimer.get(j).pegs);
                        saveChange(aSupprimer);//Va aussi remove de listePanel et principal
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
        partieBouton.setPreferredSize(new Dimension(widht/7,height));

        principal = new JPanel(){
            @Override
            //Dessine le rectangle quand on maintient la souris
            protected void paintComponent(Graphics g) {
                // TODO Auto-generated method stub
                super.paintComponent(g);
                ((Graphics2D) g).draw(selection.getRectangle());
            }
        };
        for(int i = 0; i < niveau.size();i++){
            pegsEcran = new objetMobile(niveau.get(i)){
                @Override
                public void paint(Graphics g) {
                    // TODO Auto-generated method stub
                    super.paint(g);
                    pegs.dessine(g);
                    
                }
                //Astuce ultime pour mettre à jour les textes dès qu'une coordonnée bouge
                @Override
                public void setLocation(int x, int y) {
                    // TODO Auto-generated method stub
                    super.setLocation(x, y);
                    xSaisie.setText(this.getX()+"");
                    ySaisie.setText(this.getY()+"");
                }
            };
            if(niveau.size() != 0){
                pegsEcran.setBounds((int)(niveau.get(i).getX()*view.ratioX), (int)(niveau.get(i).getY()*view.ratioY), 20, 20);
            }
            pegsEcran.setOpaque(false);
            principal.add(pegsEcran);
            listPanel.add(pegsEcran);
            principal.addMouseListener(pegsEcran);
            principal.addMouseMotionListener(pegsEcran);
        }
        principal.setLayout(null);
        principal.setBackground(Color.lightGray);
        partieDroite.add(principal,BorderLayout.CENTER);

        //Peg qui servira à créer d'autre peg rond
        objetMobile pegRond = new objetMobile(new Pegs(0, 0,0,0)){
            @Override
            public void paint(Graphics g) {
                // TODO Auto-generated method stub
                super.paint(g);
                g.setColor(Color.yellow);
                pegs.dessine(g);
            }
            @Override
            public void setLocation(int x, int y) {
                // TODO Auto-generated method stub
                super.setLocation(x, y);
                xSaisie.setText(this.getX()+"");
                ySaisie.setText(this.getY()+"");
            }
        };
        pegRond.setBounds(50,750,20,20);
        pegRond.setOpaque(false);
        pegRond.decoration = true;
        principal.add(pegRond);
        principal.addMouseListener(pegRond);
        principal.addMouseMotionListener(pegRond);
        principal.addMouseListener(selection);
        principal.addMouseMotionListener(selection);

        //Peg qui servira à créer d'autre peg carré
        objetMobile pegRect = new objetMobile(new PegsRect(0, 0, 0, 0)){
            @Override
            public void paint(Graphics g) {
                // TODO Auto-generated method stub
                super.paint(g);
                g.setColor(Color.yellow);
                pegs.dessine(g);
            }
        };
        pegRect.setBounds(80,750,20,20);
        pegRect.setOpaque(false);
        pegRect.decoration = true;
        principal.add(pegRect);
        principal.addMouseListener(pegRect);
        principal.addMouseMotionListener(pegRect);
        principal.addMouseListener(selection);
        principal.addMouseMotionListener(selection);
        principal.setFocusable(true);
        principal.requestFocus();
        principal.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
              int keyCode = e.getKeyCode();
              if (keyCode == KeyEvent.VK_Q) {
                if(objetSelectionner != null && (peutBouger || objetSelectionner.decoration)){
                    objetSelectionner.setLocation(objetSelectionner.getX()-10, objetSelectionner.getY());
                }else{
                    suivant = plusProche(3);//Gauche
                    objetSelectionner.setForeground(Color.black);
                    objetSelectionner = suivant;
                    objetSelectionner.setForeground(Color.red);
                }
              }
              if (keyCode == KeyEvent.VK_D) {
                if(objetSelectionner != null && (peutBouger || objetSelectionner.decoration)){
                    objetSelectionner.setLocation(objetSelectionner.getX()+10, objetSelectionner.getY());
                }else{
                    suivant = plusProche(1);//Droite
                    objetSelectionner.setForeground(Color.black);
                    objetSelectionner = suivant;
                    objetSelectionner.setForeground(Color.red);
                }
              }
              if (keyCode == KeyEvent.VK_Z) {
                if(objetSelectionner != null && (peutBouger || objetSelectionner.decoration)){
                    objetSelectionner.setLocation(objetSelectionner.getX(), objetSelectionner.getY()-10);
                }else{
                    suivant = plusProche(0);//Haut
                    objetSelectionner.setForeground(Color.black);
                    objetSelectionner = suivant;
                    objetSelectionner.setForeground(Color.red);
                }
              }
              if (keyCode == KeyEvent.VK_S) {
                if(objetSelectionner != null && (peutBouger || objetSelectionner.decoration)){
                    objetSelectionner.setLocation(objetSelectionner.getX(), objetSelectionner.getY()+10);
                }
                else{
                    suivant = plusProche(2);//Bas
                    objetSelectionner.setForeground(Color.black);
                    objetSelectionner = suivant;
                    objetSelectionner.setForeground(Color.red);
                }
              }
              if (keyCode == KeyEvent.VK_ENTER) {
                if(objetSelectionner != null){
                    peutBouger = !peutBouger;
                    objetSelectionner.setForeground(Color.cyan);
                    if(!peutBouger){
                        objetSelectionner.setForeground(Color.red);
                    }
                }
                if(objetSelectionner.decoration){
                    Pegs p = objetSelectionner.pegs.clone(objetSelectionner.getX()/view.ratioX, objetSelectionner.getY()/view.ratioY, 20, 20);
                    creePegs(p, objetSelectionner.getX(), objetSelectionner.getY(), 0, 0);
                }
              }
              if (keyCode == KeyEvent.VK_W) {
                objetSelectionner.setForeground(Color.black);
                objetSelectionner = pegRond;
              }
              if (keyCode == KeyEvent.VK_X) {
                objetSelectionner.setForeground(Color.black);
                objetSelectionner = pegRect;
              }
            }
        });
    }

    public boolean appartient(Component p){
        Rectangle r = selection.getRectangle();
        return p.getX() > r.getX() && p.getX() < r.getX()+r.getWidth() && p.getY() > r.getY() && p.getY() < r.getY()+r.getHeight();
    }

    public objetMobile plusProche(int direction){
        objetMobile voisin = listPanel.get(0);
        for(int i = 0; i < listPanel.size();i++){
            if((distance(listPanel.get(i)) <= distance(voisin)  || distance(voisin) == 0 )&& objetSelectionner != listPanel.get(i)){
                switch(direction){
                    case 0://Haut
                        if(listPanel.get(i).getY() < objetSelectionner.getY()){
                            voisin = listPanel.get(i);
                        }
                        break;
                    case 1://Droite
                        if(listPanel.get(i).getX() > objetSelectionner.getX()){
                            voisin = listPanel.get(i);
                        }
                        break;

                    case 2://Bas
                        if(listPanel.get(i).getY() > objetSelectionner.getY()){
                            voisin = listPanel.get(i);
                        }
                        break;

                    case 3://Gauche
                        if(listPanel.get(i).getX() < objetSelectionner.getX()){
                            voisin = listPanel.get(i);
                        }
                        break;
                }
            }
        }
        return voisin;
    }

    public double distance(objetMobile om){
        return Math.hypot(objetSelectionner.getX()-om.getX(), objetSelectionner.getY()-om.getY());
    }

    public void creePegs(Pegs p, int eX, int eY, int xClick, int yClick){
        niveau.add(p);
        objetMobile om = new objetMobile(p){
            @Override
            public void paint(Graphics g) {
                // TODO Auto-generated method stub
                super.paint(g);
                pegs.dessine(g);
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
        om.setBounds(((eX-xClick)), (eY-yClick), 20, 20);
        principal.add(om);
        listPanel.add(om);
        principal.addMouseListener(om);
        principal.addMouseMotionListener(om);
    }
    
    

    public class objetMobile extends JPanel implements MouseInputListener{
        Pegs pegs;
        boolean deplacement = false;
        boolean decoration = false;//Pour différence les pegs pour éditer et les pegs des niveaux
        int xClick;
        int yClick;

        public objetMobile(Pegs p){
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
                    if(!decoration){//Pour highlight objetSelectionner
                        objetSelectionner.setForeground(Color.BLACK);
                        suivant.setForeground(Color.black);
                        objetSelectionner = this;
                        objetSelectionner.setForeground(Color.red);
                        /*suivant = plusProche(0);
                        suivant.setForeground(Color.green);*/
                    }  
                }else{
                    if(decoration){
                        if(e.getY() > height-150){//Pour redéposer le peg d'edit dans la zone du bas
                            deplacement = false;
                        }
                    }
                    else{
                        deplacement = false;
                    }
                }
            }
            if(deplacement && e.getY() < height-150 && decoration){
                Pegs p = pegs.clone((e.getX()-xClick)/view.ratioX, (e.getY()-yClick)/view.ratioY, 20, 20);
                creePegs(p, e.getX(), e.getY(), xClick, yClick);
            }
            principal.requestFocus();

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
                this.setLocation(e.getXOnScreen()-Edit.this.getInsets().left-Edit.this.getX()-xClick-width/7,e.getYOnScreen()- Edit.this.getInsets().top-Edit.this.getY()-yClick);
            }
            principal.repaint();
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



/*Emploie*
 * Maintenir souris -> espace rectangulaire. Bouton "tout supprimer" supprime tous les pegs qui sont dans le rectangle.
 * Bouton annuler -> annule la suppression des pegs. Bouton redo -> resupprime les pegs
 * Pegs selectionner couleur rouge. Appuyé sur entré pour changer la couleur du peg en rouge ou en bleu
 * Deplacement (Z :Haut, D :Droite, S:Bas, Q: Gauche): -Quand peg rouge, déplace vers le pegs le plus proche dans la direction
 *                                                     -Quand cyan, déplace le pegs dans la direction choisie
 * Pegs jaune: (W :sélectionne le peg rond, X:sélectionne le peg carré): appuyé sur entré pour crée un peg à l'emplacement du peg jaune
 */ 