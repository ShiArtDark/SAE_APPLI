package org.APPLI.modele;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

public class Graphe {
    // On va passer par des identifiants de villes 

    private TreeMap<Integer, TreeSet<Integer>> sortant;
    private TreeMap<Integer, Integer> entrant;
    private TreeSet<Integer> source;

    // ======================== CONSTRUCTEUR ========================

    public Graphe( int[][] _tab ) {
        sortant = new TreeMap<>();
        entrant = new TreeMap<>();
        source = new TreeSet<>();

        for (int k = 0; k < _tab.length; k++ ) { // init d'entrant
            entrant.put(k, 0);
        }

        for (int i = 0; i < _tab.length; i++) { // init de sortant
            TreeSet<Integer> tempSet = new TreeSet<>();
            for (int voisin : _tab[i]) {
                tempSet.add(voisin);
                entrant.put(voisin, entrant.get(voisin)+1); // On ajoute un au nombre d'entrant
            }
            sortant.put(i, tempSet);
        }

        for (int sommet : entrant.keySet() ) {
            if (entrant.get(sommet) == 0) {
                source.add(sommet);
            }
        }
    }

    // ======================== GET/SET ========================

    public TreeMap<Integer, TreeSet<Integer>> getSortant() {
        return sortant;
    }

    public TreeMap<Integer, Integer> getEntrant() {
        return entrant;
    }

    public TreeSet<Integer> getSource() {
        return source;
    }

    // ======================== Method ========================
    

    public ArrayList<Integer> triTopologique() {
        TreeMap<Integer, Integer> degreeEntrant = (TreeMap<Integer, Integer>) entrant.clone();
        TreeSet<Integer> tempSource = (TreeSet<Integer>) source.clone();
        ArrayList<Integer> chemin = new ArrayList<>();
        while (!tempSource.isEmpty()) {
            int s = tempSource.pollFirst();
            for(int v : sortant.get(s)) {
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
        return "";
    }

    
}
