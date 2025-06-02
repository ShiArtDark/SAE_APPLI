package org.APPLI.modele;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Timer;

public class ClientGraphe {
    
    public static void main(String[] args) {
         try {
            
             

                int i = 0;
                for (i = 0; i<8;i++) {

                    String scenario = "scenario_"+i+".txt";
                    TreeMap<Sommet, ArrayList<Sommet>> map = FileManager.toGraph(scenario);
                    Graphe graphe = new Graphe(map);
                    
                    
                    // ArrayList<Sommet> chemin = graphe.triTopologique(Graphe.MAX_DISTANCE);
                    // ArrayList<Sommet> cheminDist = graphe.triTopologique(Graphe.DISTANCE);
                    
                    
                    // System.out.println("Scenario FIR"+i+" : "+ graphe.GetDistance(chemin));
                    // System.out.println("Scenario DIS"+i+" : "+ graphe.GetDistance(cheminDist));
                   
                    
                    // System.out.println(graphe.getAllSolutions());
                    // System.out.println("Scenario "+i+"\nChemin :"+graphe.getAllSolutions().size()+"\nOptim ?"+graphe.getAllSolutions().get(graphe.getAllSolutions().firstKey()));

                    ArrayList<ArrayList<Sommet>> maps = graphe.calculeDesSolutions(5);
                    for(ArrayList<Sommet> ch : maps) {
                        System.out.println(graphe.getDistance(ch)+" "+ch);
                    }
                    System.out.println("\n\n\n");
                 }
                
            
           
        
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

 
    }
}
