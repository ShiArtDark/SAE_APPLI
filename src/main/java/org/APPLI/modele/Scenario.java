package org.APPLI.modele;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

public class Scenario {
    private String name;
    private final TreeMap<String, ArrayList<Integer>> distances;
    private final TreeMap<String, ArrayList<String>> membre;
    private final TreeSet<String> allMember;
    private final TreeMap<String, Integer> villeID ;
    private final TreeMap<Integer, String> IDVille;
    private final Graphe map;

    
    public Scenario(
        String _name,
        TreeMap<String, ArrayList<Integer>> _distance,
        TreeMap<String, ArrayList<String>>  _membre,
        TreeSet<String> _allMember,
        TreeMap<String, Integer> _villeID,
        TreeMap<Integer, String> _IdVille,
        Graphe _map
    ) throws Exception 
    {

        name =_name;
        distances =_distance;
        membre= _membre;
        allMember = _allMember;
        villeID = _villeID;
        IDVille = _IdVille;
        map = _map;


    }

    public TreeMap<String, ArrayList<Integer>>  getDistance() {
        return distances;
    }
    
    public TreeMap<String, ArrayList<String>> getMembres() {
        return membre;
    }
    
    public TreeSet<String> getAllMembre() {
        return allMember;
    }

    public TreeMap<String, Integer> getVilleID() {
        return villeID;
    }

    public TreeMap<Integer, String> getIDMap() {
        return IDVille;
    }

    public Graphe getGraphe() {
        return map;
    }

}
