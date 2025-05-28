package org.APPLI;

import java.util.ArrayList;
import java.util.TreeMap;

import org.APPLI.modele.FileManager;
import org.APPLI.modele.Graphe;
import org.APPLI.modele.Sommet;

public class Main {
    public static void main(String[] args) {
        try {
            
         
            
            for (int i =0 ; i < 9; i++) {
                String scenario = "scenario_"+i+".txt";
                TreeMap<Sommet, ArrayList<Sommet>> map = FileManager.toGraph(scenario);
                Graphe graphe = new Graphe(map);
                ArrayList<Sommet> chemin = graphe.triTopologique(Graphe.FIRST);
                ArrayList<Sommet> cheminDist = graphe.triTopologique(Graphe.DISTANCE);
                ArrayList<Sommet> cheminDegr = graphe.triTopologique(Graphe.DEGREE);
                ArrayList<Sommet> chemminDistMax = graphe.triTopologique(Graphe.MAX_DISTANCE);
                
                System.out.println("Scenario FIR"+i+" : "+ graphe.GetDistance(chemin)+chemin);
                System.out.println("Scenario DIS"+i+" : "+ graphe.GetDistance(cheminDist));
                System.out.println("Scenario DIS"+i+" : "+ graphe.GetDistance(chemminDistMax));
                System.out.println("Scenario DEG"+i+" : "+ graphe.GetDistance(cheminDegr)+"\n");
                
                
                
            }
            
            String scenario = "scenario_"+4+".txt";
            TreeMap<Sommet, ArrayList<Sommet>> map = FileManager.toGraph(scenario);
            Graphe graphe = new Graphe(map);
            graphe.triTopologiqueRecursif();
            // System.out.println(graphe.getAllSolutionsSize());

        
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    
    }
} 