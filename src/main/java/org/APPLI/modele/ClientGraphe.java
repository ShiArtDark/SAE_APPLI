package org.APPLI.modele;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Timer;

public class ClientGraphe {
    
    public static void main(String[] args) {
         try {
            
             

                int i = 0;
                for (i = 0; i<9;i++) {

                    String scenario = "scenario_"+i+".txt";
                    TreeMap<Sommet, ArrayList<Sommet>> map = FileManager.toGraph(scenario);
                    Graphe graphe = new Graphe(map);
                    
                    
                    // ArrayList<Sommet> chemin = graphe.triTopologique(Graphe.MAX_DISTANCE);
                    // ArrayList<Sommet> cheminDist = graphe.triTopologique(Graphe.DISTANCE);
                    
                    
                    // System.out.println("Scenario FIR"+i+" : "+ graphe.GetDistance(chemin));
                    // System.out.println("Scenario DIS"+i+" : "+ graphe.GetDistance(cheminDist));
                   
                    
                    graphe.triTopologiqueRecursif(5);
                    TreeMap<Integer, ArrayList<ArrayList<Sommet>>> paths = graphe.getAllSolutions();
                    int firstKey = paths.firstKey();
                    // System.out.println(graphe.getAllSolutions());
                    System.out.println("Scenario DIS"+i+" : "+firstKey+" en "+graphe.getIteration()+"\n");
                    // System.out.println("Scenario "+i+"\nChemin :"+graphe.getAllSolutions().size()+"\nOptim ?"+graphe.getAllSolutions().get(graphe.getAllSolutions().firstKey()));

                    System.out.println(graphe.calculeDesSolutions(5));
                    System.out.println(graphe.convertChemin(graphe.getAllSolutions().get(firstKey).get(0)));
                 }
                
            
           
        
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

 
    }
}
