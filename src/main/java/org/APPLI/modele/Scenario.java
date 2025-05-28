package org.APPLI.modele;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

public class Scenario {
    private String name;
    private TreeMap<String, ArrayList<Integer>> distances;
    private TreeMap<String, ArrayList<String>> membre;
    private TreeSet<String> allMember;
    private TreeMap<String, Integer> villeID ;
    private TreeMap<Integer, String> IDVille;
    private TreeMap<Sommet, ArrayList<Sommet>> map;

    
    public Scenario() {
        
    }
}
