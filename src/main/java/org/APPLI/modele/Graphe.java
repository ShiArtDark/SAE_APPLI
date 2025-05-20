package org.APPLI.modele;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.TreeSet;

public class Graphe {
    // On va passer par des identifiants de villes 

    private TreeMap<Sommet, TreeSet<Sommet>> sortant;
    private TreeMap<Sommet, Integer> entrant;
    private LinkedList<Sommet> source;

    // ======================== CONSTRUCTEUR ========================

    public Graphe (TreeMap<Sommet, ArrayList<Sommet>> _tab) {
        sortant = new TreeMap<>(Sommet::compareTo);
        entrant = new TreeMap<>(Sommet::compareTo);
        source = new LinkedList<>();

        for (Sommet som : _tab.keySet()) {
            entrant.put(som, 0);
        }

        for (Sommet som : _tab.keySet()) {
            TreeSet<Sommet> tempSet = new TreeSet<>(Sommet::compareTo);

            for (Sommet voisin : _tab.get(som)) {
                tempSet.add(voisin);
                entrant.put(voisin, entrant.get(som)+1);
                
            }
            sortant.put(som, tempSet);
        }
        
        for (Sommet som : entrant.keySet()) {
            if(entrant.get(som) == 0) {
                source.add(som);
            }
        }
            
        
    }

    // ======================== GET/SET ========================

    public TreeMap<Sommet, TreeSet<Sommet>> getSortant() {
        return sortant;
    }

    public TreeMap<Sommet, Integer> getEntrant() {
        return entrant;
    }

    public LinkedList<Sommet> getSource() {
        return source;
    }

    // ======================== Method ========================
    

    public ArrayList<Sommet> triTopologique() {
        TreeMap<Sommet, Integer> degreeEntrant = (TreeMap<Sommet, Integer>) entrant.clone();
        LinkedList<Sommet> tempSource = (LinkedList<Sommet>) source.clone();
        ArrayList<Sommet> chemin = new ArrayList<>();

        

        while (!tempSource.isEmpty()) {
            // Une sélection du sommet i qui a la distance la plus faible du dernier du chemin
            Sommet s = tempSource.remove(0);
            for(Sommet v : sortant.get(s)) {
                degreeEntrant.put(v,degreeEntrant.get(v) -1);
                if (degreeEntrant.get(v) == 0) {
                    tempSource.add(v);
                }
            }
            chemin.add(s);
        }


        return chemin;
    }

    public String toString() {
        String str = "Voisin Sortant" + sortant+"\nVoisin Entrant"+ entrant+"\nSource : "+source;

        return str;
    }

    public int GetDistance(ArrayList<Sommet> _chemin) throws IOException {
        TreeMap<String, ArrayList<Integer>> distance = FileManager.exportVille();
        TreeMap<String, Integer> villeID = FileManager.exportVilleID();
        
        int res = 0;
        
        for (int i = 1; i < _chemin.size(); i++) {
            res += distance.get(_chemin.get(i).getName()).get(villeID.get(_chemin.get(i-1).getName()));
        }
        
        return res;
    }

    
}
