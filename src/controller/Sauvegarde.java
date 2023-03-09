package controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import model.Pegs;

public class Sauvegarde {
    public static ArrayList<ArrayList<Pegs>> liste = new ArrayList<ArrayList<Pegs>>();
    public Sauvegarde(){ 
    }

    public static void save(ArrayList<Pegs> a,int n){
        liste.set(n, a);
        try {
            FileOutputStream fileOut = new FileOutputStream("save.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(liste);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in save.ser");
         } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static ArrayList<Pegs> charge(int n){
        try {
           FileInputStream fileIn = new FileInputStream("save.ser");
           ObjectInputStream in = new ObjectInputStream(fileIn);
            liste = (ArrayList<ArrayList<Pegs>>) in.readObject();
           in.close();
           fileIn.close();
        } catch (IOException i) {
           i.printStackTrace();
        } catch (ClassNotFoundException c) {
           c.printStackTrace();
        }
        if(n >= liste.size()){
            ArrayList<Pegs> a = new ArrayList<>();
            a.add(new Pegs(0, 0, 0, 0));
            liste.add(a);
        }
        return liste.get(n);
    }
}
