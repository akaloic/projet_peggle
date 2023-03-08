package controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import model.Pegs;

public class Sauvegarde {
    
    public Sauvegarde(){

    }

    public static void save(ArrayList<Pegs> a){
        try {
            FileOutputStream fileOut = new FileOutputStream("save.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(a);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in save.ser");
         } catch (IOException i) {
            i.printStackTrace();
         }
    }

    public static ArrayList<Pegs> charge(int n){
        ArrayList<Pegs> e = null;
        try {
           FileInputStream fileIn = new FileInputStream("save.ser");
           ObjectInputStream in = new ObjectInputStream(fileIn);
           e = (ArrayList<Pegs>) in.readObject();
           in.close();
           fileIn.close();
        } catch (IOException i) {
           i.printStackTrace();
        } catch (ClassNotFoundException c) {
           c.printStackTrace();
        }
        return e;
    }
}
