package org.APPLI.modele;

import org.javatuples.Pair;

import java.util.*;

public class GrapheOriente {




    public TreeMap<Integer, TreeSet<Integer>> graphe;
    private TreeMap<Integer, Integer> entrant;
    private TreeSet<Integer> source;

    public GrapheOriente(int[][] _tab) {

        graphe = new TreeMap<>();

        for (int i = 0; i < _tab.length; i++) {
            TreeSet<Integer> tempSet = new TreeSet<Integer>();
            for (int voisin : _tab[i]) {
                tempSet.add(voisin);
            }
            graphe.put(i, tempSet);
        }

    }

    public String toString() {
        String res = "Ordre : "+getOrder()+"\ntaille : "+getSize()+"\nDegré Min :"+degreeMin()+"\nDegré Max :"+degreeMax()+"\n";
        for (int i = 0; i < graphe.size(); i++) {
            res += "Sommet "+i+" degré="+getDegree(i)+" voisins : ";
            for( int voisin : graphe.get(i)) {
                res += voisin+" ";
            }
            res += "\n";
        }
        return res;
    }

    public Set<Integer> getVertices() {
        return graphe.keySet();
    }

    public int getOrder() {
        return graphe.size();
    }

    public int getDegree(int _vertex) {
        return graphe.get(_vertex).size();
    }

    public int getSize() {
        int size = 0;
        for (int i = 0; i < graphe.size(); i++) {
            size += graphe.get(i).size();
        }
        return size/2;
    }

    public int degreeMin() {
        int min = Integer.MAX_VALUE; // On init à une valeur quasi infini
        for (int i = 0; i < graphe.size(); i++) {
            if (graphe.get(i).size() < min) {
                min = graphe.get(i).size();
            }
        }
        return min;
    }

    public int degreeMax() {
        int max = 0;
        for (int i = 0; i < graphe.size(); i++) {
            if (graphe.get(i).size() > max) {
                max = graphe.get(i).size();
            }
        }
        return max;
    }

    public HashMap<Integer, TreeSet<Pair<Integer,Integer>>> parcoursEnLargeur(int _depart) {
        HashMap<Integer, TreeSet<Pair<Integer,Integer>>> res = new HashMap<Integer, TreeSet<Pair<Integer,Integer>>>();

        int courant;
        int[] distance = new int[graphe.size()];
        for (int i = 0; i < graphe.size(); i++) {
            distance[i] = Integer.MAX_VALUE;
        }
        distance[_depart] = 0;
        int dist = distance[_depart];
        ArrayList<Integer> file = new ArrayList<>() ;

        file.add(_depart);
        res.put(_depart, new TreeSet<Pair<Integer, Integer>>());
        res.get(_depart).add(new Pair<Integer,Integer>(-1,0));

        while (!file.isEmpty()) {
            System.out.println(file);
            courant = file.remove(0);

            dist = distance[courant]+1;
            for (int i : graphe.get(courant)) {
                if (!res.containsKey(i) && !file.contains(i)) {
                    res.put(i, new TreeSet<Pair<Integer, Integer>>());
                    file.add(i);
                    distance[i] = dist;
                    res.get(i).add(new Pair<Integer, Integer>(courant, dist));
                }
            }
        }
        return res;
    }

    public ArrayList<Integer> plusCourtChemin(int _depart, int _arrivee, HashMap<Integer, TreeSet<Pair<Integer,Integer>>> _pred) {
        ArrayList<Integer> chemin = new ArrayList<>() ;
        int courant = _arrivee;
        chemin.add(_arrivee);
        while (courant != _depart) {
            courant = _pred.get(courant).first().getValue0();
            chemin.add(courant);
        }
        Collections.reverse(chemin);
        return chemin ;
    }


    public ArrayList<Integer> triTopologique() {
        ArrayList<Integer> num = new ArrayList<Integer>();
        TreeSet<Integer> source = getSource();
        TreeMap<Integer, Integer> entrant = getVoisinEntrant();

        while (!source.isEmpty()) {
            int extract = source.pollFirst();
            for (int sommet : graphe.get(extract)) {
                entrant.put(sommet, entrant.get(sommet) -1);
                if(entrant.get(sommet) == 0) {
                    num.add(extract);
                }

            }
            num.add(extract);
        }
        return num;
    }

    public ArrayList<Integer> triTopologique2() {
        TreeMap<Integer, Integer> degreeEntrants = getVoisinEntrant();
        TreeSet<Integer> source = getSource();
        ArrayList<Integer> num = new ArrayList<>() ;
        while (!source.isEmpty()) {
            int s = source.pollFirst();
            for (int v : graphe.get(s)) {
                degreeEntrants.put(v, degreeEntrants.get(v)  -1);
                if (degreeEntrants.get(v) == 0) {
                    source.add(v);
                }
            }
            num.add(s);
        }
        return num;
    }

    public TreeSet<Integer> getSource() {
        source = new TreeSet<Integer>();
        for (int i : getVoisinEntrant().keySet()) {
            if (getVoisinEntrant().get(i) == 0) {
                source.add(i);
            }
        }
        return source;
    }

    public TreeMap<Integer, Integer> getVoisinEntrant (){
        TreeMap<Integer, Integer> voisinEntrant = new TreeMap<>();
        for (int i = 0; i < graphe.size(); i++) {
            voisinEntrant.put(i, 0);
        }

        for (int i : graphe.keySet()) {
            for (int j : graphe.get(i)) {
                voisinEntrant.put(j, voisinEntrant.get(j) + 1);
            }
        }


        return voisinEntrant;
    }





}
