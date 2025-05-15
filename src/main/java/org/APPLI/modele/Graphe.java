package org.APPLI.modele;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

public class Graphe {
    // On va passer par des identifiants de villes 

    private TreeMap<Sommet, TreeSet<Sommet>> sortant;
    private TreeMap<Sommet, Integer> entrant;
    private TreeSet<Sommet> source;

    // ======================== CONSTRUCTEUR ========================

    public Graphe (TreeMap<Sommet, Sommet[]> _tab) {
        sortant = new TreeMap<>();
        entrant = new TreeMap<>();
        source = new TreeSet<>();

        for (Sommet som : _tab.keySet()) {
            entrant.put(som, 0);
        }

        for (Sommet som : _tab.keySet()) {
            TreeSet<Sommet> tempSet = new TreeSet<>();

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

    public TreeSet<Sommet> getSource() {
        return source;
    }

    // ======================== Method ========================
    

    public ArrayList<Sommet> triTopologique() {
        TreeMap<Sommet, Integer> degreeEntrant = (TreeMap<Sommet, Integer>) entrant.clone();
        TreeSet<Sommet> tempSource = (TreeSet<Sommet>) source.clone();
        ArrayList<Sommet> chemin = new ArrayList<>();
        while (!tempSource.isEmpty()) {
            Sommet s = tempSource.pollFirst();
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
        return "";
    }

    
}
