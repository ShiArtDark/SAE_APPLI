package org.APPLI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import org.APPLI.modele.FileManager;
import org.APPLI.modele.Graphe;
import org.APPLI.modele.Sommet;

public class Main {
    public static void main(String[] args) {
        try {

            TreeMap<Sommet, ArrayList<Sommet>> map = FileManager.toGraph("scenario_1.txt");

            Graphe graphe = new Graphe(map);

            System.out.println(graphe.triTopologique()); 
            System.out.println(graphe.GetDistance(graphe.triTopologique()));
            
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        
    
    }
} 