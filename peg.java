public class peg {//les obstacles ronds présents dans un nv
    //atributs
    private int x,y;//ses coordonées
    private boolean estTouche;//initié a false, lorsque ce bool est true alors on fait plus attention au peg
    //getters/setters
    public int getX() {return x;}
    public int getY() {return y;}
    public boolean getEstTouche(){return estTouche;}
    public void setEstTouche(boolean nv){estTouche=nv;}
}
