package org.APPLI.modele;

import java.util.ArrayList;
import java.util.TreeMap;

public class ClientGraphe {
    
    public static void main(String[] args) {
         try {
            
             
            
                int i = 4;
                String scenario = "scenario_"+i+".txt";
                TreeMap<Sommet, ArrayList<Sommet>> map = FileManager.toGraph(scenario);
                Graphe graphe = new Graphe(map);


                // ArrayList<Sommet> chemin = graphe.triTopologique(Graphe.FIRST);
                // ArrayList<Sommet> cheminDist = graphe.triTopologique(Graphe.DISTANCE);
                // ArrayList<Sommet> cheminDegr = graphe.triTopologique(Graphe.DEGREE);
             
          
                // System.out.println("Scenario FIR"+i+" : "+ graphe.GetDistance(chemin));
                // System.out.println("Scenario DIS"+i+" : "+ graphe.GetDistance(cheminDist));
                // System.out.println("Scenario DEG"+i+" : "+ graphe.GetDistance(cheminDegr)+"\n");
                

                
                System.out.println(graphe.triTopologiqueRecursif());
         
            
           
        
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

 
    }
}
