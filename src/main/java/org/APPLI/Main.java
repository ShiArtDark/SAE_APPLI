package org.APPLI;

import java.util.ArrayList;
import java.util.TreeMap;

import org.APPLI.modele.FileManager;
import org.APPLI.modele.Graphe;
import org.APPLI.modele.Sommet;

public class Main {
    public static void main(String[] args) {
        try {

            TreeMap<Sommet, ArrayList<Sommet>> map = FileManager.toGraph("scenario_0.txt");
            Graphe graphe = new Graphe(map);
            ArrayList<Sommet> chemin = graphe.triTopologique();
            System.out.println(graphe.getEntrant());
          
            System.out.println(chemin);
            System.out.println(graphe.GetDistance(chemin));
        
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        
    
    }
} 