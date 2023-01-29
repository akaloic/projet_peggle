import javax.swing.JFrame;

public class Visuel extends JFrame {
    public boolean enJeu = true;//Pour mettre le jeu en pose si besoin
    balle b = new balle();
    public Visuel(){
        this.setTitle("Hit the Peggles");
        this.setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.getContentPane().add(b);
        while(enJeu){
            b.deplacement();
            b.repaint();
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Visuel();
    }
}
