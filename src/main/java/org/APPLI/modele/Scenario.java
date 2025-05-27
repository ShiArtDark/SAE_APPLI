package org.APPLI.modele;

import java.util.ArrayList;
import java.util.TreeMap;

public class Scenario {
    String name;
    TreeMap<Sommet, ArrayList<Sommet>> scenario;
    Graphe scenarioGraphe;

    public Scenario(String _name) {
        name = _name;
        
    }

    
}
