
package org.APPLI.modele;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.TreeSet;

public class Graphe {
    // On va passer par des identifiants de villes 

    private final TreeMap<Sommet, TreeSet<Sommet>> sortant;
    private final TreeMap<Sommet, Integer> entrant;
    private final LinkedList<Sommet> source;

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




}
