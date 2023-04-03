package controller;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.MouseInputListener;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.UndoManager;
import java.awt.event.MouseAdapter;
import model.Niveau;
import model.Obstacle;
import model.Pegs;
import model.Quadrilatere;
import view.View;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
public class Edit extends JPanel{
    public ArrayList<Obstacle> niveau;
    public ArrayList<objetMobile> listPanel = new ArrayList<objetMobile>();//Liste pour garder compte des JPanel sur la page
    JPanel principal;
    objetMobile pegsEcran;
    objetMobile objetSelectionner;//Le dernière objet sur lequel on a cliqué
    objetMobile suivant = new objetMobile(null);
    boolean choixMultiple = true;
    public ArrayList<objetMobile> listeSelection = new ArrayList<objetMobile>();
    boolean peutBouger = false;
    JButton save = new JButton("Sauvegarder");
    JButton leave = new JButton("Quitter");
    JButton cancel = new JButton("Annuler");
    JButton redo = new JButton("Redo");
    JButton delete = new JButton("Tout supprimer");
    JPanel espaceSlider = new JPanel(new GridLayout(4,1));
    JSlider taille = new JSlider();
    JSlider tailleGeneral = new JSlider();
    JPanel espaceCoords = new JPanel(new BorderLayout());
    int width;
    int height;
    Selection selection = new Selection();
    UndoManager undoManager = new UndoManager();
    JTextField xSaisie = new JTextField();
    JTextField ySaisie = new JTextField();
    View view;

    public Edit(Niveau n,int widht, int height,int idSauvegarde,View v){
        this.setBackground(Color.gray);
        niveau = Sauvegarde.charge(idSauvegarde);
        this.view = v;
        this.width = widht;
        this.height = height;
        JPanel partieBouton = new JPanel();
        partieBouton.setLayout(new GridLayout(6,1));
        partieBouton.add(save);
        partieBouton.add(leave);
            JPanel sepateur = new JPanel(new GridLayout(1,2));
            sepateur.add(cancel);
            sepateur.add(redo);
        partieBouton.add(sepateur);
        partieBouton.add(delete);
        partieBouton.add(espaceCoords);
        partieBouton.add(espaceSlider);

        espaceSlider.add(new JLabel("Taille unique"));
        espaceSlider.add(taille);
        espaceSlider.add(new JLabel("Taille générale"));
        espaceSlider.add(tailleGeneral);

        taille.setMinimum(20);
        taille.setMaximum(50);
        taille.setMinorTickSpacing(1);
        taille.setMajorTickSpacing(5);
        taille.setPaintTrack(true); 
        taille.setPaintTicks(true); 
        taille.setPaintLabels(true);
        taille.setEnabled(false);
        taille.addChangeListener (( event ) -> { 
            if(objetSelectionner != null && !objetSelectionner.decoration){
                objetSelectionner.obstacle.setRayon(taille.getValue());
                objetSelectionner.setSize(new Dimension((int)(taille.getValue()*View.getRatio()),(int)(taille.getValue()*View.getRatio())));
                for (objetMobile objetMobile : listeSelection) {
                    objetMobile.obstacle.setRayon(taille.getValue());
                    objetMobile.setSize(new Dimension((int)(taille.getValue()*View.getRatio()),(int)(taille.getValue()*View.getRatio())));
                }
            principal.requestFocus();
            }
         });

        tailleGeneral.setMinimum(20);
        tailleGeneral.setMaximum(50);
        tailleGeneral.setMinorTickSpacing(1);
        tailleGeneral.setMajorTickSpacing(5);
        tailleGeneral.setPaintTrack(true); 
        tailleGeneral.setPaintTicks(true); 
        tailleGeneral.setPaintLabels(true);
        tailleGeneral.addChangeListener (( event ) -> { 
            for(int i = 0; i < listPanel.size();i++){
                listPanel.get(i).obstacle.setRayon(tailleGeneral.getValue());
                listPanel.get(i).setSize(new Dimension((int)(tailleGeneral.getValue()*View.getRatio()),(int)(tailleGeneral.getValue()*View.getRatio())));
            }
            principal.requestFocus();
          });

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
                    //Liste pour garder les objet a supprimer, car supprimer des éléments d'un liste qu'on est en train de parcourir est pas très opti
                    for(int i = 0; i < listPanel.size();i++){
                        if(appartient(listPanel.get(i))){
                            listeSelection.add(listPanel.get(i));
                        }
                    }
                    saveChange(listeSelection);//Va aussi remove de niveau, listePanel et principal
                    listeSelection = new ArrayList<objetMobile>();
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
            Obstacle o = niveau.get(i);
            pegsEcran = new objetMobile(niveau.get(i)){
                @Override
                public void paint(Graphics g) {
                    // TODO Auto-generated method stub
                    super.paint(g);
                    o.clone(0, 0, 0, obstacle.getRayon()).dessine(g);
                    if(this == objetSelectionner){
                        if(peutBouger){
                            this.setForeground(Color.cyan);
                        }
                        else{
                            this.setForeground(Color.red);
                        }
                    }
                    else{
                        if(listeSelection.contains(this)){
                            this.setForeground(Color.green);
                        }
                        else{
                            this.setForeground(Color.black);
                        }
                    } 

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
                pegsEcran.setBounds((int)(o.getX()*View.ratioX), (int)(o.getY()*View.ratioY), (int)(o.getRayon()*View.getRatio()), (int)(o.getRayon()*View.getRatio()));
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
        objetMobile pegRond = new objetMobile(new Pegs(0, 0,0,20)){
            @Override
            public void paint(Graphics g) {
                // TODO Auto-generated method stub
                super.paint(g);
                g.setColor(Color.yellow);
                obstacle.dessine(g);
            }
            @Override
            public void setLocation(int x, int y) {
                // TODO Auto-generated method stub
                super.setLocation(x, y);
                xSaisie.setText(this.getX()+"");
                ySaisie.setText(this.getY()+"");
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                specialDecoration(e);
            }
        };
        pegRond.setBounds(50,750,(int)(20*View.getRatio()),(int)(20*View.getRatio()));
        pegRond.setOpaque(false);
        pegRond.decoration = true;
        principal.add(pegRond);
        principal.addMouseListener(pegRond);
        principal.addMouseMotionListener(pegRond);
        principal.addMouseListener(selection);
        principal.addMouseMotionListener(selection);

        //Peg qui servira à créer d'autre peg carré
        objetMobile pegRect = new objetMobile(new Quadrilatere(0, 0, 20, 20)){
            @Override
            public void paint(Graphics g) {
                // TODO Auto-generated method stub
                super.paint(g);
                g.setColor(Color.yellow);
                obstacle.dessine(g);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                specialDecoration(e);
            }
        };
        pegRect.setBounds(80,750,(int)(20*View.getRatio()),(int)(20*View.getRatio()));
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
                    objetSelectionner.obstacle.setX(objetSelectionner.getX()/View.ratioX);
                }else{
                    suivant = plusProche(3);//Gauche
                    objetSelectionner = suivant;
                }
              }
              if (keyCode == KeyEvent.VK_D) {
                if(objetSelectionner != null && (peutBouger || objetSelectionner.decoration)){
                    objetSelectionner.setLocation(objetSelectionner.getX()+10, objetSelectionner.getY());
                    objetSelectionner.obstacle.setX(objetSelectionner.getX()/View.ratioX);
                }else{
                    suivant = plusProche(1);//Droite
                    objetSelectionner = suivant;
                }
              }
              if (keyCode == KeyEvent.VK_Z) {
                if(objetSelectionner != null && (peutBouger || objetSelectionner.decoration)){
                    objetSelectionner.setLocation(objetSelectionner.getX(), objetSelectionner.getY()-10);
                    objetSelectionner.obstacle.setY(objetSelectionner.getY()/View.ratioY);
                }else{
                    suivant = plusProche(0);//Haut
                    objetSelectionner = suivant;
                }
              }
              if (keyCode == KeyEvent.VK_S) {
                if(objetSelectionner != null && (peutBouger || objetSelectionner.decoration)){
                    objetSelectionner.setLocation(objetSelectionner.getX(), objetSelectionner.getY()+10);
                    objetSelectionner.obstacle.setY(objetSelectionner.getY()/View.ratioY);
                }
                else{
                    suivant = plusProche(2);//Bas
                    objetSelectionner = suivant;
                }
              }
              if (keyCode == KeyEvent.VK_ENTER) {
                if(objetSelectionner != null){
                    taille.setEnabled(true);
                    peutBouger = !peutBouger;
                }
              }
              if (keyCode == KeyEvent.VK_W) {
                if(peutBouger){
                    niveau.remove(objetSelectionner.obstacle);
                    listPanel.remove(objetSelectionner);
                    principal.remove(objetSelectionner);
                    Obstacle o = pegRond.obstacle.clone(objetSelectionner.getX()/View.getRatioX(), objetSelectionner.getY()/View.getRatioY(), 20, pegRond.obstacle.getRayon());
                    objetSelectionner = creeObstacle(o,(int)(objetSelectionner.obstacle.getX()*View.getRatioX()), (int)(objetSelectionner.obstacle.getY()*View.getRatioY()), 0, 0);
                    objetSelectionner.deplacement = false;
                }else{
                    Obstacle o = pegRond.obstacle.clone(objetSelectionner.getX()/View.ratioX, objetSelectionner.getY()/View.ratioY, (int)pegRond.obstacle.getRayon(), (int)pegRond.obstacle.getRayon());
                    objetSelectionner = creeObstacle(o, (int)(objetSelectionner.obstacle.getX()*View.getRatioX()), (int)(objetSelectionner.obstacle.getY()*View.getRatioY()), 0, 0);
                    peutBouger = true;
                }
              }
              if (keyCode == KeyEvent.VK_X) {
                if(peutBouger){
                    niveau.remove(objetSelectionner.obstacle);
                    listPanel.remove(objetSelectionner);
                    principal.remove(objetSelectionner);
                    Obstacle o = pegRect.obstacle.clone(objetSelectionner.getX()/View.getRatioX(), objetSelectionner.getY()/View.getRatioY(), 20, pegRect.obstacle.getRayon());
                    objetSelectionner = creeObstacle(o,(int)(objetSelectionner.obstacle.getX()*View.getRatioX()), (int)(objetSelectionner.obstacle.getY()*View.getRatioY()), 0, 0);
                    objetSelectionner.deplacement = false;
                }else{
                    Obstacle o = pegRect.obstacle.clone(objetSelectionner.getX()/View.ratioX, objetSelectionner.getY()/View.ratioY, (int)pegRect.obstacle.getRayon(), (int)pegRect.obstacle.getRayon());
                    objetSelectionner = creeObstacle(o, (int)(objetSelectionner.obstacle.getX()*View.getRatioX()), (int)(objetSelectionner.obstacle.getY()*View.getRatioY()), 0, 0);
                    peutBouger = true;
                }
              }
              if (keyCode == KeyEvent.VK_N) {
                if(choixMultiple){
                    if(!listeSelection.contains(objetSelectionner)){
                        listeSelection.add(objetSelectionner);
                    }else{
                        listeSelection.remove(objetSelectionner);
                    }
                }
              }
              if (keyCode == KeyEvent.VK_M) {
                choixMultiple = !choixMultiple;
              }
            }
        });
        objetSelectionner = new objetMobile(new Obstacle(0));

        
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

    public objetMobile creeObstacle(Obstacle o, int eX, int eY, int xClick, int yClick){
        niveau.add(o);
        objetMobile om = new objetMobile(o){
            @Override
            public void paint(Graphics g) {
                // TODO Auto-generated method stub
                super.paint(g);
                obstacle.clone(0, 0, 0, obstacle.getRayon()).dessine(g);
                if(this == objetSelectionner){
                    if(peutBouger){
                        this.setForeground(Color.cyan);
                    }
                    else{
                        this.setForeground(Color.red);
                    }
                }
                else{
                    if(listeSelection.contains(this)){
                        this.setForeground(Color.green);
                    }
                    else{
                        this.setForeground(Color.black);
                    }
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
        om.setBounds(((eX-xClick)), (eY-yClick), (int)(om.obstacle.getRayon()*View.getRatio()), (int)(om.obstacle.getRayon()*View.getRatio()));
        principal.add(om);
        listPanel.add(om);
        principal.addMouseListener(om);
        principal.addMouseMotionListener(om);;
        om.xClick = xClick;
        om.yClick = yClick;
        return om;

    }
    
    

    public class objetMobile extends JPanel implements MouseInputListener{
        Obstacle obstacle;        
        boolean deplacement = false;
        boolean decoration = false;//Pour différence les pegs pour éditer et les pegs des niveaux
        int xClick;
        int yClick;

        public objetMobile(Obstacle o){
            this.obstacle = o;
        }

        public void specialDecoration(MouseEvent e){
            if(e.getX() >= this.getX() && e.getX() <= this.getX()+this.getWidth()
            && e.getY() >= this.getY() && e.getY() <= this.getY()+this.getHeight()){
                xClick = e.getX()-this.getX();
                yClick = e.getY()-this.getY();
                if (peutBouger){
                    niveau.remove(objetSelectionner.obstacle);
                    listPanel.remove(objetSelectionner);
                    principal.remove(objetSelectionner);
                    Obstacle o = obstacle.clone(objetSelectionner.getX()/View.getRatioX(), objetSelectionner.getY()/View.getRatioY(), 20, obstacle.getRayon());
                    objetSelectionner = creeObstacle(o,(int)(objetSelectionner.obstacle.getX()*View.getRatioX()), (int)(objetSelectionner.obstacle.getY()*View.getRatioY()), 0, 0);
                    objetSelectionner.deplacement = false;
                }else{
                    Obstacle o = obstacle.clone((e.getX()-xClick)/View.ratioX, (e.getY()-yClick)/View.ratioY, 20, obstacle.getRayon());
                    objetSelectionner = creeObstacle(o, e.getX(), e.getY(), xClick, yClick);
                    objetSelectionner.deplacement = true;
                }
        }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getX() >= this.getX() && e.getX() <= this.getX()+this.getWidth()
                && e.getY() >= this.getY() && e.getY() <= this.getY()+this.getHeight()){
                xClick = e.getX()-this.getX();
                yClick = e.getY()-this.getY();
                if(e.getButton() == 1){
                    if(!deplacement){
                        deplacement = true;
                        objetSelectionner = this;
                        taille.setEnabled(true);
                        taille.setValue((int)objetSelectionner.obstacle.getRayon());
                    }else{
                        deplacement = false;
                        obstacle.setX((e.getX()-xClick)/View.ratioX);obstacle.setY((e.getY()-yClick)/View.ratioY);
                    }  
                }else if(e.getButton() == 3){
                    if(!listeSelection.contains(this)){
                        listeSelection.add(this);
                    }else{
                        listeSelection.remove(this);
                    }
                }
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
                //liste.get(i).setForeground(Color.black);
                listPanel.add(liste.get(i));
                principal.add(liste.get(i));
                niveau.add(liste.get(i).obstacle);
            }
        }

        public void doit(){
            for(int i = 0; i < liste.size();i++){
                principal.remove(liste.get(i));
                listPanel.remove(liste.get(i));
                niveau.remove(liste.get(i).obstacle);
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