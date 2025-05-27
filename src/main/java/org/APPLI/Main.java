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
                
                System.out.println("Scenario FIR"+i+" : "+ graphe.GetDistance(chemin));
                System.out.println("Scenario DIS"+i+" : "+ graphe.GetDistance(cheminDist));
                System.out.println("Scenario DEG"+i+" : "+ graphe.GetDistance(cheminDegr)+"\n");
                

                
                // System.out.println(graphe.topologogiqueRecursif(graphe.getEntrant(), graphe.getSource() ,new ArrayList<>()));
            }
            
            String scenario = "scenario_"+0+".txt";
            TreeMap<Sommet, ArrayList<Sommet>> map = FileManager.toGraph(scenario);
            Graphe graphe = new Graphe(map);
            System.out.println((graphe.triTopologiqueRecursif())+"\n");

        
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    
    }
} 