package controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import model.Obstacle;

public class Sauvegarde {
    public static ArrayList<ArrayList<Obstacle>> liste = new ArrayList<ArrayList<Obstacle>>();

    public static void save(ArrayList<Obstacle> a,int n){
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

    public static ArrayList<Obstacle> charge(int n){
        try {
           FileInputStream fileIn = new FileInputStream("save.ser");
           ObjectInputStream in = new ObjectInputStream(fileIn);
            liste = (ArrayList<ArrayList<Obstacle>>) in.readObject();
           in.close();
           fileIn.close();
        } catch (IOException i) {
           i.printStackTrace();
        } catch (ClassNotFoundException c) {
           c.printStackTrace();
        }
        if(n >= liste.size()){
            ArrayList<Obstacle> a = new ArrayList<>();
            liste.add(a);
        }
        return liste.get(n);
    }
}
