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
                    
                    
                    ArrayList<Sommet> cheminDist = graphe.triTopologique(Graphe.DISTANCE);
                
                    
                    
                    // System.out.println("Scenario FIR"+i+" : "+ graphe.getDistance(chemin)+chemin.size());
                    System.out.println("Scenario DIS"+i+" : "+ graphe.getDistance(cheminDist) +" "+cheminDist.size() +" "+graphe.getSortant().size());
                    
                   
                    
                    // System.out.println(graphe.getAllSolutions());
                    // System.out.println("Scenario "+i+"\nChemin :"+graphe.getAllSolutions().size()+"\nOptim ?"+graphe.getAllSolutions().get(graphe.getAllSolutions().firstKey()));

                    ArrayList<ArrayList<Sommet>> maps = graphe.calculeDesSolutions(1);
                    System.out.println(graphe.getDistance(maps.get(0))+"\n"+maps);

                    
                 }
                
            
           
        
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

 
    }
}
