package org.APPLI.modele;

import java.util.ArrayList;
import java.util.TreeMap;

public class Sommet {
    private String name;
    private int pass;
    private int id;
    private ArrayList<Integer> distance;
    private static TreeMap<String, Integer> villeID;


    // =================== Constructeur =====================
    public Sommet(String _name, int _pass, int _id) {
        name = _name;
        pass = _pass;
        id = _id;
    }   

    public Sommet(String _name, int _pass, ArrayList<Integer> _distance) {
        name = _name;
        pass = _pass;
        distance = _distance;
    }   
    public Sommet(String _name, int _pass, ArrayList<Integer> _distance, TreeMap<String, Integer> _idVille) {
        name = _name;
        pass = _pass;
        distance = _distance;
        villeID = _idVille;
        id = villeID.get(name);
    }   

 
    
    public Sommet(String _name, int _pass) {
        name = _name;
        pass = _pass;
    }   



    // =================== Get/Set ==========================
    public void setName(String _name) {
        name = _name;
    }

    public String getName() {
        return name;
    }

    public void setPass(int _pass) {
        pass = _pass;
    }

    public int getPass() {
        return pass;
    }

    public int getId() {
        return id;
    }

    public void setId(int _id) {
        id = _id;
    }

    public ArrayList<Integer> getDistance() {
        return distance;
    }

    // =================== Method =============================
    /** 
     * Cette méthode permet de déterminer si le nom du sommet est le même ou non
     * @return boolean true si c'est le cas, false au contraire
    */
    public int compareToName(Sommet _sommet) {
        int comp =getName().compareTo(_sommet.getName());
        if(comp > 0) {
            return 1;
        } else if (comp < 0){
            return -1;
        }

        return 0;
    }

    public int compareTo(Sommet _sommet) {
        int comp = name.compareTo(_sommet.getName());
        if(comp > 0) {
            return 1;
        } else if(comp < 0) {
            return -1;
        }


        else if (pass <_sommet.getPass()) {
            return -1;
        } else if (pass > _sommet.getPass()) {
            return 1;
        }
    
        return 0;
    }

    public boolean  isVelizyEnd() {
        return name.compareTo("Velizy") == 0 && pass == 1;
    }

    public String toString() {
        return name+((pass == 0)?"+" : "-");
    }

    public int getDistanceFrom(Sommet _sommet) {
        return distance.get(_sommet.getId());
    }
}

/*
 *     try {
            
             
            // for (int i =0 ; i < 1; i++) {
                int i = 4;
                String scenario = "scenario_"+i+".txt";
                TreeMap<Sommet, ArrayList<Sommet>> map = FileManager.toGraph(scenario);
                Graphe graphe = new Graphe(map);
                ArrayList<Sommet> chemin = graphe.triTopologique(Graphe.FIRST);
                ArrayList<Sommet> cheminDist = graphe.triTopologique(Graphe.DISTANCE);
                ArrayList<Sommet> cheminDegr = graphe.triTopologique(Graphe.DEGREE);
             
          
                System.out.println("Scenario FIR"+i+" : "+ graphe.GetDistance(chemin));
                System.out.println("Scenario DIS"+i+" : "+ graphe.GetDistance(cheminDist));
                System.out.println("Scenario DEG"+i+" : "+ graphe.GetDistance(cheminDegr)+"\n");
                

                
                System.out.println(graphe.triTopologiqueRecursif());
            // }
            
           
        
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

 */
