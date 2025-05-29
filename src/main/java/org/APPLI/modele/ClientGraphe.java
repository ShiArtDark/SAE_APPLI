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
                 ArrayList<Sommet> cheminDist = graphe.triTopologique(Graphe.DISTANCE);
                 ArrayList<Sommet> cheminDegr = graphe.triTopologique(Graphe.MAX_DISTANCE);
             
          
                // System.out.println("Scenario FIR"+i+" : "+ graphe.GetDistance(chemin));
                 System.out.println("Scenario DIS"+i+" : "+ graphe.GetDistance(cheminDist));
                System.out.println("Scenario DEG"+i+" : "+ graphe.GetDistance(cheminDegr)+"\n");
                

                
                graphe.triTopologiqueRecursif();
                TreeMap<Integer, ArrayList<ArrayList<Sommet>>> paths = graphe.getAllSolutions();
                int firstKey = paths.firstKey();
                System.out.println(firstKey+" "+paths.get(firstKey));
                System.out.println(graphe.getIteration());
         
            
           
        
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

 
    }
}
