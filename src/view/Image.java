package view;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Image {
    public static BufferedImage pegRondBleu;
    public static BufferedImage pegRondRose;
    public static BufferedImage pegRondRouge;
    public static BufferedImage quad;
    
    public Image(){
        BufferedImage img = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);
        try {
            img = ImageIO.read(new File("ressources/pegBleu.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        pegRondBleu = img;

        try {
            img = ImageIO.read(new File("ressources/pegRose.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        pegRondRose = img;

        try {
            img = ImageIO.read(new File("ressources/pegRouge.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        pegRondRouge = img;
        try {
            img = ImageIO.read(new File("ressources/quad.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        quad = img;

        
    }
}